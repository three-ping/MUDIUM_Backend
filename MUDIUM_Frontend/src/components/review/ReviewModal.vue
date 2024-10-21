<template>
    <div v-if="isOpen" class="modal-overlay" @click="closeModal">
        <div class="modal-content" @click.stop>
            <button @click="closeModal" class="close-button">&times;</button>
            <div class="modal-header">
                <img src="@/assets/images/MudiumText.svg" alt="MudiumLogo" class="logo">
            </div>
            <div class="modal-body">
                <h3>{{ musicalTitle }}</h3>
                <textarea
                    v-model="review"
                    placeholder="리뷰를 작성해주세요..."
                    :maxlength="MAX_CHARS"
                    rows="8"
                ></textarea>
                <div class="char-count">{{ charCount }} / {{ MAX_CHARS }}</div>
            </div>
            <div class="modal-footer">
                <button @click="closeModal" class="cancel-button">취소</button>
                <button @click="submitReview" :disabled="!review.trim()" class="submit-button">
                    {{ isEditing ? '수정' : '등록' }}
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, watch, defineProps, defineEmits } from 'vue';

const props = defineProps({
    isOpen: Boolean,
    musicalTitle: String,
    isEditing: Boolean,
    initialReview: {
        type: String,
        default: ''
    }
});

const emit = defineEmits(['close', 'submit']);

const MAX_CHARS = 10000;
const review = ref(props.initialReview || '');
const charCount = computed(() => review.value.length);

watch(() => props.isOpen, (newValue) => {
    if (newValue) {
        review.value = props.initialReview;
    } else {
        review.value = '';
    }
});

const closeModal = () => {
    emit('close');
};

const submitReview = () => {
    emit('submit', review.value);
    review.value = '';
    closeModal();
};
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background-color: white;
    padding: 40px; /* 패딩을 40px로 늘림 */
    border-radius: 12px;
    width: 90%; /* 너비는 유지하되, 필요에 따라 조정 가능 */
    max-width: 800px; /* 최대 너비를 800px로 늘림 */
    position: relative;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-header {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;
}

.logo {
    max-width: 150px;
    height: auto;
}

.close-button {
    position: absolute;
    top: 10px;
    right: 15px;
    background: none;
    border: none;
    font-size: 28px;
    cursor: pointer;
    color: #888;
    transition: color 0.3s ease;
    box-shadow: none;
}

.close-button:hover {
    color: #333;
}

.modal-body h3 {
    font-size: 1.2rem;
    margin-bottom: 15px;
    color: #333;
}

.modal-body textarea {
    width: 100%;
    margin-bottom: 10px;
    padding: 12px;
    border: none;
    background-color: #f5f5f5;
    border-radius: 8px;
    resize: vertical;
    font-size: 16px;
}

.char-count {
    text-align: right;
    color: #888;
    font-size: 14px;
    margin-bottom: 15px;
}

.modal-footer {
    display: flex;
    justify-content: flex-end;
    gap: 15px;
    box-shadow: none;
}

.modal-footer button {
    padding: 10px 20px;
    cursor: pointer;
    border: none;
    border-radius: 6px;
    font-size: 16px;
    transition: background-color 0.3s ease;
    box-shadow: none;
}

.cancel-button {
    background-color: #f0f0f0;
    color: #333;
}

.submit-button {
    background-color: #9a70cc;
    color: white;
}

.cancel-button:hover {
    background-color: #e0e0e0;
}

.submit-button:hover {
    background-color: #8a60bc;
}

.submit-button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

* {
        font-size: 2rem;
    }

button {
    box-shadow: none;
}

@media (max-width: 768px) {
    .modal-content {
        width: 95%;
        padding: 20px;
    }

    .modal-body textarea {
        font-size: 14px;
    }

    .modal-footer button {
        padding: 8px 16px;
        font-size: 14px;
    }
}
</style>