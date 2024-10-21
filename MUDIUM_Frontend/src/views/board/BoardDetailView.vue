<template>
    <div class="board-detail">
        <div class="board-detail-header">
            <h1 class="board-detail-title">{{ title }}</h1>
            <div class="board-detail-buttons">
                <template v-if="userId == author_id">
                    <button class="board-button-delete" @click="showDeleteModal = true">삭제</button>
                    <button class="board-button-update" @click="editPost">수정</button>
                </template>
            <button class="board-button-back" @click="backToBoard">글 목록</button>
        </div>
    </div>

        <div class="board-detail-subheader">
            <div class="board-detail-author-created">
                <span class="board-detail-author">작성자: {{ author }}</span>
                <span class="board-detail-created">작성시간: {{ convertToKoreanTime(createdAt) }}</span>
            </div>
        </div>

        <template v-if="updatedAt !== null">
            <div class="board-detail-updated-template">
                <span class="board-detail-updated">수정시간: {{ convertToKoreanTime(updatedAt) }}</span>
            </div>
        </template>

        <div class="board-detail-subheader">
            <div class="board-detail-viewCount">
                <span class="board-detail-viewCount">조회수: {{ viewCount }}</span>
            </div>
        </div>

        <div class="board-detail-content">
            <div class="board-detail-content-read">
                <p>{{ content }}</p>
            </div>
            <Like class="board-like" :likeCount="likeCount" :isLiked="isLiked" :boardId="id" :userId="userId" />
        </div>
        <BoardComment :access_token="access_token" :id="id" :userNickname="userNickname" :userId="userId"></BoardComment>
    </div>

    <Modal v-model:isVisible="showDeleteModal" @confirm="deletePost">
        정말 삭제하시겠습니까?
    </Modal>
</template>

<script setup>
import { ref,reactive, onMounted,defineProps } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Like from "@/components/common/Like.vue";
import Modal from "@/components/common/Modal.vue";
import BoardComment from './BoardComment.vue';


const route = useRoute();
const router = useRouter();
const id = ref(Number(route.params.id));
const props = defineProps({
    userId: Number,
    nickname: String,
    access_token:String
});

const userId = Number(props.userId);
const title = ref('');
const author = ref('');
const content = ref('로딩중');
const createdAt = ref(0);
const updatedAt = ref(0);
const likeCount = ref(0);
const isLiked = ref(false);
const userNickname = props.nickname;
const showDeleteModal = ref(false);
const viewCount = ref(0);
const author_id = ref(0);
const access_token = props.access_token;

const editPost = () => {
    router.push(`/board/edit/${id.value}`);
};

const deletePost = async () => {
    await fetch(`http://localhost:8080/api/board/${id.value}/${userId}`, {
        method: 'DELETE',
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
    }
    );
    router.push('/board');  
};

const fetchDetailBoard = async() => {
    const response = await fetch(`http://localhost:8080/api/board/${id.value}`, {
        method: "GET",
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
    });
    const responseDTO = await response.json();
    title.value = responseDTO.data.title;
    author.value = responseDTO.data.nickname;
    author_id.value = responseDTO.data.userId;
    content.value = responseDTO.data.content;
    createdAt.value = responseDTO.data.createdAt;
    updatedAt.value = responseDTO.data.updatedAt;
    likeCount.value = Number(responseDTO.data.boardLike);
    viewCount.value = Number(responseDTO.data.viewCount);

    incrementViewCount();
}

const incrementViewCount = async () => {
    const viewedPosts = JSON.parse(localStorage.getItem('viewedPosts') || '{}');
    if (!viewedPosts[id.value]) {
        viewedPosts[id.value] = true;
        localStorage.setItem('viewedPosts', JSON.stringify(viewedPosts));

        await fetch(`http://localhost:8080/api/board/${id.value}/count`, {
            method: 'PUT',
            headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
        });

        viewCount.value++;
    }
}



const backToBoard = () => {
    router.push('/board'); 

}

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

const checkIsLiked = async () => {
    const response = await fetch(`http://localhost:8080/api/board-like/${id.value}/${userId.value}`, {
        method: "GET",
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
    });
    const responseDTO = await response.json();
    isLiked.value = responseDTO.data;
}


onMounted(() => {
    fetchDetailBoard();
    checkIsLiked();
});
</script>

<style scoped>
button { 
    box-shadow: none;
}
.board-detail {
font-family: Arial, sans-serif;
max-width: 1200px;
width: 80%;
margin: 5vh auto;
padding: 20px;
}

.board-detail-header {
border-bottom: 2px solid #D53EC6;
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

.board-submit {
border: none;
padding: 10px 20px;
border-radius: 4px;
cursor: pointer;
color: white;
}

.board-detail-author-created {
font-size: 14px;
color: #666;
display:flex;
justify-content: space-between;
}

.board-detail-viewCount {
    font-size: 14px;
    color: #666;
    display:flex;
    flex-direction: column;
    align-items: end;
}
.board-detail-comment h2 {
font-size: 18px;
margin-bottom: 10px;
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


.board-button-update {
background-color: #9A70CC;
}

.board-button-delete{
    background-color: #D53EC6;
}

.board-button-back {
    background-color: #6EABE1;
}

.board-detail-comments {
margin-top: 30px;
}


.board-detail-container {
position: relative;
width: 100%;
min-height: 500px; /* 최소 높이 설정, 필요에 따라 조정 */
border: 1px solid #ccc;
}

.board-content {
width: 100%;
height: 100%;
padding: 20px;
box-sizing: border-box;
}
.board-detail-content {
position: relative;
min-height: 50vh;
margin-bottom: 30px;
border: 1px solid #eee;
padding: 20px;
overflow-y: auto;
}

.board-detail-content-read {
margin-bottom: 100px; /* 좋아요 박스와 겹치지 않도록 여백 추가 */
}

.board-like {
position: absolute;
left: 50%;
bottom: 20%; /* 아래에서 20% 위치 (전체 높이의 4/5 지점) */
transform: translateX(-50%);
border-radius: 8px;
box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.board-button-delete,
.board-button-update,
.board-button-back {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  color: white;
  font-weight: bold;
  min-width: 80px;
}
* {
    font-size: 2rem;
}



</style>
