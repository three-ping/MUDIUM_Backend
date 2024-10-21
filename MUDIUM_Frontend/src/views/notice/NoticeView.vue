<template>
    <div class="board-container">
      <div class="board-actions">
        <div class="search-bar">
          <select v-model="searchType">
            <option value="TITLE">제목</option>
            <option value="CONTENT">내용</option>
          </select>
          <input class="search-box" type="text" v-model="searchQuery" placeholder="검색어를 입력하세요">
          <button @click="search" class="search-button" :disabled="!searchQuery.trim()">검색</button>
          <button @click="goToList" class="back-button">목록</button>
        </div>
        <template v-if="userRole == `ROLE_ADMIN`">
          <button @click="createBoard" class="create-button">글 쓰기</button>
        </template>
      </div>
      <div class="board">
        <table class="board-table">
          <thead class="board-table-header">
            <tr class="board-tr">
              <th>번호</th>
              <th>제목</th>
              <th>작성일</th>
            </tr>
          </thead>   
          <tbody>
            <tr v-for="pageItem in pageItems" :key="pageItem.id" class="board-tr">
              <td class="td-id" data-label="번호">{{ pageItem.id }}</td>
              <td class="td-title" data-label="제목">
                <router-link :to="{ name: 'NoticeDetailView', params: { id: pageItem.id } }">
                  {{ pageItem.title }}
                </router-link>
              </td>
              <td class="td-createdAt" data-label="작성일">{{ convertToKoreanTime(pageItem.createdAt) }}</td>
            </tr>
          </tbody>     
        </table>
      </div> 
      <Paging 
        :requestURL="requestURL" 
        :pageNumber="pageNumber" 
        :totalPageNumber="totalPageNumber" 
        @updatePageNumber="updatePageNumber" />
    </div>
  </template>
  
  <script setup>
  import Paging from '@/components/notice/pagination.vue';
  import { ref, reactive, onMounted, watch } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  
  const router = useRouter();
  const route = useRoute();
  const requestURL = `api/notice`;
  const pageNumber = ref(1);
  const totalPageNumber = ref(0);
  const pageItems = reactive([]);
  const searchType = ref('TITLE');
  const searchQuery = ref('');
  // const userRole = ref("ROLE_ADMIN");
  const userRole = ref("ROLE_MEMBER");
  
  // 현재 페이지 번호를 저장하는 함수
  const saveCurrentPage = () => {
    localStorage.setItem('lastVisitedPage', pageNumber.value.toString());
  };
  
  // 저장된 페이지 번호를 불러오는 함수
  const loadSavedPage = () => {
    const savedPage = localStorage.getItem('lastVisitedPage');
    return savedPage ? parseInt(savedPage) : 1;
  };
  
  const fetchPageData = async () => {
    const response = await fetch(`http://localhost:8080/${requestURL}?page=${pageNumber.value}`, {
      method: "GET"
    });
    const responseDTO = await response.json();
    pageItems.length = 0;
    pageItems.push(...responseDTO.data.content);
    totalPageNumber.value = responseDTO.data.totalPages;
    saveCurrentPage(); 
  };
  
  const queryPageData = async () => {
    const response = await fetch(`http://localhost:8080/${requestURL}?searchType=${searchType.value}&searchQuery=${searchQuery.value}&page=${pageNumber.value}`, {
      method: "GET"
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
    if(pageItems.length == 0) {
      router.push("view/no-args");
    }
  };
  
  const createBoard = () => {
    router.push('create');
  };
  
  // 목록 버튼 클릭 시 호출되는 함수
  const goToList = () => {
    const savedPage = loadSavedPage();
    pageNumber.value = savedPage;
    fetchPageData();
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
    const savedPage = loadSavedPage();
    pageNumber.value = savedPage;
    fetchPageData();
  });
  
  // 라우트 변경 감지
  watch(() => route.name, (newRouteName) => {
    if (newRouteName === 'BoardListView') {
      const savedPage = loadSavedPage();
      pageNumber.value = savedPage;
      fetchPageData();
    }
  });
  </script>
  
  <style scoped>
  .board-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    max-width: 1200px;
    margin: 20px auto;
    padding: 0 20px;
  }
  
  .board-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    margin-bottom: 30px; /* 게시판과 검색 부분 간격 증가 */
  }
  
  .search-bar {
    display: flex;
    gap: 10px;
    width: 80%; /* 검색 바 너비 증가 */
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
  
  .search-button,
  .back-button,
  .create-button {
    padding: 8px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    color: white;
    font-weight: bold;
    min-width: 80px;
  }
  
  .back-button {
    background-color: #6EABE1;
    margin-right: 20px; /* 목록 버튼과 글쓰기 버튼 사이 간격 증가 */
  }
  
  .search-button {
    background-color: #9A70CC;
  }
  
  .create-button {
    background-color: #D53EC6;
  }
  
  .search-button,
  .create-button,
  .back-button {
    padding: 8px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    color: white;
    font-weight: bold;
    min-width: 80px;
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
    background-color: #279977;
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

  .board-table th, .board-table td {
  padding: 12px 15px;
  border-bottom: 1px solid #e0e0e0;
}

/* 번호 열 너비 설정 */
.td-id {
  width: 10%; /* 열 너비 10%로 설정 */
  text-align: center;
}

/* 제목 열 너비 설정 */
.td-title {
  width: 70%; /* 열 너비 70%로 설정 */
  text-align: left;
}

/* 작성일 열 너비 설정 */
.td-createdAt {
  width: 20%; /* 열 너비 20%로 설정 */
  text-align: right;
}

  
  @media screen and (max-width: 768px) {
    .board-table, .board-table tbody, .board-table tr, .board-table td {
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
  
    .search-bar {
      flex-direction: column;
    }
  
    .search-bar select,
    .search-bar input,
    .search-button,
    .back-button {
      width: 100%;
    }
  
    .create-button {
      width: 100%;
      margin-top: 10px;
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
      margin-right: 0; /* 모바일에서는 마진 제거 */
      margin-bottom: 15px; /* 대신 아래쪽 마진 추가 */
    }
  }
  button {
    box-shadow: none;
}
*{
    font-size: 2rem;
}
  </style>