<template>
  <section class="container-fluid">
    <!-- 뮤지컬 상세 정보 -->
    <div class="musical-detail-container">
      <div class="poster-section">
        <img :src="musical.poster" alt="Poster" />
      </div>
      <div class="detail-section">
        <h2>{{ musical.title || '제목 정보 없음' }}</h2>
        <p class="rating">{{ musical.rating }} 관람가</p>
        <p class="production">제작사: {{ musical.production || '정보 없음' }}</p>
        <p v-if="averageScope" class="average-scope">
          평균 별점: {{ averageScope.scope }}
          ({{ averageScope.people }} 참여)
        </p>
        <p v-else class="average-scope">평균 별점: 정보 없음</p>
        <div class="star-rating">
          <StarRating 
            :rating="scope ? scope.scope : 0"
            :musical-id="musical.musicalId"
            v-if="userStore.userInfo.isLoggedIn"
            :userId="userId"
            @set-rating="setRating"
          />
          <StarRating 
            :rating="scope ? scope.scope : 0"
            :musicalId="musical.musicalId"
            v-else
            @set-rating="setRating"
          />
        </div>
      </div>
    </div>

        <!-- 리뷰 리스트 -->
        <div class="review-container">
            <h3>리뷰</h3>
            <button v-if="reviews.length > 0" @click="goToReviewPage">더보기</button>
            <button v-else @click="openModal">리뷰 쓰기</button>
        </div>
        <div v-if="reviews.length > 0" class="review-list">
            <ul>
                <li v-for="review in limitedReviews" :key="review.reviewId">
                    <ReviewCard :content="review.content" :scope="review.scope" :nickName="review.nickName" />
                </li>
            </ul>
        </div>
        <ReviewModal
            v-if="isModalOpen"
            :isOpen="isModalOpen"
            :musicalTitle="musical.title"
            @close="closeModal"
            @submit="(review) => handleSubmit(review)"
        />

        <!-- 공연 정보 섹션 -->
        <div class="performance-list">
            <h3>공연 정보</h3>
            <div class="performance-grid">
                <div v-for="performance in performanceList" :key="performance.performanceId" class="performance-item">
                    <img :src="performance.poster" alt="Performance Poster" />
                    <p>{{ performance.region }}</p>
                </div>
            </div>
        </div>
    </section>
</template>

<script setup>
import { ref, onMounted, defineProps, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import StarRating from '@/components/scope/StarRating.vue';
import ReviewCard from '@/components/review/ReviewCard.vue'; // 새로운 컴포넌트 임포트
import ReviewModal from '@/components/review/ReviewModal.vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const route = useRoute();
const router = useRouter();
const props = defineProps({ id: String }); 
const musical = ref({});
const averageScope = ref({});
const performanceList = ref([]);
const reviews = ref([]);
const isModalOpen = ref(false);
const access_token = userStore.userInfo.access_token;

const goToReviewPage = () => {
    router.push(`/review/view/${musical.value.musicalId}`);
};

const openModal = () => {
    isModalOpen.value = true;
};

const closeModal = () => {
    isModalOpen.value = false;
};

const handleSubmit = async (review) => {
    try {
        const response = await fetch(`http://localhost:8080/api/review/${musical.value.musicalId}`, {
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
        await fetchMusicalDetail(); // 리뷰 다시 불러오기
    } catch (error) {
        console.error('Error saving review:', error);
    }
};

const scope = ref({});
const userId = userStore.userInfo.user_id || parseInt(userStore.userInfo.userId);

const setRating = (newRating) => {
  scope.value.scope = newRating;
};

const limitedReviews = computed(() => {
    return reviews.value.slice(0, 4);
});

const fetchPerformanceList = async (id) => {
  try {
    const response = await fetch(`http://localhost:8080/api/performance/${id}`);
    const data = await response.json();
    performanceList.value = data.data;
  } catch (error) {
    console.error('Error fetching performance list:', error);
  }
};

const fetchMyScope = async (id) => {
  console.log("userId: ", userId);
  try {
    const response = await fetch(`http://localhost:8080/api/scope/${userId}/${id}`);
    const data = await response.json();
    scope.value = data.data;
    console.log("MyScope: ", scope.value);
  } catch (error) {
    console.error('Error fetching scope:', error);
  }
};

const fetchReviews = async (id) => {
  try {
    const response = await fetch(`http://localhost:8080/api/review/${id}`);
    const data = await response.json();
    reviews.value = data.data;
    console.log(reviews.value);
  } catch (error) {
    console.error('Error fetching reviews:', error);
  }
};

const fetchAverageScope = async (id) => {
    try {
        const response = await fetch(`http://localhost:8080/api/scope/${id}`);
        const data = await response.json();

    if (data.success && data.data) {
      console.log('찾은 별점:', data.data.scope);
      averageScope.value = {
        scope: data.data.scope,
        people: data.data.people,
        musicalId: data.data.musicalId
      };
    } else {
      averageScope.value = null;
    }
  } catch (error) {
    console.error('평균 별점 가져오기 오류:', error);
  }
};

const fetchMusicalDetail = async () => {
    const id = route.params.id;
    try {
        const response = await fetch(`http://localhost:8080/api/musical/${id}`);
        const data = await response.json();
        musical.value = {
            title: data.data.title,
            rating: data.data.rating,
            production: data.data.production,
            poster: data.data.poster,
            synopsys: data.data.synopsys,
            reviewVideos: data.data.reviewVideos,
            musicalId: data.data.musicalId,
        };
        console.log(musical.value);

        await fetchAverageScope(musical.value.musicalId);
        await fetchPerformanceList(musical.value.musicalId);
        await fetchReviews(musical.value.musicalId);
    } catch (error) {
        console.error('Error fetching musical detail:', error);
    }
    await fetchAverageScope(musical.value.musicalId);
    await fetchPerformanceList(musical.value.musicalId);
    await fetchReviews(musical.value.musicalId);
    await fetchMyScope(musical.value.musicalId);
  };

onMounted(() => {
    fetchMusicalDetail();
});
</script>

<style scoped>
.container-fluid {
    flex-direction: column;
    background-color: #fff;
    color: #000;
    padding: 20px;
    width: 95%;
    max-width: 1200px;
    margin: 50px auto;
}

.musical-detail-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    background-color: #f0f0fb;
    border-radius: 10px;
    margin-bottom: 30px;
}

.poster-section {
    flex: -1 0 40%;
    margin: -5% 10% -5% 0%;
}

.poster-section img {
    box-shadow: 0 0 10px -5px;
    width: 300px;
    height: 400px;
    object-fit: cover;
    border-radius: 10px;
}

.detail-section {
    flex: 1;
}

.detail-section h2 {
    font-size: 2rem;
    margin-bottom: 15px;
}

.detail-section p {
    margin-bottom: 8px;
    font-size: 1rem;
}

.star-rating {
    margin-top: 15px;
}

.review-container {
    display: flex;
    justify-content: space-between;
    margin-top: 10vh;
    font-size: 2rem;
}

.review-container button {
  box-shadow: none;
}

.review-list {
    margin-top: 30px;
    margin-bottom: 30px;
}

.review-list h3 {
    font-size: 1.5rem;
    margin-bottom: 15px;
}

.review-list ul {
    list-style-type: none;
    padding: 0;
}

.review-list li {
  list-style: none;
    margin-bottom: 10px;
    font-size: 1rem;
}

.performance-list {
    width: 100%;
    margin-top: 30px;
    margin-left: 20px;
}

.performance-list h3 {
    font-size: 1.5rem;
    margin-bottom: 15px;
}

.performance-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 20px;
    justify-items: start; /* 왼쪽 정렬 */
}

.performance-item {
    text-align: center;
}

.performance-item img {
    box-shadow: 0 0 10px -4px;
    width: 230px; /* 고정 너비 */
    height: 310px; /* 고정 높이 */
    object-fit: cover; /* 이미지 비율 유지 */
    border-radius: 5px;
    margin-bottom: 10px;
}

.performance-item p {
    font-size: 1rem;
}
</style>

