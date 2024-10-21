<template>
    <div class="musical-card-container" id="musical-section">
      <RouterLink
        v-for="musical in musicals"
        :key="musical.musicalId"
        :to="{ name: 'MusicalDetailView', params: { id: musical.musicalId.toString() } }"
        class="musical-card"
      >
        <div class="rank-badge">{{ musical.rank }}</div>
        <img :src="musical.poster" :alt="musical.title" />
        <div class="musical-title">{{ musical.title + ' ' + '[' + musical.region + ']' }}</div>
      </RouterLink>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const musicals = ref([]);
  
  const fetchMusicals = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/performance/rank/month');
      const data = await response.json();
      if (data.success && Array.isArray(data.data)) {
        musicals.value = data.data;
      } else {
        console.error('Unexpected data structure:', data);
        musicals.value = [];
      }
    } catch (error) {
      console.error('Error fetching musicals:', error);
      musicals.value = [];
    }
  };
  
  onMounted(() => {
    fetchMusicals();
  });
  </script>
  
  <style scoped>
  .musical-card-container {
    display: flex;
    overflow-x: auto;
    height: fit-content;
    scroll-behavior: smooth;
    gap: 20px;
    width: 100%;
    padding: 20px 0;
    margin-top: 1.5%;
  }
  
  .musical-card {
    position: relative;
    flex: 0 0 calc(20% - 20px);
    box-sizing: border-box;
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
    gap: 13px;
    vertical-align: top;
    margin: 0;
    text-align: center;
  }
  
  .rank-badge {
    position: absolute;
    top: 10px;
    left: 10px;
    background-color: rgba(0, 0, 0, 0.7);
    opacity: 0.7;
    color: white;
    border-radius: 20%;
    width: 30px;
    height: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: 500;
    font-size: 1.7rem;
  }
  
  .musical-card img {
    width: 100%;
    height: 90%;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
  
  .musical-title {
    font-size: 1.9rem;
    text-align: center;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 200px;
    display: inline-block;
    font-weight: bold;
    color: black;
  }
  </style>