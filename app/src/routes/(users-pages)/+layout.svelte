<script lang="ts">
	import { onDestroy, onMount } from 'svelte';
	import { page } from '$app/stores';

	let loading = true;
	import '$lib/ui/app.pcss';
	import PreDebug from '@/dev/PreDebug.svelte';
	import UserPageHeader from '@/ui/UserPageHeader.svelte';
	import UserPageFooter from '@/ui/UserPageFooter.svelte';
	import { ModeWatcher, mode, setMode } from 'mode-watcher';

	let tempMode: 'light' | 'dark' = 'dark';
	onMount(() => {
		mode.subscribe((val) => {
			tempMode = val as 'light' | 'dark';
		});

		console.log('mounted');
		setMode('light');
		loading = false;
	});
	onDestroy(() => {
		setMode(tempMode);
	});
	export let data: {
		siteHeader: any;
		siteFooter: any;
	};

	// force light mode on users pages
</script>

<svelte:head>
	<link
		rel="stylesheet"
		href="/api/site-style?u={$page.url.pathname?.split('/')[1]}&v={Math.random()}"
	/>
</svelte:head>
<div class=" bg-white dark:bg-white">
	<div class="flex h-screen flex-col">
		<UserPageHeader headerData={data?.siteHeader} />
		<main class="flex-1 p-8 md:p-16">
			<slot />
		</main>
		<UserPageFooter footerData={data?.siteFooter} />
	</div>
</div>
