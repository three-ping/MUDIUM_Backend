<template>
      <table>
        <thead>
          <tr>
            <th>뮤지컬</th>
            <th>설명</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in recommendations" :key="index">
            <td>{{ item.musicalTitle }}</td>
            <td>{{ item.musicalDescription }}</td>
          </tr>
        </tbody>
      </table>
      
      <div v-if="loading" class="loading">Loading...</div>
      <div v-if="error" class="error">{{ error }}</div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const recommendations = ref([]);  // 데이터를 저장할 ref
  const loading = ref(false);  // 로딩 상태
  const error = ref(null);  // 에러 상태
  
  const fetchRecommendations = async () => {
    loading.value = true;
    error.value = null;
  
    try {
      const response = await fetch('http://localhost:8080/api/recommended-musical');
  
      if (!response.ok) {
        throw new Error('Failed to fetch recommendations');
      }
  
      const data = await response.json();  
      recommendations.value = data.data;  
      console.log(recommendations);
    } catch (err) {
      error.value = "데이터를 불러오는 데 실패하였습니다!";
    } finally {
      loading.value = false;  
    }
  };
  
  onMounted(() => {
    fetchRecommendations();
  });
  </script>
  
  <style scoped>

  table {
    width: 80%;
    border-collapse: collapse;
    margin-top: 1px;
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 50px;
    padding: 20px;
}
  
  th, td {
    border: 2px solid #ddd;
    padding: 15px;
    text-align: center;
  }
  
  th {
    background-color: #e2d4e6;
    font-weight: bold;
  }
  
  tr:hover {
    background-color: #f1e9f3;
  }
  
  .loading {
    text-align: center;
    font-size: 18px;
    color: #3498db;
  }
  
  .error {
    text-align: center;
    font-size: 18px;
    color: #e74c3c;
  }
  </style>