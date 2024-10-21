<template>
	<div class="bookmarks-container">
		<h1>내 북마크된 뮤지컬</h1>
		<div v-if="loading" class="loading">Loading bookmarks...</div>
		<div v-else-if="error" class="error">{{ error }}</div>
		<div v-else-if="bookmarks.length === 0" class="no-bookmarks">
			You haven't bookmarked any musicals yet.
		</div>
		<div v-else class="bookmarks-grid">
			<div v-for="bookmark in bookmarks" :key="bookmark.musicalId" class="bookmark-card">
				<div class="card-inner">
					<div class="card-front">
						<img :src="bookmark.poster" :alt="bookmark.title" class="card-image">
					</div>
					<div class="card-back">
						<h2>{{ bookmark.title }}</h2>
						<p><strong>Rating:</strong> {{ bookmark.rating }}</p>
						<p><strong>Production:</strong> {{ bookmark.production }}</p>
						<p><strong>View Count:</strong> {{ bookmark.viewCount }}</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useUserStore } from '@/scripts/user/user';
import axios from 'axios';

const props = defineProps({
	userInfo: {
		type: Object,
		required: true
	}
});

const userStore = useUserStore();
const bookmarks = ref([]);
const loading = ref(true);
const error = ref(null);

const fetchBookmarks = async () => {
	if (!props.userInfo || !props.userInfo.userId) {
		error.value = 'User information not available.';
		loading.value = false;
		return;
	}

	try {
		loading.value = true;
		const response = await axios.get(`/api/bookmark/users/${props.userInfo.userId}`);
		bookmarks.value = response.data.data;
	} catch (err) {
		error.value = 'Failed to fetch bookmarks. Please try again later.';
		console.error('Error fetching bookmarks:', err);
	} finally {
		loading.value = false;
	}
};

onMounted(fetchBookmarks);

watch(() => props.userInfo, (newUserInfo) => {
	if (newUserInfo && newUserInfo.id) {
		fetchBookmarks();
	}
}, { deep: true });
</script>

<style scoped>
.bookmarks-container {
	max-width: 1200px;
	margin: 0 auto;
	padding: 20px;
}

.loading,
.error,
.no-bookmarks {
	text-align: center;
	margin-top: 20px;
}

.bookmarks-grid {
	display: grid;
	grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
	gap: 20px;
}

.bookmark-card {
	perspective: 1000px;
	height: 60rem;
	width: 40rem;
}

.card-inner {
	position: relative;
	width: 100%;
	height: 100%;
	text-align: center;
	transition: transform 0.6s;
	transform-style: preserve-3d;
}

.bookmark-card:hover .card-inner {
	transform: rotateY(180deg);
}

.card-front,
.card-back {
	position: absolute;
	width: 100%;
	height: 100%;
	backface-visibility: hidden;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-front {
	background-color: #f1f1f1;
}

.card-back {
	background-color: #e1e1e1;
	transform: rotateY(180deg);
	display: flex;
	flex-direction: column;
	justify-content: center;
	padding: 10px;
}

.musical-poster {
	width: 100%;
	height: 100%;
	object-fit: cover;
	border-radius: 10px;
}

h2 {
	font-size: 1.2em;
	margin-bottom: 10px;
}

p {
	font-size: 0.9em;
	margin: 5px 0;
}

.card-image {
	width: 100%;
	height: 100%;
	object-fit: cover;
	object-position: center;
}
</style>