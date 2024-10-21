<template>
    <div>
        <div v-if="loading" class="text-center py-8">
            <p class="text-xl">Loading reviews...</p>
        </div>
        <div v-else-if="error" class="text-center py-8 text-red-600">
            <p class="text-xl">{{ error }}</p>
        </div>
        <div v-else class="cards-grid">
            <div
                v-for="review in reviews"
                :key="review.reviewId"
                class="card-container"
                @mouseenter="rotateCard"
                @mouseleave="unrotateCard"
            >
                <div class="card">
                    <!-- Front side - Only poster -->
                    <div class="card-side card-front">
                        <img :src="review.poster" :alt="review.musicalTitle" class="card-image" />
                    </div>
                    <!-- Back side -->
                    <div class="card-back flex flex-col items-center justify-between">
                        <div v-if="review.secretReview" class="mt-4 mb-4 text-lg">
                            <p>{{ review.secretReview }}</p>
                        </div>
                        <button
                            @click="openReviewModal(review)"
                            class="mt-auto p-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition duration-300"
                        >
                            {{ review.secretReview ? '비밀 리뷰 수정' : '비밀 리뷰 쓰기' }}
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <ReviewModal
            :isOpen="isModalOpen"
            :musicalTitle="currentReview?.musicalTitle"
            :isEditing="!!currentReview?.secretReview"
            :initialReview="currentReview?.secretReview"
            @close="closeReviewModal"
            @submit="submitReview"
        />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import ReviewModal from '@/components/review/ReviewModal.vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const reviews = ref([]);
const loading = ref(true);
const error = ref(null);
const isModalOpen = ref(false);
const currentReview = ref(null);

const fetchReviews = async (userId) => {
    try {
        const response = await axios.get(`/api/review/users/${userId}`);
        if (response.data.success) {
            reviews.value = response.data.data;
        } else {
            throw new Error(response.data.error || 'Failed to fetch reviews');
        }
    } catch (err) {
        error.value = err instanceof Error ? err.message : 'An unexpected error occurred';
    } finally {
        loading.value = false;
    }
};

const formatDate = (timestamp) => {
    if (!timestamp) return 'N/A';
    const date = new Date(timestamp);
    return date.toLocaleDateString();
};

const rotateCard = (event) => {
    event.currentTarget.querySelector('.card').style.transform = 'rotateY(180deg)';
};

const unrotateCard = (event) => {
    event.currentTarget.querySelector('.card').style.transform = 'rotateY(0deg)';
};

const openReviewModal = (review) => {
    currentReview.value = review;
    isModalOpen.value = true;
};

const closeReviewModal = () => {
    isModalOpen.value = false;
    currentReview.value = null;
};

const submitReview = async (reviewContent) => {
    if (currentReview.value) {
        // Update the current review's secretReview property
        currentReview.value.secretReview = reviewContent;

        // Prepare the review data for the API call
        const reviewData = {
            userId: userId,
            content: reviewContent,
        };

        try {
            // Make the API call to save the secret review
            const response = await fetch(`http://localhost:8080/api/secretreview/${musicalId.value}`, {
                method: 'POST',
                headers: {
                    Authorization: `${access_token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(reviewData),
            });

            // Check if the response is ok (status code in the range 200-299)
            if (!response.ok) throw new Error('리뷰 저장에 실패했습니다.');

            // Parse the JSON response
            const result = await response.json();
            console.log('리뷰 저장 성공:', result);

            // Update the local state to reflect the saved review
            const index = reviews.value.findIndex((r) => r.reviewId === currentReview.value.reviewId);
            if (index !== -1) {
                // Update the review in the local state
                reviews.value[index] = { ...reviews.value[index], secretReview: reviewContent };
            }

            // Close the modal and refresh the review list
            closeModal();
            page.value = 1; // Reset the page
            await fetchReviews(); // Fetch the updated reviews

        } catch (error) {
            console.error('리뷰 저장 중 오류 발생:', error);
        }
    }
    closeReviewModal(); // Close the review modal
};



// Assuming we're getting the userId from a prop or a global state
const userId = userStore.userInfo.user_id; // Replace this with the actual way you're getting the userId

onMounted(() => {
    fetchReviews(userId);
});
</script>

<style scoped>
.cards-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(40rem, 1fr));
    gap: 2rem;
    padding: 2rem;
}

.card-container {
    width: 40rem;
    height: 60rem;
    perspective: 1000px;
}

.card {
    position: relative;
    width: 100%;
    height: 100%;
    transition: transform 0.6s;
    transform-style: preserve-3d;
}

.card-side {
    position: absolute;
    width: 100%;
    height: 100%;
    backface-visibility: hidden;
    border-radius: 0.5rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.card-front {
    background-color: white;
    overflow: hidden;
}

.card-back {
    background-color: #ffffff;
    transform: rotateY(180deg);
    backface-visibility: hidden;
}

.card-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
}

button {
    box-shadow: none;
}

.card-back {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
	padding: 4vh;
	/* width: 70%; */
	height: 100%;
}

.card-back p {
    font-size: 3rem; /* Adjust this value as needed */
    margin-top: 1rem;
    margin-bottom: 1rem;
}

.mt-4 {
	width: 100%;
}

</style>
