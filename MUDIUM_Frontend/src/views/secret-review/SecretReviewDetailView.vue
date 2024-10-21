<template>
    <div class="review-wrapper">
        <div class="review-container">
            <!-- 리뷰 헤더 -->
            <div class="review-header">
                <!-- 뮤지컬 사진과 이름이 나오는 부분 -->
                <img :src="secretReview.musicalProfile" alt="Musical Profile Image" class="user-avatar" />
                <div class="user-info">
                    <h3>
                        <span>
                            {{ secretReview.musicalTitle }} 
                            <span v-if="secretReview.isPremium" class="premium-badge">P</span>
                        </span>
                        <span class="dropdown-wrapper">
                            <span @click="toggleDropdown" class="dropdown-icon">
                                <img src="@/assets/images/threeDots.svg" alt="threeDots">
                            </span>
                            <div v-if="dropdownVisible" class="dropdown-menu">
                                <button @click="editReview" class="update">수정</button>
                                <button @click="deleteReview" class="delete">삭제</button>
                            </div>
                        </span>
                    </h3>
                    <p>{{ formatDate(secretReview.createdAt) }}</p>
                </div>
            </div>
            <br>

            <!-- 리뷰 내용 -->
            <p class="review-content">{{ secretReview.content }}</p>

            <!-- 수정 모달 -->
            <div v-if="showModal">
                <ReviewModal
                    :isOpen="showModal"
                    :musicalTitle="secretReview.musicalTitle"
                    :isEditing="true"
                    :onClose="closeModal"
                    :onSubmit="handleReviewSubmit"
                    :initialReview="secretReview.content"
                />
            </div>

            <!-- 삭제 모달 -->
            <div v-if="showDeleteModal">
                <ReviewDeleteModal
                    :isOpen="showDeleteModal"
                    @close="closeModal"
                    @submit="handleDeleteReviewSubmit"
                >
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
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

// 라우터 훅
const route = useRoute();
const router = useRouter();

// 리뷰 데이터
const secretReview = reactive({
    secretReviewId: null,
    userNickname: '',
    content: '',
    createdAt: '',
    like: 0,
    musicalTitle: '',
    musicalProfile: '',
    userProfile: '',
});

// 라우트 파라미터
const musicalId = ref(route.params.musicalId);
const secretReviewId = ref(route.params.secretReviewId);

// 상태 변수
const showComments = ref(false);
const dropdownVisible = ref(false);
const showModal = ref(false);
const showDeleteModal = ref(false);
const userId = userStore.userInfo.user_id;
const access_token = userStore.userInfo.access_token;

// 드롭다운 토글
const toggleDropdown = () => {
    dropdownVisible.value = !dropdownVisible.value;
};

// 클릭 외부 감지
const handleClickOutside = (event) => {
    const dropdown = document.querySelector('.dropdown-wrapper');
    if (dropdown && !dropdown.contains(event.target)) {
        dropdownVisible.value = false; // 드롭다운 닫기
    }
};

// 리뷰 데이터 가져오기
const fetchReview = async () => {
    try {
        const response = await fetch(`http://localhost:8080/api/secretreview/${secretReviewId.value}?userId=${userId}`,
            {
                method: 'GET',
            headers: {
                    'Authorization': `${access_token}`,
                    'Content-Type': 'application/json',
                },
            }
        );
        if (!response.ok) throw new Error('리뷰를 불러오는 데 실패했습니다.');
        const data = await response.json();
        Object.assign(secretReview, data.data[0]);
    } catch (error) {
        console.error('리뷰를 불러오는 데 실패했습니다:', error);
    }
};

// 수정 모달 열기
const editReview = () => {
    dropdownVisible.value = false;
    showModal.value = true; // 모달 표시
};

// 모달 닫기
const closeModal = () => {
    showModal.value = false;
    showDeleteModal.value = false;
}

// 수정된 리뷰 제출 처리
const handleReviewSubmit = async (updatedReview) => {
    const reviewData = {
        userId: userId,
        content: updatedReview,
    };

    try {
        const response = await fetch(`http://localhost:8080/api/secretreview/${secretReviewId.value}`, {
            method: 'PUT',
            headers: {
                'Authorization': `${access_token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(reviewData),
        });
        if (!response.ok) throw new Error('리뷰 수정에 실패했습니다.');

        const result = await response.json();
        console.log('리뷰 저장 성공:', result);
        closeModal(); // 모달 닫기
        await fetchReview(); // 리뷰 새로 고침
    } catch (error) {
        console.error('리뷰 업데이트 중 오류 발생:', error);
    }
};

// 삭제 모달 열기
const deleteReview = () => {
    dropdownVisible.value = false;
    showDeleteModal.value = true; // 삭제 모달 표시
};

// 리뷰 삭제 처리
const handleDeleteReviewSubmit = async () => {
    try {
        const response = await fetch(`http://localhost:8080/api/secretreview/${secretReviewId.value}?userId=${userId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `${access_token}`,
                'Content-Type': 'application/json',
            },
        });
        if (!response.ok) throw new Error('리뷰 삭제에 실패했습니다.');

        const result = await response.json();
        console.log('리뷰 삭제 성공:', result);
        closeModal(); // 모달 닫기
        await fetchReview(); // 리뷰 새로 고침
        router.push(`/secret-review/view`); // 리뷰 목록으로 리다이렉트
    } catch (error) {
        console.error('리뷰 삭제 중 오류 발생:', error);
    }
};

// 날짜 포맷팅 함수
const formatDate = (timestamp) => {
    const now = new Date();
    const reviewDate = new Date(timestamp);
    const diffTime = now - reviewDate;

    const diffSeconds = Math.floor(diffTime / 1000);
    const diffMinutes = Math.floor(diffSeconds / 60);
    const diffHours = Math.floor(diffMinutes / 60);
    const diffDays = Math.floor(diffHours / 24);
    const diffMonths = Math.floor(diffDays / 30);
    const diffYears = Math.floor(diffDays / 365);

    if (diffYears > 0) return `${diffYears}년 전`;
    if (diffMonths > 0) return `${diffMonths}개월 전`;
    if (diffDays > 0) return `${diffDays}일 전`;
    if (diffHours > 0) return `${diffHours}시간 전`;
    if (diffMinutes > 0) return `${diffMinutes}분 전`;
    return '방금 전';
};

// 컴포넌트가 마운트될 때
onMounted(() => {
    fetchReview(); // 리뷰 데이터 가져오기
    document.addEventListener('click', handleClickOutside); // 클릭 외부 감지
});

// 컴포넌트 언마운트 시 이벤트 리스너 제거
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
    margin: 20px auto;
    /* box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); */
    border-radius: 8px;
    /* border: 1px solid #e0e0e0; */
}

.review-header {
    display: flex;
    align-items: center;
    margin-bottom: 12px;
}

.review-content {
    margin: 16px 0;
    font-size: 16px;
    white-space: pre-wrap;
}

.user-avatar {
    width: 48px;
    height: 48px;
    /* border-radius: 50%; */
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
    font-size: 20px;
    display: flex;
    justify-content: space-between; /* 닉네임과 점 3개 아이콘 사이 공간 확보 */
}

.user-info p {
    margin: 0;
    font-size: 14px;
    color: #666;
}

.delete-confirmation {
    text-align: center; /* 가운데 정렬 */
    font-size: 20px; /* 폰트 크기 조정 */
    margin: 0; /* 기본 마진 제거 */
    padding: 10px 0; /* 상하 패딩 추가 (필요시) */
}

/* 드롭다운 스타일 */
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
    background-color: #f0f0f0; /* 호버 효과 */
}

* {
        font-size: 2rem;
    }

button {
    box-shadow: none;
}

/* 반응형 디자인 */
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

    .delete-confirmation {
        font-size: 16px;
    }
}
</style>