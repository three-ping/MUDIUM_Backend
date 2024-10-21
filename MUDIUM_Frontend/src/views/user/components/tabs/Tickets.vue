<template>
	<div>
	  <h1>내 티켓</h1>
	  <!-- 상위 컴포넌트에서 티켓 데이터를 하위 컴포넌트로 전달 -->
	  <<CustomTicketView :userInfo="userInfo"/>
	</div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import CustomTicketView from '@/views/customticket/CustomTicketView.vue';
  
  const props = defineProps({
  userInfo: {
    type: Object,
    required: true
  }
});

onMounted(() => {
  console.log('User Info in Tickets:', props.userInfo);
});

  </script>
  