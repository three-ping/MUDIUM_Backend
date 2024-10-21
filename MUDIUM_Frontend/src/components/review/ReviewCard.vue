<template>
  <div class="review-card">
    <div class="review-header">
      <span class="nickname">{{ nickName }}</span>
      <div class="star-rating">
        <span
          v-for="star in 5"
          :key="star"
          class="star"
        >
          <span v-if="star <= scope">★</span>
          <span v-else-if="star - 0.5 <= scope">★</span>
          <span v-else>☆</span>
        </span>
      </div>
    </div>
    <p class="review-content">{{ content }}</p>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  content: String,
  scope: Number,
  nickName: String
});
</script>

<style scoped>
.review-card {
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 10px;
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nickname {
  font-weight: bold;
}

.star-rating {
  display: flex;
  font-size: 0.8rem; /* 작은 별 크기 */
}


.star.filled {
  color: gold;
}

.star.half {
  color: gold;
  position: relative;
}

.star.half::before {
  content: attr(data-split);
  position: absolute;
  top: 0;
  left: 0;
  width: 50%;
  overflow: hidden;
}

.review-content {
  margin-top: 5px;
  font-size: 0.9rem;
}
</style>
