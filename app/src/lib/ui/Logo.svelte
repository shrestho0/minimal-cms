<script lang="ts">
	import { AppLinks } from '@/utils/app-links';

	export let mode: 'dark' | 'light' = 'light';

	const modeCSS = {
		dark: 'text-white',
		light: 'text-gray-900'
	};

	export let className = '';
</script>

<a
	href={AppLinks.HOME}
	class="{className} relative z-20 flex items-center text-lg font-extrabold tracking-tight text-gray-900 dark:text-white"
>
	mCMS
</a>
