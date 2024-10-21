<template>
    <div class="board-container">

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
        </table>
        <div class="no-results">
        <p class="no-results-message">검색 결과가 없습니다.</p>
        <button class="back-to-board-button" @click="goBackToBoard">게시글 목록으로 돌아가기</button>
        </div>
    </div>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goBackToBoard = () => {
    router.push('/board');
};
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
    font-family: Arial, sans-serif;
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

.search-button,
.back-button,
.create-button,
.back-to-board-button {
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
    margin-right: 20px;
}

.search-button {
    background-color: #9A70CC;
}

.create-button {
    background-color: #D53EC6;
}

.board {
    width: 100%;
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

.board-table th {
    padding: 12px 15px;
    text-align: left;
    color: white;
    font-weight: bold;
    text-transform: uppercase;
}

.no-results {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 40px;
}

.no-results-message {
    font-size: 18px;
    margin-bottom: 20px;
}

.back-to-board-button {
    background-color: #6EABE1;
}

.back-to-board-button:hover {
    background-color: #9acbf6;
}

@media screen and (max-width: 768px) {
    .board-actions {
    flex-direction: column;
    align-items: stretch;
    }

    .search-bar {
    width: 100%;
    margin-bottom: 15px;
    flex-direction: column;
    }

    .search-bar select,
    .search-bar input,
    .search-button,
    .back-button,
    .create-button,
    .back-to-board-button {
    width: 100%;
    margin-bottom: 10px;
    }

    .back-button {
    margin-right: 0;
    }
}
* {
    font-size: 2rem;
}

</style>