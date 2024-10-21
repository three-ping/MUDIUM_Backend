<template>
    <div class="board-detail-content-like">
    <div 
        class="like-button-container" 
        @click="toggleLike"
        :class="{ 'liked': isLiked }"
    >
        <!-- <div class="like-count" id="likeCount">{{ likeCount }}</div> -->
        <button class="like-button" id="likeButton">
            <img src="@/assets/images/like.svg" alt="">
        </button>
    </div>
    </div>
</template>

<script setup>
import { ref, defineProps, onMounted, onUnmounted, watch } from 'vue';

const props = defineProps({
    likeCount: Number,
    isLiked: Boolean,
    reviewId: Number,
    userId: Number
})

const likeCount = ref(0);
const isLiked = ref(false);
const firstLike = ref(false);

const toggleLike = () => {
    if (isLiked.value) {
    likeCount.value--;
    } else {
    likeCount.value++;
    }
    isLiked.value = !isLiked.value;
};

watch(() => props.likeCount, (newValue) => {
    likeCount.value = newValue;
});

watch(() => props.isLiked, (newValue) => {
    isLiked.value = newValue;
    firstLike.value = isLiked.value;

});


const postLike = async (reviewId,userId) => {
    await fetch(`http://localhost:8080/api/review-like/${reviewId}`,
        {
            method:"POST",
            headers:{
                'Authorization': `${access_token}`,
                "Content-Type":"application/json"},
            body: `${userId}`
        }
    )
};

const deleteLike = async (reviewId,userId) => {
    await fetch(`http://localhost:8080/api/review-like/${reviewId}`,
        {
            method:"DELETE",
            headers:{
                'Authorization': `${access_token}`,
                "Content-Type":"application/json"},
        body: `${userId}`
        }
    )
};

onMounted( ()=>{
    likeCount.value = props.likeCount;
    isLiked.value = props.isLiked;
    firstLike.value = isLiked.value;
})

onUnmounted( () => {
    if (firstLike.value == isLiked.value) {
        return;
    } 

    if(isLiked.value) {
        postLike(props.reviewId,props.userId);
    } else {
        deleteLike(props.reviewId,props.userId);

    }
})
</script>

<style scoped>
.like-button-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100px;
    cursor: pointer;
    border: 2px solid #ccc;
    border-radius: 8px;
    padding: 10px;
    transition: all 0.3s ease;
    user-select: none;
}

.like-button-container:hover {
    border-color: #007bff;
}

.like-button-container.liked {
    background-color: #e6f2ff;
    border-color: #007bff;
}

.like-count {
    font-size: 20px;
    margin-bottom: 5px;
}

.like-button {
    border: none;
    background-color: transparent;
    font-size: 40px;
    cursor: pointer;
    transition: color 0.3s ease;
}

.liked .like-button {
    color: #007bff;
}

.like-button:hover {
    color: #0056b3;
}
</style>