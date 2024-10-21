<template>
  <div class="board-container">
    <div class="board-actions">
      <button @click="createBoard" class="create-button">글 쓰기</button>

      <div class="search-bar">
        <select v-model="searchType">
          <option value="TITLE">제목</option>
          <option value="NICKNAME">작성자</option>
          <option value="CONTENT">내용</option>
        </select>
        <input class="search-box" type="text" v-model="searchQuery" placeholder="검색어를 입력하세요">
        <button @click="search" class="search-button" :disabled="!searchQuery.trim()">검색</button>
        <button @click="fetchPageData" class="back-button">목록</button>
      </div>
    </div>
    <div class="board">
      <table class="board-table">
        <thead class="board-table-header">
          <tr class="board-tr">
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>좋아요</th>
            <th>조회수</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="pageItem in pageItems" :key="pageItem.boardId" class="board-tr">
            <td class="td-id" data-label="번호">{{ pageItem.boardId }}</td>
            <td class="td-title" data-label="제목">
              <router-link :to="{ name: 'BoardDetailView', params: { id: pageItem.boardId } }">
                {{ pageItem.title }} [{{ pageItem.comments }}]
              </router-link>
            </td>
            <td class="td-nickname" data-label="작성자">{{ pageItem.nickname }}</td>
            <td class="td-createdAt" data-label="작성일">{{ convertToKoreanTime(pageItem.createdAt) }}</td>
            <td class="td-like" data-label="좋아요">{{ pageItem.boardLike }}</td>
            <td class="td-viewCount" data-label="조회수">{{ pageItem.viewCount }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <Paging :requestURL="requestURL" :pageNumber="pageNumber" :totalPageNumber="totalPageNumber"
      @updatePageNumber="updatePageNumber" />
  </div>
</template>

<script setup>
import Paging from '@/components/board/pagination.vue';
import Detail from '@/views/board/BoardDetailView.vue';
import { ref, reactive, onMounted, defineProps } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const requestURL = `api/board`;
const pageNumber = ref(1);
const totalPageNumber = ref(0);
const pageItems = reactive([]);
const searchType = ref('TITLE');
const searchQuery = ref('');
const props = defineProps({
  access_token: String
});
const access_token = props.access_token;


const fetchPageData = async () => {
  const response = await fetch(`http://localhost:8080/${requestURL}?page=${pageNumber.value}`, {
    method: "GET",
    headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
  });
  const responseDTO = await response.json();
  pageItems.length = 0;
  pageItems.push(...responseDTO.data.content);
  totalPageNumber.value = responseDTO.data.totalPages;
};

const queryPageData = async () => {
  const response = await fetch(`http://localhost:8080/${requestURL}?searchType=${searchType.value}&searchQuery=${searchQuery.value}`, {
    method: "GET",
    headers: {
        'Authorization': `Bearer ${access_token}`, 
        'Content-Type': 'application/json'  
    }
  });
  const responseDTO = await response.json();
  pageItems.length = 0;
  pageItems.push(...responseDTO.data.content);
  totalPageNumber.value = responseDTO.data.totalPages;
};

const updatePageNumber = (newPageNumber) => {
  pageNumber.value = newPageNumber;
  fetchPageData();
};

const search = async () => {
  pageNumber.value = 1;
  await queryPageData();
  if (pageItems.length == 0) {
    router.push("view/no-args");
  }
};

const createBoard = () => {
  router.push('create');
};

function convertToKoreanTime(timestamp) {
  const date = new Date(timestamp);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  return `${year}.${month}.${day} ${hours}:${minutes}:${seconds}`;
}

onMounted(() => {
  setTimeout(() => {
    fetchPageData();
  }, 100);
});

</script>

<style scoped>
button { 
    box-shadow: none;
}
.board-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 1200px;
  margin: 5vh auto;
  padding: 0 20px;
}

.board-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-bottom: 30px;
}

.search-bar {
  display: flex;
  gap: 10px;
  width: 80%;
}

.search-bar select,
.search-bar input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-box {
  flex-grow: 1;
}



.back-button {
  background-color: #6EABE1;
}

.search-button {
  background-color: #9A70CC;
}


.create-button {
  background-color: #D53EC6;
  align-self: flex-end;
}

.board {
  width: 100%;
  overflow-x: auto;
}

.board-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
}

.board-table-header {
  background-color: #D53EC6;
}

.board-table th,
.board-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.board-table th {
  color: white;
  font-weight: bold;
  text-transform: uppercase;
}

.board-tr:last-child td {
  border-bottom: none;
}

.board-tr:nth-child(even) {
  background-color: #f9f9f9;
}

.td-title a {
  color: #333;
  text-decoration: none;
}

.td-title a:hover {
  text-decoration: underline;
}

@media screen and (max-width: 768px) {

  .board-table,
  .board-table tbody,
  .board-table tr,
  .board-table td {
    display: block;
  }

  .board-table thead {
    display: none;
  }

  .board-table tr {
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 8px;
    overflow: hidden;
  }

  .board-table td {
    text-align: right;
    padding-left: 50%;
    position: relative;
  }

  .board-table td::before {
    content: attr(data-label);
    position: absolute;
    left: 6px;
    width: 45%;
    padding-right: 10px;
    white-space: nowrap;
    font-weight: bold;
    text-align: left;
  }

  .td-title {
    text-align: left !important;
    padding-left: 15px !important;
  }

  .td-title::before {
    display: none;
  }

  .board-actions {
    flex-direction: column;
    align-items: stretch;
  }

  .search-bar {
    width: 100%;
    margin-bottom: 15px;
  }

  .search-bar select,
  .search-bar input,
  .search-button,
  .back-button {
    width: 100%;
    margin-bottom: 10px;
  }

  .create-button {
    width: 100%;
  }

  .back-button {
    margin-right: 0;
    margin-bottom: 15px;
  }
}
* {
    font-size: 2rem;
}

</style>