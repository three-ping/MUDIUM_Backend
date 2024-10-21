<template>
    <div class="board-create">
    <h1>게시글 작성</h1>
    <div class="board-create-header">
        <input type="text" placeholder="제목을 입력하세요" v-model="title">
    </div>
    <div class="board-create-content">
        <textarea placeholder="내용을 입력하세요" v-model="content"></textarea>
    </div>
    <div class="button-container">
        <button class="board-back" @click="goBack">취소</button>
        <button class="comment-submit" @click="submitBoard">등록</button>
    </div>
    <Modal v-if="showModal" @close="showModal = false">
        <p>제목과 내용을 모두 입력해주세요.</p>
    </Modal>
    </div>
</template>

<script setup>
import { ref,defineProps } from 'vue';
import { useRouter } from 'vue-router';
import Modal from '@/components/common/Modal.vue'; 

const props = defineProps({
    userId: Number,
    access_token: String
})
const router = useRouter();
const title = ref('');
const content = ref('');
const userId = Number(props.userId);
const showModal = ref(false);
const access_token = props.access_token;

const goBack = () => {
    router.push('/board/view');
};

const submitBoard = async () => {
    if (title.value.trim() === '' || content.value.trim() === '') {
        showModal.value = true;
    return;
    }

    const createBoard = async(title,content)=>{
    return await fetch(`http://localhost:8080/api/board`,{
        method:"POST",
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    },
        body: JSON.stringify({
            "title":`${title.value}`,
            "content":`${content.value}`,
            "userId":`${userId}`
        })
    })
};
    const response = await createBoard(title,content);
    const data = await response.json();
    console.log(data);
    if(data.success){
        router.push('/board/view');
    } else{
        window.alert(`${data.error}`)
    }
};
</script>

<style scoped>
button { 
    box-shadow: none;
}
.board-create {
    width: 100%;
    height: 70%;
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.board-create-header {
    border-bottom: 2px solid #D53EC6;
    padding-bottom: 10px;
    margin-bottom: 20px;
}

.board-create-header input {
    width: 100%;
    padding: 10px;
    font-size: 18px;
    border: none;
    outline: none;
}

.board-create-content {
    margin-bottom: 20px;
}

.board-create-content textarea {
    width: 100%;
    height: 50vh;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    resize: vertical;
}

.button-container {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
}

.board-back, .comment-submit {
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

.board-back {
    background-color: #9A70CC;
    color: white;
}

.comment-submit {
    background-color: #D53EC6;
    color: white;
}
</style>