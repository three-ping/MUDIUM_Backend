<template>
  <div class="root-container">
    <Navigation @open-login-modal="openLoginModal" @open-signup-modal="openSignupModal" @logout="handleLogout" />
    <LoginModal :isLoginModalVisible="isLoginModalVisible" @close="closeLoginModal"
      @update:isLoggedIn="handleLoginSuccess" @update:userInfo="updateUserInfo" />
    <SignupModal :isSignupModalVisible="isSignupModalVisible" @close="closeSignupModal"
      @switch-to-login="switchToLogin" />
    <router-view />
    <Modal />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from './stores/userStore';
import Navigation from '@/components/layout/Navigation.vue';
import LoginModal from '@/views/user/components/LoginModal.vue';
import SignupModal from '@/views/user/components/SignupModal.vue';
import Modal from './components/layout/Modal.vue';

const isLoginModalVisible = ref(false);
const isSignupModalVisible = ref(false);
const userStore = useUserStore();

const openLoginModal = () => {
  isLoginModalVisible.value = true;
};

const closeLoginModal = () => {
  isLoginModalVisible.value = false;
};

const openSignupModal = () => {
  isSignupModalVisible.value = true;
};

const closeSignupModal = () => {
  isSignupModalVisible.value = false;
};

const switchToLogin = () => {
  closeSignupModal();
  openLoginModal();
};

const handleLoginSuccess = (isLoggedIn) => {
  if (isLoggedIn) {
    userStore.updateLoginStatus(true);
    closeLoginModal();
  }
};
const handleLogout = () => {
  console.log("handle Logout");
  userStore.updateLoginStatus(false);
  userStore.clearUserInfo();
}

const updateUserInfo = (userInfo) => {
  console.log("updateUserInfo")
  userStore.updateUserInfo(userInfo);
  // Save user info to localStorage
  localStorage.setItem('userInfo', JSON.stringify(userInfo));
};

// Load user info from localStorage on component mount
onMounted(() => {
  const storedUserInfo = localStorage.getItem('userInfo');
  if (storedUserInfo) {
    const userInfo = JSON.parse(storedUserInfo);
    userStore.updateUserInfo(userInfo);
    userStore.updateLoginStatus(true);
  }
});
</script>

<style>
/* Add any global styles here */
</style>