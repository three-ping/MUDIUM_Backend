<template>
      <div class="calendar-header">
        <button @click="previousMonth" class="icon-button">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-left" viewBox="0 0 16 16">
          <path d="M10 12.796V3.204L4.519 8zm-.659.753-5.48-4.796a1 1 0 0 1 0-1.506l5.48-4.796A1 1 0 0 1 11 3.204v9.592a1 1 0 0 1-1.659.753"/>
        </svg>        </button>
        <h2>{{ currentMonth }} {{ currentYear }}</h2>
        <button @click="nextMonth" class="icon-button">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-right" viewBox="0 0 16 16">
          <path d="M6 12.796V3.204L11.481 8zm.659.753 5.48-4.796a1 1 0 0 0 0-1.506L6.66 2.451C6.011 1.885 5 2.345 5 3.204v9.592a1 1 0 0 0 1.659.753"/>
        </svg>        </button>
    <div class="image-change-buttons">
      <button @click="nextBackground">이미지 변경</button>
    </div>
      </div>
<div class="container">
  <div :style="{ backgroundImage: `url(${currentBackgroundImage})` }" class="background-container">
    <div class="calendar-wrapper">
      <div class="calendar-grid">
        <div class="day" v-for="day in days" :key="day">{{ day }}</div>
        <div 
          class="date" 
          v-for="(date, index) in datesInMonth" 
          :key="index"
          :class="{ 'today': isToday(date) }"
          @click="selectDate(date)"
        >
          {{ date.getDate() }}
        </div>
      </div>
    </div>
  </div>
</div>
</template>


<script setup>
import { ref, computed, onMounted } from 'vue';

const currentDate = ref(new Date());
const selectedDate = ref(null);

const backgroundImages = ref({});
const currentBackgroundIndex = ref(0);

const currentBackgroundImage = computed(() => {
  const imageNames = Object.keys(backgroundImages.value);
  if (imageNames.length === 0) return '';
  const currentImageName = imageNames[currentBackgroundIndex.value];
  return `data:image/jpeg;base64,${backgroundImages.value[currentImageName]}`;
});

const fetchImages = async () => {
  try {
    const response = await fetch('http://localhost:8080/calendar-theme/images');
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    backgroundImages.value = data;
    console.log('Images fetched successfully');
  } catch (error) {
    console.error('Error fetching images:', error);
  }
};

onMounted(fetchImages);

const currentMonth = computed(() => currentDate.value.toLocaleString('default', { month: 'long' }));
const currentYear = computed(() => currentDate.value.getFullYear());

const days = ['M', 'T', 'W', 'T', 'F', 'S', 'S'];

const datesInMonth = computed(() => {
  const year = currentDate.value.getFullYear();
  const month = currentDate.value.getMonth();
  const firstDayOfMonth = new Date(year, month, 1);
  const lastDayOfMonth = new Date(year, month + 1, 0);
  const dates = [];

  for (let i = firstDayOfMonth.getDay() - 1; i > 0; i--) {
    dates.push(new Date(year, month, -i + 1));
  }

  for (let i = 1; i <= lastDayOfMonth.getDate(); i++) {
    dates.push(new Date(year, month, i));
  }

  return dates;
});

const isToday = (date) => {
  const today = new Date();
  return (
    date.getDate() === today.getDate() &&
    date.getMonth() === today.getMonth() &&
    date.getFullYear() === today.getFullYear()
  );
};

const previousMonth = () => {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() - 1, 1);
};

const nextMonth = () => {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, 1);
};

const previousBackground = () => {
  currentBackgroundIndex.value = (currentBackgroundIndex.value - 1 + Object.keys(backgroundImages.value).length) % Object.keys(backgroundImages.value).length;
};

const nextBackground = () => {
  currentBackgroundIndex.value = (currentBackgroundIndex.value + 1) % Object.keys(backgroundImages.value).length;
};

const selectDate = (date) => {
  selectedDate.value = date;
  console.log('Selected date:', date);
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center; 
  width: 100%;
}
.image-change-buttons {
  border: none;
  border-radius: 10px;
  width: auto;
  gap: 30px;
}
.background-container {
  position: relative; /* Set position to relative for absolute positioning of buttons */
  width: auto;
  height: auto;
  background-size: cover;
  background-position: center top; 
  background-repeat: no-repeat;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.calendar-wrapper {
  background: rgba(255, 255, 255, 0.6);
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 100%;
  height: auto;
  box-sizing: border-box; 
  z-index: 1; 
  transparent:
}


.calendar-header {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 20px;
  font-size: 2rem;
}

.day, .date {
  text-align: center;
  padding: 5px;
}

.today {
  background-color: #d0e1ff;
  border-radius: 60%;
}
</style>