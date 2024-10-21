<template>
	<div class="page-container">
		<Sidebar v-model:selectedItem="selectedItem" />

		<main class="main-content" id="profile-container">
			<ProfileTab v-if="selectedItem === 'profile'" :userInfo="userInfo" />
			<Calendar v-if="selectedItem === 'profile'" />

			<MyReviews v-if="selectedItem === 'myWorks'" :userInfo="userInfo" />
			<BookmarksTab v-if="selectedItem === 'bookmarks'" :userInfo="userInfo" />
			<BoardTab v-if="selectedItem === 'board'" :userInfo="userInfo" />
			<TicketsTab v-if="selectedItem === 'tickets'" :userInfo="userInfo" />
		</main>
	</div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useUserStore } from '@/scripts/user/user';
import Sidebar from './components/SideBar.vue';
import ProfileTab from "./components/tabs/Profile.vue";
import MyReviews from './components/tabs/MyReviews.vue';
import BookmarksTab from './components/tabs/Bookmarks.vue';
import BoardTab from './components/tabs/BoardTab.vue';
import TicketsTab from './components/tabs/Tickets.vue';
import Calendar from '@/components/calendar/Calendar.vue';

const userStore = useUserStore();
const userInfo = ref(userStore.userInfo);

// Add state for selected sidebar item
const selectedItem = ref('profile');

watch(() => userStore.userInfo, (newUserInfo) => {
	userInfo.value = newUserInfo;
	console.log('User info updated:', newUserInfo);
});
</script>

<style scoped>
.page-container {
	display: flex;
}

.main-content {
	flex-grow: 1;
	padding: 20px;
}
</style>
