<template>
    <div class="comment-reply">
    <div v-if="!isEditing">
        <strong>{{ reply.nickname }}</strong>
        <p>{{ reply.content }}</p>
        <div class="reply-time">
        <span>{{ convertToKoreanTime(reply.createdAt) }}</span>
        <span v-if="reply.updatedAt && reply.updatedAt !== reply.createdAt">
            (수정됨: {{ convertToKoreanTime(reply.updatedAt) }})
        </span>
        </div>
        <template v-if="userId === reply.userId">
        <button @click="enableEdit">수정</button>
        <button @click="deleteReply">삭제</button>
        </template>
    </div>
    <div v-else>
        <strong>{{ reply.nickname }}</strong>
        <textarea v-model="editContent"></textarea>
        <div class="edit-buttons">
        <button @click="cancelEdit">수정 취소</button>
        <button @click="submitEdit">수정 완료</button>
        </div>
    </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
    reply: Object,
    userId: Number,
    boardId: Number,
    access_token: String
});

const emit = defineEmits(['updateReply', 'deleteReply']);
const access_token = props.access_token;

const isEditing = ref(false);
const editContent = ref(props.reply.content);

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

const enableEdit = () => {
    isEditing.value = true;
};

const cancelEdit = () => {
    isEditing.value = false;
    editContent.value = props.reply.content;
};

const submitEdit = async () => {
    if (!editContent.value.trim()) return;

    const response = await fetch(`http://localhost:8080/api/board-reply/${props.reply.boardReplyId}`, {
        method: "PUT",
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    },
        body: JSON.stringify({
        content: editContent.value
        })
    });

        emit('updateReply', {
        ...props.reply,
        content: editContent.value,
        updatedAt: new Date().toISOString()
        });
        isEditing.value = false;

};

const deleteReply = async () => {
    if (confirm("대댓글을 삭제하시겠습니까?")) {

        const response = await fetch(`http://localhost:8080/api/board-reply/${props.reply.boardReplyId}`, {
        method: "DELETE",
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
        });
        emit('deleteReply', props.reply.boardReplyId);
    }
};
</script>

<style scoped>
button { 
    box-shadow: none;
}
.comment-reply {
    background-color: #f0f0f0;
    border-left: 2px solid #ccc;
    padding-left: 15px;
    margin-top: 10px;
}
.reply-time {
    font-size: 0.8em;
    color: #888;
}
button {
    margin-right: 10px;
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
textarea {
    width: 100%;
    height: 60px;
    margin-bottom: 10px;
    padding: 5px;
}
.edit-buttons {
    display: flex;
    gap: 10px;
}
button {
    box-shadow: none;
}
*{
    font-size: 2rem;
}
</style>