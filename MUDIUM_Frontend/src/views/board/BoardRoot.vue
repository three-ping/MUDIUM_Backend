<template>
    <RouterView :userId="userId" :nickname="nickname"
                :access_token="access_token"></RouterView>
</template>

<script setup>
import { useUserStore } from '@/stores/userStore';
const userStore = useUserStore();
const user = userStore.userInfo;
const userId = Number(user.user_id);
const nickname = user.nickname;
const access_token = user.access_token;
</script>

<style scoped>

</style>