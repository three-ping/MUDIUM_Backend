<template>
	<footer>
		<ToggleTheme />
	</footer>
</template>

<script setup>
import ToggleTheme from './ToggleTheme.vue';
</script>

<style scoped>
footer {
	position: fixed;
	display: flex;
	align-items: center;
	bottom: 0px;
	width: 100%;
    height: 70px;
}
</style>