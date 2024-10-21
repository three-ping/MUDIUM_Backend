<template>
    <div class="review-wrapper">
        <div class="review-container">
            <div class="review-header">
                <img :src="review.userProfile" alt="User Profile Image" class="user-avatar" />
                <div class="user-info">
                    <h3>
                        <span>
                            {{ review.nickName }} <span v-if="review.isPremium" class="premium-badge">P</span>
                        </span>
                        <span v-if="review.userId === userId" class="dropdown-wrapper">
                            <span @click="toggleDropdown" class="dropdown-icon">
                                <img src="@/assets/images/threeDots.svg" alt="threeDots" />
                            </span>
                            <div v-if="dropdownVisible" class="dropdown-menu">
                                <button @click="editReview" class="update">수정</button>
                                <button @click="deleteReview" class="delete">삭제</button>
                            </div>
                        </span>
                    </h3>
                    <p>{{ formatDate(review.createdAt) }}</p>
                </div>
            </div>
            <div class="movie-info">
                <h2>{{ review.musicalTitle }}</h2>
            </div>
            <br />
            <p class="review-content">{{ review.content }}</p>
            <br />
            <div class="review-footer">
                <div class="review-stats">
                    <span class="likes">좋아요 {{ review.like }}</span>
                    <span class="comments">댓글 {{ review.comments }}</span>
                </div>
                <br><br>
                <div class="review-actions">
                    <!-- <ReviewLike class="action-button" :likeCount="likeCount" :isLiked="isLiked" :reviewId="reviewId" :userId="userId" /> -->
                    <button @click="handleLike" class="action-button">
                        <img :src="likeIcon" alt="Like" class="action-icon" />
                        좋아요
                    </button>
                    <button @click="handleComment" class="action-button">
                        <img src="@/assets/images/comment.svg" alt="Comment" class="action-icon" />
                        댓글
                    </button>
                </div>
            </div>
            <div v-if="showComments" class="comments-section">
                <div v-for="comment in review.commentList" :key="comment.id" class="comment">
                    <img :src="comment.userAvatar" alt="Commenter Avatar" class="commenter-avatar" />
                    <div class="comment-content">
                        <h4>{{ comment.userName }}</h4>
                        <p>{{ comment.content }}</p>
                        <span>좋아요 {{ comment.likes }}</span>
                    </div>
                </div>
            </div>

            <!-- 수정 모달 -->
            <div v-if="showModal">
                <ReviewModal
                    :isOpen="showModal"
                    :musicalTitle="review.musicalTitle"
                    :isEditing="true"
                    :onClose="closeModal"
                    :onSubmit="(review) => handleReviewSubmit(review)"
                    :initialReview="review.content"
                />
            </div>

            <!-- 삭제 모달 -->
            <div v-if="showDeleteModal">
                <ReviewDeleteModal :isOpen="showDeleteModal" @close="closeModal" @submit="handleDeleteReviewSubmit">
                    <p class="delete-confirmation">삭제하시겠습니까?</p>
                </ReviewDeleteModal>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, reactive, onUnmounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import ReviewModal from '../../components/review/ReviewModal.vue';
import ReviewDeleteModal from '../../components/review/ReviewDeleteModal.vue';
import ReviewLike from '@/components/review/ReviewLike.vue';
import likeIconPath from '@/assets/images/like.svg';
import likeFillIconPath from '@/assets/images/like-fill.svg';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const route = useRoute();
const router = useRouter();
const review = reactive({
    reviewId: null,
    nickName: '',
    content: '',
    createdAt: '',
    updatedAt: '',
    like: '',
    musicalTitle: '',
    userProfile: '/src/assets/images/profile_default.svg',
    userId: '',
});
const musicalId = ref(route.params.musicalId);
const reviewId = ref(route.params.reviewId);
const showComments = ref(false);
const dropdownVisible = ref(false); // 드롭다운!!
const showModal = ref(false);
const showDeleteModal = ref(false);
const userId = userStore.userInfo.user_id;
const access_token = userStore.userInfo.access_token;

const likeCount = ref(review.like);
const isLiked = ref(false);
const likeIcon = ref(likeIconPath); // 초기 아이콘

const toggleDropdown = () => {
    dropdownVisible.value = !dropdownVisible.value; // 드롭다운 토글!!
};

// 클릭 이벤트 핸들러
const handleClickOutside = (event) => {
    const dropdown = document.querySelector('.dropdown-wrapper');
    if (dropdown && !dropdown.contains(event.target)) {
        dropdownVisible.value = false; // 드롭다운 닫기
    }
};

const fetchReview = async () => {
    try {
        const response = await fetch(`http://localhost:8080/api/review/${musicalId.value}/${reviewId.value}`, {
            method: 'GET',
            headers: {
                    'Authorization': `${access_token}`,
                    'Content-Type': 'application/json',
                },
        });
        if (!response.ok) {
            throw new Error('리뷰를 불러오는 데 실패했습니다.');
        }
        const data = await response.json();
        Object.assign(review, data.data[0]);

        checkLikeStatus();
    } catch (error) {
        console.error('리뷰를 불러오는 데 실패했습니다:', error);
    }
};

const editReview = () => {
    dropdownVisible.value = false; // 드롭다운 닫기
    // 여기서 수정 로직을 구현
    showModal.value = true; // 모달 표시
};

// 모달 닫기
const closeModal = () => {
    showModal.value = false;
    showDeleteModal.value = false;
};

// 수정된 리뷰 제출 처리
const handleReviewSubmit = async (updatedReview) => {
    const reviewData = {
        userId: userId,
        content: updatedReview,
    };

    try {
        const response = await fetch(`http://localhost:8080/api/review/${musicalId.value}/${reviewId.value}`, {
            method: 'PUT',
            headers: {
                'Authorization': `${access_token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(reviewData), // 업데이트된 내용 전송
        });
        if (!response.ok) {
            throw new Error('리뷰 수정에 실패했습니다.');
        }

        const result = await response.json();
        console.log('리뷰 저장 성공:', result);

        // 수정 후 리뷰 데이터를 새로 고침
        closeModal(); // 제출 후 모달 닫기
        await fetchReview();
    } catch (error) {
        console.error('리뷰 업데이트 중 오류 발생:', error);
    }
    // window.location.reload();
};

const deleteReview = () => {
    dropdownVisible.value = false; // 드롭다운 닫기
    // 여기서 삭제 로직을 구현
    showDeleteModal.value = true;
};

const handleDeleteReviewSubmit = async () => {
    try {
        const response = await fetch(
            `http://localhost:8080/api/review/${musicalId.value}/${reviewId.value}?userId=${userId}`,
            {
                method: 'DELETE',
                headers: {
                    'Authorization': `${access_token}`,
                    'Content-Type': 'application/json',
                },
            }
        );
        if (!response.ok) {
            throw new Error('리뷰 삭제에 실패했습니다.');
        }

        const result = await response.json();
        console.log('리뷰 삭제 성공:', result);

        closeModal();
        await fetchReview();

        router.push(`/review/view/${musicalId.value}`);
    } catch (error) {
        console.error('리뷰 삭제 중 오류 발생:', error);
    }
};

const formatDate = (timestamp) => {
    const now = new Date();
    const reviewDate = new Date(timestamp); // Use the timestamp directly

    const diffTime = now - reviewDate; // Difference in milliseconds
    const diffSeconds = Math.floor(diffTime / 1000); // Convert to seconds
    const diffMinutes = Math.floor(diffSeconds / 60); // Convert to minutes
    const diffHours = Math.floor(diffMinutes / 60); // Convert to hours
    const diffDays = Math.floor(diffHours / 24); // Convert to days
    const diffMonths = Math.floor(diffDays / 30); // Roughly calculate months
    const diffYears = Math.floor(diffDays / 365); // Roughly calculate years

    if (diffYears > 0) {
        return `${diffYears}년 전`;
    } else if (diffMonths > 0) {
        return `${diffMonths}개월 전`;
    } else if (diffDays > 0) {
        return `${diffDays}일 전`;
    } else if (diffHours > 0) {
        return `${diffHours}시간 전`;
    } else if (diffMinutes > 0) {
        return `${diffMinutes}분 전`;
    } else {
        return '방금 전'; // Just now
    }
};

const checkLikeStatus = async () => {
    try {
        const response = await fetch(`http://localhost:8080/api/review-like/${reviewId.value}/${userId}`, { 
            method: "GET",
            headers: {
                    'Authorization': `${access_token}`,
                    'Content-Type': 'application/json',
                },
        });
        if (!response.ok) {
            throw new Error('좋아요 상태를 확인하는 데 실패했습니다.');
        }
        const data = await response.json();
        isLiked.value = data.data;
        likeIcon.value = isLiked.value ? likeFillIconPath : likeIconPath;
    } catch (error) {
        console.error('좋아요 상태를 확인하는 데 실패했습니다:', error);
    }
};

const handleLike = async () => {
    // 즉시 UI 업데이트
    isLiked.value = !isLiked.value;
    likeIcon.value = isLiked.value ? likeFillIconPath : likeIconPath;
    review.like += isLiked.value ? 1 : -1;

    try {
        const url = `http://localhost:8080/api/review-like/${reviewId.value}`;
        const method = isLiked.value ? 'POST' : 'DELETE';
        const body = JSON.stringify(userId);

        const response = await fetch(url, {
            method: method,
            headers: {
                'Authorization': `${access_token}`,
                'Content-Type': 'application/json',
            },
            body: body,
        });

        if (!response.ok) {
            throw new Error(isLiked.value ? '좋아요 추가 실패' : '좋아요 삭제 실패');
        }

        console.log(isLiked.value ? '좋아요 추가 성공' : '좋아요 삭제 성공');
    } catch (error) {
        console.error('좋아요 처리 중 오류 발생:', error);
        // 오류 발생 시 UI 상태 되돌리기
        isLiked.value = !isLiked.value;
        likeIcon.value = isLiked.value ? likeFillIconPath : likeIconPath;
        review.like += isLiked.value ? 1 : -1;
    }
};

console.log(review);
console.log(review.userId);
console.log(userId);

onMounted(() => {
    fetchReview();
    // checkIsLiked();
    document.addEventListener('click', handleClickOutside);
});
// 컴포넌트가 언마운트될 때 이벤트 리스너 제거
onUnmounted(() => {
    document.removeEventListener('click', handleClickOutside);
});
</script>

<style scoped>
.review-wrapper {
    display: flex;
    justify-content: center;
    width: 100%;
    padding: 20px;
}

.review-container {
    width: 80%;
    max-width: 1000px;
    padding: 16px;
    margin-bottom: 20px;
    /* box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); */
    border-radius: 8px;
}

.review-header {
    display: flex;
    align-items: center;
    margin-bottom: 12px;
}

.user-avatar {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    margin-right: 12px;
}

.user-info {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.user-info h3 {
    margin: 0;
    font-size: 16px;
    display: flex;
    justify-content: space-between;
}

.user-info p {
    margin: 0;
    font-size: 14px;
    color: #666;
}

.premium-badge {
    background-color: #ffd700;
    color: #000;
    padding: 2px 4px;
    border-radius: 4px;
    font-size: 12px;
    margin-left: 4px;
}

.movie-info h2 {
    margin: 0;
    font-size: 20px;
}

.review-content {
    margin: 16px 0;
    font-size: 16px;
    white-space: pre-wrap;
}

.review-footer {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
    margin-top: 12px;
}

.review-stats {
    font-size: 14px;
    color: #666;
    margin-bottom: 8px;
    display: block;
}

.review-stats .likes,
.review-stats .comments {
    display: inline-block;
    min-width: 100px;
}

.review-actions {
    display: flex;
    justify-content: center;
    width: 100%; /* 부모 요소의 너비를 채움 */
    margin: 0 auto; /* 중앙 정렬 */
    gap: 100px;
    margin-top: 8px;
}

.action-button {
    display: inline-flex;
    align-items: center;
    margin-right: 16px;
    background: none;
    border: none;
    color: #666;
    font-size: 14px;
    cursor: pointer;
    box-shadow: none;
}

.action-icon {
    width: 16px;
    height: 16px;
    margin-right: 4px;
}

.review-actions button {
    background: none;
    border: none;
    cursor: pointer;
    margin-left: 16px;
    font-size: 14px;
}

.comments-section {
    margin-top: 16px;
    border-top: 1px solid #e0e0e0;
    padding-top: 16px;
}

.comment {
    display: flex;
    margin-bottom: 12px;
}

.commenter-avatar {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    margin-right: 8px;
}

.comment-content h4 {
    margin: 0;
    font-size: 14px;
}

.comment-content p {
    margin: 4px 0;
    font-size: 14px;
}

.comment-content span {
    font-size: 12px;
    color: #666;
}

.delete-confirmation {
    text-align: center;
    font-size: 20px;
    margin: 0;
    padding: 10px 0;
}

.dropdown-wrapper {
    position: relative;
    display: inline-block;
}

.dropdown-icon {
    cursor: pointer;
    margin-left: 8px;
}

.dropdown-menu {
    position: absolute;
    top: 100%;
    right: 0;
    background-color: white;
    border: 1px solid #e0e0e0;
    border-radius: 4px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
    z-index: 10;
    min-width: 100px;
    padding: 8px 0; /* 상하 패딩 추가 (버튼 간격 확보) */
    box-shadow: none;
}

.dropdown-menu button {
    display: block;
    width: 100%;
    padding: 12px 16px;
    border: none;
    background: none;
    text-align: center;
    cursor: pointer;
    font-size: 16px;
    font-weight: normal;
    box-shadow: none;
}

.dropdown-menu button.update {
    color: black;
}

.dropdown-menu button.delete {
    color: red;
}

.dropdown-menu button:hover {
    background-color: #f0f0f0;
}

* {
        font-size: 2rem;
    }

button {
    box-shadow: none;
}

@media (max-width: 768px) {
    .review-container {
        width: 95%;
        padding: 12px;
    }

    .user-avatar {
        width: 40px;
        height: 40px;
    }

    .user-info h3 {
        font-size: 14px;
    }

    .user-info p {
        font-size: 12px;
    }

    .movie-info h2 {
        font-size: 16px;
    }

    .review-content {
        font-size: 14px;
    }

    .review-footer {
        flex-direction: column;
        align-items: flex-start;
    }

    .review-stats {
        margin-bottom: 8px;
    }

    .review-actions button {
        margin-left: 0;
        margin-right: 16px;
    }
}
</style>
