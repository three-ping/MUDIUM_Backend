<template>
    <nav class="appHeader">
        <div class="header-container">
            <RouterLink to="/" class="logo">
                <img src="@/assets/images/logo2.svg" alt="Mudium">
            </RouterLink>
            <div class="banner">
                <RouterLink to="/musicalInfo" class="banner-link"
                    :class="{ active: currentRoute.startsWith('/musicalInfo') || currentRoute === '/' }">
                    뮤지컬
                </RouterLink>
                <RouterLink to="/musicalBoard" class="banner-link"
                    :class="{ active: currentRoute.startsWith('/musicalBoard') }">
                    작품 게시판
                </RouterLink>
                <RouterLink to="/board" class="banner-link" :class="{ active: currentRoute.startsWith('/board') }">
                    자유 게시판
                </RouterLink>
                <RouterLink to="/guidebook/guidemain" class="banner-link"
                    :class="{ active: currentRoute.startsWith('/guidebook/guidemain') }">
                    가이드북
                </RouterLink>
                <RouterLink to="/notice" class="banner-link" :class="{ active: currentRoute.startsWith('/notice') }">
                    공지사항
                </RouterLink>
                <div class="search-bar">
                    <input 
                        type="text" 
                        v-model="searchQuery" 
                        placeholder="검색어를 입력하세요" 
                        @keyup.enter="performSearch"
                    />
                </div>

                <div v-if="isLoggedIn" class="user-info">
                    <RouterLink to="/calender">
                        <img src="/src/assets/calendar-icon.svg" alt="캘린더" class="calendar-icon">
                    </RouterLink>
                    <RouterLink to="/myPage">
                        <img src="/src/assets/mypage-icon.svg" alt="프로필" class="mypage-icon">
                    </RouterLink>
                </div>
                <div v-else class="login-banner">
                    <RouterLink to="/login" class="banner-link">로그인</RouterLink>
                    <RouterLink to="/signup" class="banner-link">회원가입</RouterLink>
                </div>
            </div>
        </div>
    </nav>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

// 현재 경로를 추적하기 위한 변수
const route = useRoute();
const router = useRouter();
const currentRoute = ref(route.path);

const isLoggedIn = ref(false);
const hasSearched = ref(false);
const searchQuery = ref('');

const resetSearch = () => {
  hasSearched.value = false;  // 검색 상태 초기화
  searchQuery.value = '';     // 검색어 초기화
};

const performSearch = () => {
  if (!searchQuery.value.trim()) return; // 검색어가 없으면 실행하지 않음
  router.push({
    path: '/musicalInfo',  // Search results will be shown here
    query: { title: searchQuery.value }
  });
};
// 경로가 변경될 때마다 currentRoute 업데이트
watch(route, (newRoute) => {
    currentRoute.value = newRoute.path;
});
</script>

<style scoped>
.appHeader {
    display: flex;
    width: 100vw;
    margin-top: 1%;
    justify-content: space-between;
    align-items: center;
    position: relative;
    padding-bottom: 10px;
}

.appHeader::after {
    content: "";
    position: absolute;
    bottom: 0;
    left: 3%;
    right: 3%;
    height: 1px;
    background-color: #ccc;
}

.header-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-grow: 1;
    width: 100%;
}

.appHeader .logo {
    flex: 1;
    align-items: center;
}

.appHeader .logo img {
    display: flex;
    width: 27%;
    margin-left: 30px;
}

.appHeader .banner {
    display: flex;
    align-items: center;
    flex-grow: 1;
    flex: 2;
    margin-left: -20%;
    gap: 8%;
}

.appHeader .banner .banner-link {
    font-size: 1.0vw;
    font-weight: 500;
    color: black;
    text-decoration: none;
    position: relative;
    margin-left: -3%;
}

.appHeader .search-bar {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-grow: 1;
}

.appHeader .search-bar input {
    padding: 1%;
    width: 70%;
    font-size: 1vw;
    /* width: 100%; */
    border: 1x solid #ccc;
    border-radius: 10px;
    margin-left: -15%;
    margin-right: 30%;
}

.appHeader .login-banner {
    display: flex;
    gap: 12%;
    align-items: center;
    flex-grow: 1;
    margin-right: -9%;
}

.appHeader .banner .banner-link.active::after {
    content: "";
    position: absolute;
    display: flex;
    bottom: -5px;
    left: 0;
    width: 100%;
    height: 3px;
    background-color: purple;
}

.calendar-icon {
    width: 50px;
    height: 50px;
    margin-left: -60%;
}

.calendar-icon {
    width: 50px;
    height: 50px;
    margin-left: -100%;
}

.mypage-icon {
    width: 50px;
    height: 50px;
    margin-left: 30%;
}
</style>