<template>
    <div class="modal-overlay" v-if="isVisible">
        <div class="modal-content">
            <button class="close-button" @click="closeModal"></button>
            <div class="modal-message">
                <slot>정말 삭제하시겠습니까?</slot>
            </div>
            <div class="buttons">
                <button class="common-button" @click="closeModal">취소</button>
                <button class="common-button-purple" @click="confirmModal">확인</button>
            </div>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    isVisible: {
        type: Boolean,
        default: false
    }
});

const emit = defineEmits(['update:isVisible', 'confirm']);

const closeModal = () => {
    emit('update:isVisible', false);
};

const confirmModal = () => {
    emit('confirm');
    emit('update:isVisible', false);
};
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    position: relative;
    background: white;
    padding: 3rem;
    border-radius: 1rem;
    width: 300px;
    /* box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); */
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 20px;
    cursor: pointer;
    width: 30px;
    height: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0;
}

.close-button::before,
.close-button::after {
    content: '';
    position: absolute;
    width: 20px;
    height: 2px;
    background-color: #333;
}

.close-button::before {
    transform: rotate(45deg);
}

.close-button::after {
    transform: rotate(-45deg);
}

.modal-message {
    margin: 20px 0;
    text-align: center;
    font-size: 16px;
}

.buttons {
    display: flex;
    justify-content: center;
    gap: 10px;
}

.common-button,
.common-button-purple {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
    width: 100px;
}

.common-button {
    background-color: #f0f0f0;
}

.common-button-purple {
    background-color: #8e44ad;
    color: white;
}
button {
    box-shadow: none;
}
</style>