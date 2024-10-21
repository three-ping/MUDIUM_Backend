<template>
	<h1>프로필</h1>
	<div class="profile-section card">


		<img src="@/assets/images/profile_default.svg" alt="Profile" class="profile-image">
		<div class="profile-info">
			<h2>{{ userInfo.nickname || '뜨리핑님' }}</h2>
			<p>{{ userInfo.email || 'threeping@gmail.com' }}</p>
			<p>{{ userInfo.membershipStatus || '일반회원' }}</p>
		</div>
		<button class="edit-profile-btn">수정</button>
	</div>

	
</template>

<script setup>
defineProps({
	userInfo: {
		type: Object,
		required: true
	}
});
</script>
<style scoped>
.profile-section {
	display: flex;
	align-items: center;
	background-color: white;
	padding: 20px;
	border-radius: 10px;
	margin-bottom: 20px;
}

.profile-image {
	width: 100px;
	height: 100px;
	border-radius: 50%;
	object-fit: cover;
	margin-right: 20px;
}

.profile-info {
	flex-grow: 1;
}

.profile-info h2 {
	margin: 0 0 10px 0;
}

.profile-info p {
	margin: 5px 0;
	color: #666;
}

.warning {
	color: var(--warning-color)
}

.edit-profile-btn {
	background-color: var(--primary-color);
	color: white;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	cursor: pointer;
}

.calendar-section {
	background-color: white;
	padding: 20px;
	border-radius: 10px;
}
</style>