<template>
	<aside class="sidebar">
		<nav>
			<ul>
				<li v-for="item in menuItems" :key="item.value">
					<a href="#" :class="{ 'selected': selectedItem === item.value }"
						@click="setSelectedItem(item.value)">
						{{ item.label }}
					</a>
				</li>
			</ul>
		</nav>
	</aside>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
	selectedItem: {
		type: String,
		required: true
	}
});

const emit = defineEmits(['update:selectedItem']);

const menuItems = [
	{ value: 'profile', label: '프로필' },
	{ value: 'myWorks', label: '내가 본 작품' },
	{ value: 'bookmarks', label: '북마크' },
	{ value: 'board', label: '게시판' },
	{ value: 'tickets', label: '티켓' },

];

const setSelectedItem = (item) => {
	emit('update:selectedItem', item);
};
</script>

<style scoped>
.sidebar {
	width: 30rem;
	color: var(--secondary-color);
	padding: 3rem;
}

.sidebar ul {
	list-style-type: none;
	padding: 0;
}

.sidebar li {
	margin-bottom: 5rem;
	cursor: pointer;
	text-align: right;
}

.sidebar a {
	text-decoration: none;
	color: var(--text-color);
}

.sidebar a.selected {
	font-weight: bold;
	color: black;
}

.sidebar a:hover {
	opacity: 0.8;
}
</style>