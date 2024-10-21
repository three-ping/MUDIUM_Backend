<template>
  <div class="musical-container">
    <input type="text" v-model="searchQuery" placeholder="검색어를 입력하세요" class="search-input" @input="handleInput" />
    <div v-if="searchResults.length > 0">
      <h1>검색 결과</h1>
      <div class="search-results-container">
        <RouterLink v-for="musical in searchResults" :key="musical.musicalId"
          :to="{ name: 'MusicalDetailView', params: { id: musical.musicalId.toString() } }" class="search-musical-card">
          <img :src="musical.poster" :alt="musical.title" class="musical-poster" />
          <div class="musical-title">{{ musical.title }}</div>
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import { debounce } from 'lodash';


const searchResults = ref([]);
const searchQuery = ref('');
const route = useRoute();

const fetchSearchResults = async (query) => {
  try {
    const sanitizedQuery = query.trim();
    const url = `http://localhost:8080/api/musical?title=${encodeURIComponent(sanitizedQuery)}&page=0&size=200`;

    const response = await axios.get(url);
    if (response.data && response.data.data && Array.isArray(response.data.data.content)) {
      searchResults.value = response.data.data.content;
    } else {
      searchResults.value = [];
    }
  } catch (error) {
    console.error('Error fetching search results:', error);
    searchResults.value = [];
  }
};

// Debounced version of fetchSearchResults
const debouncedFetchSearchResults = debounce(fetchSearchResults, 300);

// Handle input event
const handleInput = (event) => {
  debouncedFetchSearchResults(event.target.value);
};

// Initial fetch for all musicals
fetchSearchResults('');
</script>

<style scoped>
.search-input {
  width: 95%;
  padding: 10px;
  margin-bottom: 20px;
  margin-left: 3%;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.search-results-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr); /* 한 줄에 4개 */
  gap: 5px; /* 간격 조정 */
  width: 95%;
  grid-template-columns: repeat(5, 1fr);
  width: 100%;
  padding: 20px 0;
  margin-left: 1%;
}


.search-musical-card {
  position: relative;
  width: 250px;  /* 포스터 카드의 고정된 너비 */
  height: 350px; /* 포스터 카드의 고정된 높이 */
  text-align: center;
  margin-bottom: 25%;
}

.musical-title {
  margin-top: 1%; 
  white-space: nowrap;       
  overflow: hidden;         
  text-overflow: ellipsis;   
  font-size: 1.9rem;         
  color: black;  
  font-weight: bold;           
  max-width: 70%;         
  text-align: center;       
  margin-left: auto;         
  margin-right: auto;       
  display: block;           
}

.search-musical-card img {
  width: 100%;
  height: 100%;
  border-radius: 8px; 
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.musical-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  overflow-x: hidden; /* 수평 스크롤 숨김 */
  overflow-y: auto; /* 수직 스크롤 자동 */
  max-height: calc(100vh - 헤더_높이); /* 헤더 높이에 맞게 조정 */
  padding: 20px;
  object-fit: cover;  
  
}

.musical-container h1 {
  font-size: 1.5rem;
  margin-top: 1.5%;
}

</style>
