<template>
  <div>
    <button @click="toggleBookmark">
      <span v-if="isBookmarked" v-html="bookmarkFillSvg"></span>
      <span v-else v-html="bookmarkSvg"></span>
    </button>
    <div v-if="loading" class="loading">Loading...</div>
    <div v-if="error" class="error">{{ error }}</div>
    <BookmarkModal
      :isOpen="isModalOpen"
      :message="modalMessage"
      @close="closeModal" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import BookmarkModal from "@/components/bookmark/BookmarkModal.vue";

const props = defineProps({
  musicalId: {
    type: Number,
    required: true,
  },
  initialBookmarked: {
    type: Boolean,
    default: false,
  },
});

const isBookmarked = ref(props.initialBookmarked);
const loading = ref(false);
const error = ref(null);
const isModalOpen = ref(false);
const modalMessage = ref("");

// 북마크 해제
const bookmarkSvg = `
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmarks" viewBox="0 0 16 16">
  <path d="M2 4a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v11.5a.5.5 0 0 1-.777.416L7 13.101l-4.223 2.815A.5.5 0 0 1 2 15.5zm2-1a1 1 0 0 0-1 1v10.566l3.723-2.482a.5.5 0 0 1 .554 0L11 14.566V4a1 1 0 0 0-1-1z"/>
  <path d="M4.268 1H12a1 1 0 0 1 1 1v11.768l.223.148A.5.5 0 0 0 14 13.5V2a2 2 0 0 0-2-2H6a2 2 0 0 0-1.732 1"/>
</svg>
`;

// 북마크 추가
const bookmarkFillSvg = `
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmarks-fill" viewBox="0 0 16 16">
  <path d="M2 4a2 2 0 0 1 2-2h6a2 2 0 0 1 2 2v11.5a.5.5 0 0 1-.777.416L7 13.101l-4.223 2.815A.5.5 0 0 1 2 15.5z"/>
  <path d="M4.268 1A2 2 0 0 1 6 0h6a2 2 0 0 1 2 2v11.5a.5.5 0 0 1-.777.416L13 13.768V2a1 1 0 0 0-1-1z"/>
</svg>
`;

// 북마크 상태를 토글하는 함수
const toggleBookmark = async () => {
  loading.value = true;
  error.value = null;

  try {
    const url = isBookmarked.value
      ? `http://localhost:8080/api/bookmark`
      : "http://localhost:8080/api/bookmark/add";

    const options = isBookmarked.value
      ? { method: "DELETE" }
      : {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ musicalId: props.musicalId }),
        };

    const response = await fetch(url, options);

    if (!response.ok) {
      throw new Error("Failed to update bookmark");
    }

    // 상태를 반전시킴
    isBookmarked.value = !isBookmarked.value;
    modalMessage.value = isBookmarked.value
      ? "북마크가 추가되었습니다!"
      : "북마크가 제거되었습니다!";
    isModalOpen.value = true;
  } catch (err) {
    error.value = "북마크 처리 중 오류가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const closeModal = () => {
  isModalOpen.value = false;
};

// 컴포넌트가 마운트되었을 때 초기 상태를 설정
onMounted(() => {
  isBookmarked.value = props.initialBookmarked;
});
</script>

<style scoped>
button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
}

.loading {
  text-align: center;
  font-size: 18px;
  color: #3498db;
}

.error {
  text-align: center;
  font-size: 18px;
  color: #e74c3c;
}
</style>
