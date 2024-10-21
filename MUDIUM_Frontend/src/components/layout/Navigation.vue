<template>
	<header class="container-fluid fixed-header">
		<nav>
			<div class="nav-left">
				<RouterLink to="/">
					<img src="@/assets/images/Mudium.svg" alt="Mudium" class="logo">
				</RouterLink>
			</div>

			<ul class="nav-links">
				<li>
					<RouterLink to="/musicalInfo"
						:class="{ active: currentRoute.startsWith('/musicalInfo') || currentRoute === '/' }">
						<strong>뮤지컬</strong>
					</RouterLink>
				</li>
				<li>
					<RouterLink to="/musicalBoard" :class="{ active: currentRoute.startsWith('/musicalBoard') }">
						<strong>작품 게시판</strong>
					</RouterLink>
				</li>
				<li>
					<RouterLink to="/board" :class="{ active: currentRoute.startsWith('/board') }">
						<strong>자유 게시판</strong>
					</RouterLink>
				</li>

				<li>

					<RouterLink to="/guidebook/guidemain"
						:class="{ active: currentRoute.startsWith('/guidebook/guidemain') || currentRoute.startsWith('/guidebook/terms') || currentRoute.startsWith('/guidebook/etiquette') }">
						<strong>가이드북</strong>
					</RouterLink>
				</li>

				<li>
					<RouterLink to="/notice" :class="{ active: currentRoute.startsWith('/notice') }">
						<strong>공지사항</strong>
					</RouterLink>
				</li>
			</ul>
			<div class="nav-right">
				<input type="search" v-model="searchQuery" placeholder="검색어를 입력하세요" @keyup.enter="performSearch">
				<div v-if="!userStore.userInfo.isLoggedIn" class="auth-links">
					<a href="#" @click.prevent="openLoginModal">로그인</a>
					<span class="auth-separator">|</span>
					<a href="#" @click.prevent="openSignupModal">회원가입</a>
				</div>
				<div v-else class="profile-container dropdown">
					<img src="@/assets/images/profile_default.svg" alt="Profile" class="profile-img"
						@click="toggleProfileMenu">
					<ul class="dropdown-menu profile-menu" v-if="isProfileMenuOpen">
						<li><a href="#" @click.prevent="navigateToMyPage">마이페이지</a></li>
						<li><a href="#" @click.prevent="logout">로그아웃</a></li>
					</ul>
				</div>
			</div>

		</nav>
	</header>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore';

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const currentRoute = ref(route.path);
const searchQuery = ref('');
const isProfileMenuOpen = ref(false);

const emit = defineEmits(['openLoginModal', 'openSignupModal', 'logout']);

const performSearch = () => {
	if (!searchQuery.value.trim()) return;
	router.push({
		path: '/musicalInfo',
		query: { title: searchQuery.value }
	});
};

watch(route, (newRoute) => {
	currentRoute.value = newRoute.path;
});

const openLoginModal = () => {
	emit('openLoginModal');
};

const openSignupModal = () => {
	emit('openSignupModal');
};

const navigateToMyPage = () => {
	console.log('navigate to mypage')
	router.push('/mypage');
	isProfileMenuOpen.value = false;
};

const logout = () => {
	userStore.clearUserInfo();
	router.push({ path: '/musicalInfo', query: {} });
	isProfileMenuOpen.value = false;
};

const toggleProfileMenu = () => {
	isProfileMenuOpen.value = !isProfileMenuOpen.value;
};
</script>
<style scoped>
.nav-links {
	display: flex;
	list-style-type: none;
}

.nav-links li {
	position: relative;
	margin-right: 20px;
}

.dropdown-menu {
	display: none;
	position: absolute;
	top: 100%;
	left: 0;
	background-color: white;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	border-radius: 4px;
	padding: 10px 0;
	z-index: 1000;
}

.dropdown:hover .dropdown-menu {
	display: block;
}

.dropdown-menu li {
	margin: 0;
	padding: 5px 15px;
	white-space: nowrap;
}

.dropdown-menu a {
	color: #333;
	text-decoration: none;
}

.dropdown-menu a:hover {
	color: #000;
	text-decoration: underline;
}

.profile-menu {
	right: 0;
	left: auto;
}

/* ... (rest of the existing styles) */
</style>