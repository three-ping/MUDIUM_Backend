<template>
    <div class="review-wrapper">
        <div class="review-container">
            <h2 class="review-title">비밀 리뷰</h2>
            <!-- 모달창 테스트 -->
            <button @click="openModal">비밀리뷰 작성</button>
            <ReviewModal
                :isOpen="isModalOpen"
                :musicalTitle="musicalTitle"
                :onClose="closeModal"
                :onSubmit="handleSubmit"
            />
            <div class="review-list" v-infinite-scroll="loadMore">
                <div v-for="secretReview in displayedReviews" :key="secretReview.id" class="review-item">
                    <div class="review-header">
                        <div class="review-info">
                            <!-- <img :src="secretReview.userProfile" alt="User avatar" class="avatar" /> -->
                            <img :src="secretReview.musicalProfile" alt="MusicalProfile" class="avatar" />
                            <span class="musical-title">{{ secretReview.musicalTitle }}</span>
                        </div>
                        <span class="rating">{{ secretReview.rating }}</span>
                    </div>
                    <p class="review-content">
                        <router-link
                            :to="{
                                name: 'SecretReviewDetailView',
                                params: { musicalId: secretReview.musicalId, secretReviewId: secretReview.secretReviewId },
                            }"
                            class="text-link"
                        >
                            {{ secretReview.content }}
                        </router-link>
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useInfiniteScroll } from '@vueuse/core';
import { useRoute } from 'vue-router';
import ReviewModal from '@/components/review/ReviewModal.vue';
import { useUserStore } from '@/stores/userStore';

// Store 및 변수 설정
const userStore = useUserStore();
const reviews = ref([]);
const page = ref(1);
const perPage = 10;
const hasMore = ref(true);
const isLoading = ref(false);
const musicalTitle = ref('k-water와함께하는뮤지컬갈라콘서트');
const musicalId = ref(9);
const musicalProfile = ref('');
const userId = userStore.userInfo.user_id;
const access_token = userStore.userInfo.access_token;

// 모달 관련
const isModalOpen = ref(false);
const openModal = () => { isModalOpen.value = true; };
const closeModal = () => { isModalOpen.value = false; };

// 리뷰 목록 조회
const fetchReviews = async () => {
    if (isLoading.value || !hasMore.value) return;
    isLoading.value = true;

    try {
        const response = await fetch(
            `http://localhost:8080/api/secretreview?page=${page.value}&perPage=${perPage}&userId=${userId}`,
            {
                method: 'GET',
                headers: {
                    Authorization: `${access_token}`,
                    'Content-Type': 'application/json',
                },
            }
        );

        if (!response.ok) throw new Error('리뷰를 불러오는 데 실패했습니다.');
        
        const data = await response.json();
        console.log(data.data);

        // data가 null 또는 data.data가 null인 경우 체크
        if (!data || !data.data) {
            console.error('응답 데이터가 유효하지 않습니다:', data);
            hasMore.value = false; // 더 이상 가져올 데이터가 없다고 설정
            return;
        }

        // 리뷰가 없을 때 처리
        if (data.data.length === 0) {
            hasMore.value = false; // 더 이상 가져올 데이터가 없다고 설정
            return;
        }

        // 페이지가 1이면 리뷰 목록 초기화
        if (page.value === 1) {
            reviews.value = data.data;
        } else {
            reviews.value.push(...data.data); // 새로운 리뷰 추가
        }

        // 받은 리뷰 수가 perPage보다 적으면 더 이상 데이터가 없는 것으로 간주
        if (data.data.length < perPage) {
            hasMore.value = false;
        }

        page.value++; // 다음 페이지 번호 증가
    } catch (error) {
        console.error('리뷰를 불러오는 데 실패했습니다:', error);
    } finally {
        isLoading.value = false; // 로딩 상태 초기화
    }
};

// 리뷰 작성 처리
const handleSubmit = async (review) => {
    const reviewData = {
        userId: userId,
        content: review,
    };

    try {
        const response = await fetch(`http://localhost:8080/api/secretreview/${musicalId.value}`, {
            method: 'POST',
            headers: {
                Authorization: `${access_token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(reviewData),
        });

        if (!response.ok) throw new Error('리뷰 저장에 실패했습니다.');

        const result = await response.json();
        console.log('리뷰 저장 성공:', result);

        // 모달 닫기 및 리뷰 리스트 새로고침
        closeModal();
        page.value = 1; // 페이지 초기화
        reviews.value = []; // 기존 리뷰 초기화
        hasMore.value = true; // 데이터 더 불러오기 활성화
        await fetchReviews(); // 리뷰 다시 불러오기
    } catch (error) {
        console.error('리뷰 저장 중 오류 발생:', error);
    }
};

// 무한 스크롤 설정
const loadMore = () => {
    if (hasMore.value && !isLoading.value) {
        fetchReviews();
    }
};

// 리뷰 내용 트렁케이션 함수
const truncateContent = (content) => {
    const maxLength = 100; // 최대 100자
    const maxLines = 5; // 최대 5줄

    // 줄 바꿈 문자를 기준으로 문자열 분할
    const lines = content.split('\n');

    // 줄 수와 글자 수를 기준으로 잘릴 내용 계산
    let truncatedContent = '';
    let currentLines = 0;

    for (const line of lines) {
        // 현재 줄 수가 최대 줄 수를 초과할 경우 "..." 추가 후 종료
        if (currentLines >= maxLines) {
            truncatedContent += '...';
            break;
        }

        // 현재 줄의 길이가 남은 글자 수를 초과할 경우
        if (truncatedContent.length + line.length > maxLength) {
            const remainingChars = maxLength - truncatedContent.length;
            truncatedContent += line.slice(0, remainingChars) + '...'; // 남은 글자 수만큼 추가 후 "..." 추가
            break;
        }

        // 현재 줄 추가
        truncatedContent += line + '\n';
        currentLines++;
    }

    return truncatedContent.trim(); // 앞뒤 공백 제거
};

// 계산된 리뷰 리스트
const displayedReviews = computed(() => {
    return reviews.value.map(secretReview => ({
        ...secretReview,
        content: truncateContent(secretReview.content),
    }));
});

console.log(displayedReviews);

// 컴포넌트 마운트 시 리뷰 불러오기
onMounted(() => {
    fetchReviews();
});

// 무한 스크롤 설정
useInfiniteScroll(
    document,
    () => {
        loadMore();
    },
    { distance: 10 }
);
</script>

<style scoped>
.review-wrapper {
    display: flex;
    justify-content: center;
    width: 100%;
    padding: 20px;
}

.review-container {
    width: 70%;
    max-width: 1200px;
}

.review-title {
    font-size: 1.5rem;
    font-weight: bold;
    margin-bottom: 1rem;
}

.review-list {
    margin-top: 20px;
}

.review-item {
    border: 2px solid #9a70cc;
    border-radius: 8px;
    padding: 15px;
    margin-bottom: 15px;
}

.review-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
}

.review-info {
    display: flex;
    align-items: center;
}

.avatar {
    width: 40px;
    height: 40px;
    /* border-radius: 50%; */
    margin-right: 10px;
}

.musical-title {
    font-weight: bold;
    margin: 0 10px;
}

.rating {
    color: #ffd700;
}

.review-content {
    margin-bottom: 10px;
}

.text-link {
    text-decoration: none;
    color: inherit;
    white-space: pre-wrap;
}

.text-link:hover {
    text-decoration: underline;
}

* {
        font-size: 2rem;
    }

button {
    box-shadow: none;
}

/* 미디어 쿼리: 반응형 디자인 */
@media (max-width: 768px) {
    .review-container {
        width: 90%;
    }

    .review-header {
        flex-direction: column;
        align-items: flex-start;
    }

    .review-info {
        margin-bottom: 10px;
    }
}
</style>
