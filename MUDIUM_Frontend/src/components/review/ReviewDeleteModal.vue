<template>
    <div v-if="isOpen" class="modal-overlay" @click="closeModal">
        <div class="modal-content" @click.stop>
            <div class="modal-header">
                <img src="@/assets/images/MudiumText.svg" alt="MudiumLogo" class="logo">
                <button @click="closeModal" class="close-button">&times;</button>
            </div>
            <div class="modal-body">
                <slot></slot> <!-- 외부에서 주입된 내용 -->
            </div>
            <div class="modal-footer">
                <button @click="closeModal" class="cancel-button">취소</button>
                <button @click="yesModal" class="delete-button">삭제</button>
            </div>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    isOpen: Boolean,
});

const emit = defineEmits(['close', 'submit']);

const closeModal = () => {
    emit('close');
};

const yesModal = () => {
    emit('submit');
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
    background-color: #ffffff;
    padding: 30px;
    border-radius: 12px;
    width: 90%;
    max-width: 500px;
    position: relative;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-header {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 0;
    position: relative;
}

.logo {
    max-width: 180px;
    height: auto;
    margin: 0 auto;
}

.close-button {
    position: absolute;
    top: 0;
    right: 0;
    background: none;
    border: none;
    font-size: 28px;
    cursor: pointer;
    color: #888;
    padding: 10px;
    transition: color 0.3s ease;
    box-shadow: none;
}

.close-button:hover {
    color: #333;
}

.modal-body {
    margin-bottom: 30px;
    text-align: center;
}

.modal-footer {
    display: flex;
    justify-content: center;
    gap: 15px;
    box-shadow: none;
}

.cancel-button {
    background-color: #f0f0f0;
    color: #333;
    padding: 10px 20px;
    border: none;
    border-radius: 6px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    box-shadow: none;
}

.cancel-button:hover {
    background-color: #e0e0e0;
}

.delete-button {
    background-color: #9a70cc;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 6px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    box-shadow: none;
}

.delete-button:hover {
    background-color: #8a60bc;
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

    .cancel-button, .delete-button {
        padding: 8px 16px;
        font-size: 14px;
    }

    .close-button {
        font-size: 24px;
    }
}

@media (max-width: 480px) {
    .modal-content {
        width: 100%;
        padding: 15px;
    }

    .logo {
        max-width: 140px;
    }
}
</style>
