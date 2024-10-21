<template>
    <div class="pagination">
        <div class="first-page-button">
            <button @click="changePage(1)" :disabled="pageNumber === 1"><<</button>
        </div>
        <div class="front-page-button">
            <button @click="changePage(pageNumber - 1)" :disabled="pageNumber === 1"><</button>
        </div>
        <template v-for="page in pages" :key="page">
            <div class="number-page-button">
                <button 
                    class="page-button" 
                    :class="{ active: page === pageNumber }"
                    @click="changePage(page)"
                >
                    {{ page }}
                </button>
            </div>
        </template>
        <div class="back-page-button">
            <button @click="changePage(pageNumber + 1)" :disabled="pageNumber === totalPageNumber">></button>
        </div>
        <div class="last-page-button">
            <button @click="changePage(totalPageNumber)" :disabled="pageNumber === totalPageNumber">>></button>
        </div>
    </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, toRefs, watch } from 'vue';

const props = defineProps({
    requestURL: String,
    pageNumber: Number,
    totalPageNumber: Number
});

const emitUpdatePage = defineEmits(['updatePageNumber']);

const { pageNumber, totalPageNumber } = toRefs(props);
const pages = ref([]);

const changePage = (newPageNumber) => {
    if (newPageNumber > 0 && newPageNumber <= totalPageNumber.value) {
        emitUpdatePage('updatePageNumber', newPageNumber);
    }
};

const updatePages = () => {
    const startPage = Math.floor((pageNumber.value - 1) / 10) * 10 + 1;
    const endPage = Math.min(startPage + 9, totalPageNumber.value);
    pages.value = [];
    for (let i = startPage; i <= endPage; i++) {
        pages.value.push(i);
    }
};

watch([pageNumber, totalPageNumber], updatePages);

updatePages();
</script>

<style scoped>

.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 20px;
    font-family: Arial, sans-serif;
}

.pagination button {
    background-color: white;
    border: 1px solid #D53EC6;
    color: #D53EC6;
    padding: 8px 12px;
    margin: 0 4px;
    cursor: pointer;
    transition: all 0.3s ease;
    border-radius: 4px;
    font-size: 14px;
}

.pagination button:hover {
    background-color: #D53EC6;
    color: white;
}

.pagination button:disabled {
    background-color: #f0f0f0;
    border-color: #ccc;
    color: #999;
    cursor: not-allowed;
}

.pagination .number-page-button button {
    min-width: 15px;
}

.pagination .number-page-button button.active {
    background-color: #D53EC6;
    color: white;
}

.first-page-button button,
.last-page-button button {
    font-weight: bold;
}
button {
    width: 2vw;
    height: 4vh;
}
</style>