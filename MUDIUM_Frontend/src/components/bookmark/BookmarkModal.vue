<template>
    <div class="modal-overlay" v-if="isOpen">
      <div class="modal-content">
        <p>{{ message }}</p> <!-- Use dynamic message -->
        <button @click="closeModal">닫기</button>
        <router-link to="/bookmarklist" @click.native="closeModal"> <!-- Close modal on navigation -->
          <button>북마크 목록 가기</button>
        </router-link>
      </div>
    </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  isOpen: Boolean,
  message: {
    type: String,
    default: '북마크를 추가하였습니다!'
  }
});

const emits = defineEmits(['close']);

const closeModal = () => {
  emits('close');
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
    z-index: 9999;
  }
  .modal-content {
    background: white;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
  }
  .modal-content button {
    margin: 10px;
    padding: 10px 20px;
  }
  </style>
  