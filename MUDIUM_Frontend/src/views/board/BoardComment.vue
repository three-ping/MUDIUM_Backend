<template>
    <div class="board-comment">
        <div class="board-detail-comment">
            <div class="write-comment">
                <h2>댓글을 입력하세요</h2>
                <button class="comment-submit" @click="submitComment">등록</button>
            </div>
            <textarea v-model="newComment" placeholder="댓글을 입력하세요."></textarea>
        </div>

        <div class="board-detail-comments">
            <div class="comment" v-for="(comment, index) in comments" :key="comment.id">
                <template v-if="comment.activeStatus == 'ACTIVE'">
                    <div v-if="!comment.isEditing">
                        <strong>{{ comment.nickname }}</strong>
                        <p>{{ comment.content }}</p>
                        <div class="comment-time">
                            <span>{{ convertToKoreanTime(comment.createdAt) }}</span>
                            <span v-if="comment.updatedAt && comment.updatedAt !== comment.createdAt">
                                (수정됨: {{ convertToKoreanTime(comment.updatedAt) }})
                            </span>
                        </div>
                    
                        <template v-if="userId==comment.userId">
                            <button @click="enableEdit(index)">수정</button>
                            <button @click="deleteComment(comment.boardCommentId)">삭제</button> 
                        </template>
                        
                    </div>
            
                    <div v-else>
                        <strong>{{ comment.nickname }}</strong>
                        <textarea v-model="comment.editContent"></textarea>
                        <div class="edit-buttons">
                            <button @click="cancelEdit(index)">수정 취소</button>
                            <button @click="submitEdit(comment, index)">수정 완료</button>
                        </div>
                    </div>
                </template>
                <template v-else>
                    <strong>삭제된 댓글입니다.</strong>
                </template>
                

                <div v-for="reply in comment.replies" :key="reply.boardReplyId">
                    <Reply 
                        :reply="reply" 
                        :userId="userId"
                        :access_token="access_token"
                        @updateReply="handleReplyUpdate"
                        @deleteReply="handleReplyDelete"
                    />
                </div>
                
                <div class="reply-create">
                    <textarea v-model="newReply" placeholder="대댓글을 작성하세요."></textarea>
                    <button class="reply-submit" @click="submitReply(comment.boardCommentId)">등록</button>
                </div>

            </div>
        </div>
        <Paging 
            :requestURL="requestURL" 
            :pageNumber="pageNumber" 
            :totalPageNumber="totalPageNumber" 
            @updatePageNumber="updatePageNumber">
        </Paging>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import Reply from './BoardReply.vue';
import Paging from "@/components/board/pagination.vue";

const props = defineProps({
    id: Number,
    userNickname: String,
    userId: Number,
    access_token: String
});
const isVisible = ref(false);
const requestURL = `api/board-comment`;
const pageNumber = ref(1);
const totalPageNumber = ref(0);
const id = ref(props.id);
const userId = ref(props.userId);
const userNickname = ref(props.userNickname);
const newComment = ref('');
const comments = reactive([]);
const pageSize = ref(5);
const newReply = ref('');
const access_token = props.access_token;

const fetchComments = async () => {
    const response = await fetch(`http://localhost:8080/api/board-comment/${id.value}?page=${pageNumber.value}&size=${pageSize.value}`, {
        method: 'GET',
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
    });
    const responseDTO = await response.json();
    const fetchedComments = responseDTO.data.content;
    comments.length = 0;
    
    for (const comment of fetchedComments) {

        const replies = await fetchReplies(comment.boardCommentId);
        comments.push({
            ...comment,
            isEditing: false,
            editContent: comment.content,
            replies: replies 
        });
    }

    totalPageNumber.value = responseDTO.data.totalPages;
};

const updatePageNumber = (newPageNumber) => {
    pageNumber.value = newPageNumber;
    fetchComments();
};

const submitComment = async () => {
    if (!newComment.value.trim()) {
        return;
    }
    await fetch(`http://localhost:8080/api/board-comment/${id.value}`, {
        method: "POST",
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    },
        body: JSON.stringify({
            "content": newComment.value,
            "userId": userId.value,
            "nickname": userNickname.value
        })
    });
    newComment.value = '';
    pageNumber.value = 1;
    await fetchComments();
};

const submitReply = async (id) => {
    if(!newReply.value.trim()) {
        return;
    }
    await fetch(`http://localhost:8080/api/board-reply/${id}`,{
        method: "POST",
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    },
        body: JSON.stringify({
            "content": newReply.value,
            "userId": userId.value,
            "nickname": userNickname.value
        })
    })
    newReply.value = '';
    await fetchComments();
};

const enableEdit = (index) => {
    comments[index].isEditing = true;
};

const cancelEdit = (index) => {
    comments[index].isEditing = false;
    comments[index].editContent = comments[index].content;
};

const submitEdit = async (comment, index) => {
    if (!comment.editContent.trim()) {
        return;
    }
    await fetch(`http://localhost:8080/api/board-comment/${comment.boardCommentId}`, {
        method: "PUT",
        headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    },
        body: JSON.stringify({
            "content": comment.editContent
        })
    });
    comments[index].content = comment.editContent;
    comments[index].isEditing = false;
    await fetchComments();
};

const deleteComment = async (commentId) => {
    if (confirm("댓글을 삭제하시겠습니까?")) {
        await fetch(`http://localhost:8080/api/board-comment/${commentId}`, {
            method: "DELETE",
            headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
        });
        await fetchComments();
    }
};

const fetchReplies = async (boardCommentId) => {
    const response = await fetch(`http://localhost:8080/api/board-reply/${boardCommentId}`,{
    method: "GET",
    headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
    });
    const responseDTO = await response.json();
    return responseDTO.data;
};

const handleReplyUpdate = async (updatedReply) => {
    const commentIndex = comments.findIndex(comment => 
        comment.replies.some(reply => reply.boardReplyId === updatedReply.boardReplyId)
    );
    if (commentIndex !== -1) {
        const replyIndex = comments[commentIndex].replies.findIndex(
            reply => reply.boardReplyId === updatedReply.boardReplyId
        );
        if (replyIndex !== -1) {
            comments[commentIndex].replies[replyIndex] = updatedReply;
        }
    }
};

const handleReplyDelete = async (replyId) => {
    const commentIndex = comments.findIndex(comment => 
        comment.replies.some(reply => reply.boardReplyId === replyId)
    );
    if (commentIndex !== -1) {
        comments[commentIndex].replies = comments[commentIndex].replies.filter(
            reply => reply.boardReplyId !== replyId
        );
    }
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
    fetchComments();
});
</script>

<style scoped>
button { 
    box-shadow: none;
}
.board-detail-comment h2 {
    font-size: 18px;
    margin-bottom: 10px;
}

textarea {
    width: 100%;
    height: 100px;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.comment-submit {
    padding: 8px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    color: white;
    font-weight: bold;
    min-width: 80px;
    height: 4vh;
    background-color: #D53EC6;
}

.comment {
    background-color: #f9f9f9;
    border: 1px solid #eee;
    border-radius: 4px;
    padding: 10px;
    margin-bottom: 10px;
}

.comment strong {
    display: block;
    margin-bottom: 5px;
}

.write-comment {
    display: flex;
    justify-content: space-between;
}

.comment-time {
    font-size: 0.8em;
    color: #888;
    text-align: right;
}

.edit-buttons {
    display: flex;
    gap: 10px;
    margin-top: 10px;
}

.replies {
    margin-top: 10px;
}

button {
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.reply-create {
    margin-top: 3vh;
    margin-left: 5vh;
    width: 85%;
    display: flex;
    flex-direction: column;
    align-items: end;
}
*{
    font-size: 2rem;
}
</style>