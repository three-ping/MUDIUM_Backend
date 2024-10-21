<template>
	<div>
		<details class="dropdown" ref="menu">
			<summary>Theme</summary>
			<ul>
				<li>
					<a href="#" data-theme-switcher="auto" @click.prevent="setScheme('auto')">Auto (System
						Preference)</a>
				</li>
				<li>
					<a href="#" data-theme-switcher="light" @click.prevent="setScheme('light')">Light</a>
				</li>
				<li>
					<a href="#" data-theme-switcher="dark" @click.prevent="setScheme('dark')">Dark</a>
				</li>
			</ul>
		</details>
	</div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, computed } from 'vue';

const _scheme = ref('auto');
const menu = ref(null);
const rootAttribute = 'data-theme';
const localStorageKey = 'picoPreferredColorScheme';

// Preferred color scheme based on system preference
const preferredColorScheme = computed(() => {
	return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
});

// Get color scheme from local storage
const schemeFromLocalStorage = window.localStorage.getItem(localStorageKey) || _scheme.value;

// Set scheme
const setScheme = (scheme) => {
	if (scheme === 'auto') {
		_scheme.value = preferredColorScheme.value;
	} else if (scheme === 'dark' || scheme === 'light') {
		_scheme.value = scheme;
	}
	applyScheme();
	schemeToLocalStorage();
	closeDropdown();
};

// Apply scheme
const applyScheme = () => {
	const scheme = _scheme.value === 'auto' ? preferredColorScheme.value : _scheme.value;
	document.querySelector('html')?.setAttribute(rootAttribute, scheme);
};

// Store scheme to local storage
const schemeToLocalStorage = () => {
	window.localStorage.setItem(localStorageKey, _scheme.value);
};

// Close dropdown
const closeDropdown = () => {
	if (menu.value) {
		menu.value.removeAttribute('open');
	}
};

// Handle system preference change
const handleSystemPreferenceChange = () => {
	if (_scheme.value === 'auto') {
		applyScheme(); // Apply new system preference if theme is set to auto
	}
};

// Lifecycle hooks
onMounted(() => {
	_scheme.value = schemeFromLocalStorage;

	applyScheme();

	// Listen for system preference changes
	// window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', handleSystemPreferenceChange);
});

onBeforeUnmount(() => {
	// Cleanup event listener when component is destroyed
	// window.matchMedia('(prefers-color-scheme: dark)').removeEventListener('change', handleSystemPreferenceChange);
});
</script>

<style scoped>
/* Add your custom styles here */
/* .dropdown {
	opacity: 0.7;
} */
</style>
