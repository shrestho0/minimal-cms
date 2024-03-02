<script lang="ts">
	import { page } from '$app/stores';
	import { AppLinks } from '@/utils/app-links';

	export let mode: 'dark' | 'light' = 'light';

	const modeCSS = {
		dark: 'text-white',
		light: 'text-gray-900'
	};

	export let className = 'text-gray-900 dark:text-white';
</script>

<a
	href="/"
	class="{className} relative z-20 flex items-center text-lg font-extrabold tracking-tight"
>
	mCMS
</a>
