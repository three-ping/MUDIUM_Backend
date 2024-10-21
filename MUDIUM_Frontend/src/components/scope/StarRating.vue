<template>
  <div class="row-box">
    <div
      v-for="idx in 5"
      :key="idx"
      class="star-div"
    >
      <span v-if="currentScore >= idx - 0.5 && currentScore < idx">
        <span class="half-star">&#9733;</span>
        <span class="half-star-cover">&#9734;</span>
      </span>
      <span v-else-if="currentScore >= idx">
        &#9733; <!-- 꽉 찬 별 -->
      </span>
      <span v-else>
        &#9734; <!-- 빈 별 -->
      </span>
      <!-- 왼쪽 반 클릭 이벤트 -->
      <div
        class="left"
        @click="handleStarClick(idx - 0.5)"
        @mouseover="handleLeftHalfEnter(idx)"
        @mouseleave="handleStarLeave"
      ></div>
      <!-- 오른쪽 반 클릭 이벤트 -->
      <div
        class="right"
        @click="handleStarClick(idx)"
        @mouseover="handleRightHalfEnter(idx)"
        @mouseleave="handleStarLeave"
      ></div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, defineProps, defineEmits } from 'vue';
import axios from 'axios';

const props = defineProps({
  musicalId: Number,
  rating: {
    type: Number,
    default: 0
  },
  userId: {
    type: Number,
    default: null
  }
});

const emit = defineEmits(['setRating']);

const currentScore = ref(props.rating);
const scoreFixed = ref(props.rating);

watch(() => props.rating, (newRating) => {
  currentScore.value = newRating;
  scoreFixed.value = newRating;
});

const handleLeftHalfEnter = (idx) => {
  currentScore.value = idx - 0.5;
};

const handleRightHalfEnter = (idx) => {
  currentScore.value = idx;
};

const handleStarClick = async (score) => {
  scoreFixed.value = score;
  currentScore.value = score;
  emit('setRating', score);

  console.log("Selected Rating: ", score);
  console.log("musicalId: ", props.musicalId);

  try {
    const userId = props.userId;
    console.log("userId: ", userId);
    console.log("별점: ", score);
    
    const response = await axios.post(
      `http://localhost:8080/api/scope/create/${userId}/${props.musicalId}`,
      { scope: score },
      {
        headers: {
          'Content-Type': 'application/json'
        }
      }
    );
    
    console.log('별점 저장 성공:', response.data);
  } catch (error) {
    console.error('별점 저장 오류:', error.response ? error.response.data : error.message);
  }
};

const handleStarLeave = () => {
  currentScore.value = scoreFixed.value;
};
</script>

<style>
.row-box {
  display: flex;
  gap: 5px;
}

.row-box .star-div {
  display: inline-block;
  position: relative;
  font-size: 2rem;
  cursor: pointer;
}

.row-box .star-div:hover {
  color: gold; 
}

.half-star {
  position: absolute;
  top: 0;
  left: 0;
  width: 50%;
  overflow: hidden;
}

.left {
  position: absolute;
  top: 0;
  left: 0;
  width: 50%;
  height: 100%;
  cursor: pointer;
}

.right {
  position: absolute;
  top: 0;
  right: 0;
  width: 50%;
  height: 100%;
  cursor: pointer;
}

.star-div .right:hover {
  color: gold;
}

.star-div .left:hover {
  color: gold;
}

.row-box .star-div span {
  font-size: 4rem;
  color: gold; 
  text-shadow: 
    2px 2px 0px #c3a617; 
  transform: rotate(0deg);
  transition: transform 0.3s ease-in-out, text-shadow 0.3s ease-in-out;
  cursor: pointer;
}

.row-box .star-div span:hover {
  transform: rotate(15deg) scale(1.1); 
  color: gold; 
  text-shadow: 
    3px 3px 0px #c3a617, 
    6px 6px 15px rgba(0, 0, 0, 0.7);

}
</style>
