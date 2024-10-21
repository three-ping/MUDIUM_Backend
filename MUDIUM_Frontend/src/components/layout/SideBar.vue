<template>
	<aside class="sidebar">
		<div class="logo-container">
			<img src="@/assets/images/logo.svg" alt="Mudium Logo" class="logo">
		</div>
		<nav class="navigation">
			<ul>
				<li v-for="item in navItems" :key="item.name" :class="{ active: item.name === activeItem }">
					<a @click="setActiveItem(item.name)">{{ item.label }}</a>
				</li>
			</ul>
		</nav>
	</aside>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';  // Vue Router 사용

// Vue Router 인스턴스 가져오기
const router = useRouter();

const navItems = [
	{ name: 'profile', label: '프로필' },
	{ name: 'myWorks', label: '내가 본 작품' },
	{ name: 'bookmarks', label: '북마크' },
	{ name: 'board', label: '게시판' },
	{ name: 'tickets', label: '티켓' },
	{ name: 'logout', label: '로그아웃' }
];

const activeItem = ref('profile');

const setActiveItem = (itemName) => {
	activeItem.value = itemName;
  	// Here you can add logic to navigate to the corresponding page
	// For example: router.push({ name: itemName });
};
</script>

<style scoped>
.sidebar {
	width: 200px;
	height: 100vh;
	background: linear-gradient(to bottom, #9b59b6, #3498db);
	color: white;
	padding: 20px;
	display: flex;
	flex-direction: column;
}

.logo-container {
	text-align: center;
	margin-bottom: 30px;
}

.logo {
	width: 120px;
	height: auto;
}

.navigation ul {
	list-style-type: none;
	padding: 0;
	margin: 0;
}

.navigation li {
	margin-bottom: 15px;
}

.navigation a {
	color: white;
	text-decoration: none;
	font-size: 16px;
	cursor: pointer;
	transition: opacity 0.3s ease;
}

.navigation a:hover {
	opacity: 0.8;
}

.navigation li.active a {
	font-weight: bold;
}
</style>