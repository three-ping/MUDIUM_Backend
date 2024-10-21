<template>
    <div class="board-detail">
    <div class="board-detail-header">
        <h1 class="board-detail-title">공지사항 수정</h1>
        <div class="board-detail-buttons">
        <button class="board-button" @click="updateBoard">수정 완료</button>
        <button class="board-button" @click="cancelEdit">취소</button>
        </div>
    </div>
    <div class="board-detail-subheader">
        <div class="board-detail-author-created">
        <span class="board-detail-author">작성자: {{ author }}</span>
        <span class="board-detail-created">작성시간: {{ convertToKoreanTime(createdAt) }}</span>
        </div>

    <template v-if="updatedAt !== null">
        <div class="board-detail-updated-template">
        <span class="board-detail-updated">수정시간: {{ convertToKoreanTime(updatedAt) }}</span>
        </div>
    </template>
    </div>
    <div class="board-detail-content">
        <input v-model="title" class="edit-title" placeholder="제목을 입력하세요" />
        <textarea v-model="content" class="edit-content" placeholder="내용을 입력하세요"></textarea>
    </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const id = ref(route.params.id);

const title = ref('');
const author = ref('');
const content = ref('');
const createdAt = ref(0);
const updatedAt = ref(null);
const userId = ref(3);

const fetchDetailBoard = async() => {
    try {
    const response = await fetch(`http://localhost:8080/api/notice/${id.value}`, {
        method: "GET"
    });
    const responseDTO = await response.json();
    title.value = responseDTO.data.title;
    author.value = responseDTO.data.nickname;
    content.value = responseDTO.data.content;
    createdAt.value = responseDTO.data.createdAt;
    updatedAt.value = responseDTO.data.updatedAt;
    } catch (error) {
    console.error('게시글을 불러오는데 실패했습니다:', error);
    }
}

const updateBoard = async () => {
    try {
    const response = await fetch(`http://localhost:8080/api/notice/${id.value}`, {
        method: 'PUT',
        headers: {
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({
        title: title.value,
        content: content.value,
        userId: userId.value
        }),
    });

    if (response.ok) {
        router.push(`/notice/view/${id.value}`);
    } else {
        console.error('게시글 수정에 실패했습니다.');
    }
    } catch (error) {
    console.error('게시글 수정 중 오류가 발생했습니다:', error);
    }
};

const cancelEdit = () => {
    router.push(`/board/view/${id.value}`);
};

function convertToKoreanTime(timestamp) {
    const date = new Date(timestamp);

    const options = {
    timeZone: 'Asia/Seoul', 
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    };

    return date.toLocaleString('ko-KR', options);
}

onMounted(() => {
    fetchDetailBoard();
});
</script>

<style scoped>
.board-detail {
    font-family: Arial, sans-serif;
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
}

.board-detail-header {
    border-bottom: 2px solid #279977;
    padding-bottom: 10px;
    margin-bottom: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.board-detail-buttons {
    display: flex;
    gap: 10px;
}

.board-detail-title {
    font-size: 24px;
    margin: 0;
}

.board-detail-subheader {
    margin-bottom: 20px;
}

.board-detail-author-created {
    font-size: 14px;
    color: #666;
    display: flex;
    justify-content: space-between;
}

.board-detail-updated {
    font-size: 14px;
    color: #666;
}

.board-detail-updated-template {
    display: flex;
    align-items: end;
    flex-direction: column;
}

.board-detail-content {
    min-height: 50vh;
    margin-bottom: 30px;
    border: 1px solid #eee;
    padding: 20px;
    overflow-y: auto;
}

.board-button {
    border: none;
    padding: 10px 20px;
    border-radius: 4px;
    cursor: pointer;
    color: white;
    background-color: #9A70CC;
}

.edit-title {
    width: 100%;
    font-size: 20px;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.edit-content {
    width: 100%;
    height: 300px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    resize: 
    vertical;
}
button {
    box-shadow: none;
}
*{
    font-size: 2rem;
}
</style>