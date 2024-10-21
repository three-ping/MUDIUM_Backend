<template>
    <div class="review-wrapper">
        <div class="review-container">
            <h2 class="review-title">리뷰</h2>
            <div class="sort-options">
                <div class="sort-select">
                    <select v-model="sortOption" @change="sortReviews">
                        <option value="likes">좋아요 순</option>
                        <option value="highRating">높은 평점 순</option>
                        <option value="lowRating">낮은 평점 순</option>
                        <option value="recent">작성 순</option>
                    </select>
                </div>
                <button @click="openModal" class="review-button">리뷰 작성</button>
                <ReviewModal
                    :isOpen="isModalOpen"
                    :musicalTitle="musicalTitle"
                    :onClose="closeModal"
                    :onSubmit="(review) => handleSubmit(review)"
                />
            </div>
            <div class="review-list" v-infinite-scroll="loadMore">
                <div v-for="review in displayedReviews" :key="review.id" class="review-item">
                    <div class="review-header">
                        <div class="user-info">
                            <img :src="review.userProfile" alt="User avatar" class="avatar" />
                            <span class="user-nickname">{{ review.nickName }}</span>
                        </div>
                        <span class="rating">{{ review.rating }}</span>
                    </div>
                    <p class="review-content">
                        <router-link
                            :to="{ name: 'ReviewDetailView', params: { reviewId: review.reviewId } }"
                            class="text-link"
                        >
                            {{ review.content }}
                        </router-link>
                    </p>
                    <br />
                    <div class="review-footer">
                        <div class="reaction-container">
                            <span class="likes">
                                <img src="@/assets/images/like.svg" alt="좋아요" class="review-icon" />
                                <span class="count">{{ review.like }}</span>
                            </span>
                            <span class="comments">
                                <img src="@/assets/images/comment.svg" alt="댓글" class="review-icon" />
                                <span class="count">{{ review.comments }}</span>
                            </span>
                        </div>
                    </div>
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

const userStore = useUserStore();

const isModalOpen = ref(false);
const reviews = ref([]);
const sortOption = ref('likes');
const page = ref(1);
const perPage = 10;
const hasMore = ref(true);
const isLoading = ref(false);
const route = useRoute();
const musicalId = ref(route.params.musicalId);
const musicalTitle = ref('');
const userId = userStore.userInfo.user_id;
const access_token = userStore.userInfo.access_token;

const openModal = () => {
    isModalOpen.value = true;
};

const closeModal = () => {
    isModalOpen.value = false;
};

const handleSubmit = async (review) => {
    try {
        const response = await fetch(`http://localhost:8080/api/review/${musicalId.value}`, {
            method: 'POST',
            headers: {
                Authorization: `${access_token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId: userId, content: review }),
        });

        if (!response.ok) {
            throw new Error('Failed to save review');
        }

        const result = await response.json();
        console.log('Review saved successfully:', result);

        closeModal();
        page.value = 1; // 페이지 초기화
        reviews.length = 0; // 기존 리뷰 초기화
        hasMore.value = true; // 데이터 더 불러오기 활성화
        await fetchReviews(); // 리뷰 다시 불러오기
    } catch (error) {
        console.error('Error saving review:', error);
    }
};

const resetAndRefetchReviews = async () => {
    page.value = 1;
    reviews.value = [];
    hasMore.value = true;
    await fetchReviews();
};

const fetchReviews = async () => {
    if (isLoading.value || !hasMore.value) return;
    isLoading.value = true;

    try {
        const response = await fetch(
            `http://localhost:8080/api/review/${musicalId.value}?page=${page.value}&perPage=${perPage}&sort=${sortOption.value}`,
            {
                method: 'GET',
                headers: {
                    Authorization: `${access_token}`,
                    'Content-Type': 'application/json',
                },
            }
        );
        if (!response.ok) {
            throw new Error('Failed to fetch reviews');
        }
        const data = await response.json();

        if (data.data.length > 0) {
            musicalTitle.value = data.data[0].musicalTitle;
        }

        reviews.value = page.value === 1 ? data.data : [...reviews.value, ...data.data];
        hasMore.value = data.data.length === perPage;
        page.value++;
    } catch (error) {
        console.error('Error fetching reviews:', error);
    } finally {
        isLoading.value = false;
    }
};

const sortReviews = () => {
    resetAndRefetchReviews();
};

const loadMore = () => {
    if (hasMore.value && !isLoading.value) {
        fetchReviews();
    }
};

const truncateContent = (content) => {
    const maxLength = 100;
    const maxLines = 5;
    const lines = content.split('\n');
    let truncatedContent = '';

    for (let i = 0; i < Math.min(maxLines, lines.length); i++) {
        if (truncatedContent.length + lines[i].length > maxLength) {
            truncatedContent += lines[i].slice(0, maxLength - truncatedContent.length) + '...';
            break;
        }
        truncatedContent += lines[i] + '\n';
    }

    return truncatedContent.trim();
};

const displayedReviews = computed(() =>
    reviews.value.map((review) => ({
        ...review,
        userProfile: review.userProfile || '/src/assets/images/profile_default.svg',
        content: truncateContent(review.content),
    }))
);

console.log(displayedReviews);

onMounted(() => {
    fetchReviews();
});

useInfiniteScroll(document, loadMore, { distance: 10 });
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

.sort-options {
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.sort-select {
    flex: 0 1 auto;
    min-width: 140px;
    max-width: 150px;
}

.sort-select select {
    padding: 6px 10px;
    font-size: 0.9rem;
}

.review-button {
    padding: 8px 16px;
    font-size: 0.9rem;
    background-color: #9a70cc;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    max-width: 120px;
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

.user-info {
    display: flex;
    align-items: center;
}

.avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 15px;
}

.user-nickname {
    font-weight: bold;
}

.rating {
    color: #ffd700;
}

.review-content {
    margin-bottom: 10px;
}

.review-footer {
    display: flex;
    justify-content: flex-start;
}

.reaction-container {
    display: flex;
    align-items: center;
}

.likes,
.comments {
    display: flex;
    align-items: center;
    margin-right: 50px;
    min-width: 60px;
}

.review-icon {
    width: 20px;
    height: 20px;
    margin-right: 8px;
}

.count {
    font-size: 0.9rem;
}

.text-link {
    text-decoration: none;
    color: inherit;
    white-space: pre-wrap;
}

.text-link:hover {
    text-decoration: none;
}

* {
        font-size: 2rem !important;
    }

button {
    box-shadow: none;
}

@media (max-width: 768px) {
    .review-container {
        width: 90%;
    }

    .sort-options {
        flex-direction: column;
        align-items: stretch;
    }

    .sort-select,
    .review-button {
        margin-bottom: 10px;
        width: 100%;
    }

    .review-header {
        flex-direction: column;
        align-items: flex-start;
    }

    .user-info {
        margin-bottom: 10px;
    }

    .reaction-container {
        flex-direction: column;
        align-items: flex-start;
    }

    .likes,
    .comments {
        margin-right: 0;
        margin-bottom: 10px;
    }
}
</style>
