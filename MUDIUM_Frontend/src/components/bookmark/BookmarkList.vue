<template>
    <div class="bookmark-list">
      <h2>내 북마크 목록</h2>
      <ul>
        <li v-for="bookmark in bookmarks" :key="bookmark.id">
          <router-link :to="`/musical/${bookmark.musicalId}`">
            {{ bookmark.musicalTitle }}
          </router-link>
        </li>
      </ul>
      <div v-if="loading" class="loading">Loading...</div>
      <div v-if="error" class="error">{{ error }}</div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const bookmarks = ref([]);
  const loading = ref(false);
  const error = ref(null);
  
  const fetchBookmarks = async () => {
    loading.value = true;
    error.value = null;
    try {
      const userId = getCurrentUserId();
      const response = await fetch(`http://localhost:8080/api/bookmark/${userId}`); 
      if(!response.ok) {
        throw new Error('데이터 불러오기 실패!');
      }
      const data = await response.json();
      bookmarks.value = data.data; 
      console.log("북마크: ", bookmarks);
    } catch (err) {
      error.value = "데이터를 불러오는 중 오류가 발생했습니다.";
    } finally {
      loading.value = false;
    }
  };
  
  onMounted(() => {
    fetchBookmarks();
  });
  </script>
  
  <style scoped>
  .bookmark-list {
    padding: 20px;
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
  