 <template>
    <table>
      <thead>
        <tr>
          <th>관람 매너</th>
          <th>설명</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in term" :key="index">
          <td>{{ item.etiquette }}</td>
          <td>{{ item.etiquetteDescription }}</td>
        </tr>
      </tbody>
    </table>
    
    <div v-if="loading" class="loading">Loading...</div>
    <div v-if="error" class="error">{{ error }}</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const term = ref([]);  
const loading = ref(false); 
const error = ref(null); 

const fetchTerms = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await fetch('http://localhost:8080/api/etiquette');

    if (!response.ok) {
      throw new Error('Failed to fetch terms');
    }

    const data = await response.json(); 
    term.value = data.data;  
    console.log(term);
  } catch (err) {
      error.value = "데이터를 불러오는 데 실패하였습니다!";
  } finally {
    loading.value = false; 
  }
};

onMounted(() => {
  fetchTerms();
});
</script>

<style scoped>

table {
  width: 70%;
  border-collapse: collapse;
  margin-top: 1px;
  margin-left: auto;
  margin-right: auto;
  padding: 20px;
}

th, td {
  border: 1px solid #ddd;
  padding: 10px;
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