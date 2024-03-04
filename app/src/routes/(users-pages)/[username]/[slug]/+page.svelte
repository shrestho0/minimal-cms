<script lang="ts">
	import { onMount } from 'svelte';

	import Markdown from '@magidoc/plugin-svelte-marked';
	import UserPageHeader from '@/ui/UserPageHeader.svelte';
	import UserPageFooter from '@/ui/UserPageFooter.svelte';
	import type { SiteFooterType, SiteHeaderType } from '@/types/customizations';
	import type { SinglePage } from '@/types/entity';
	import UserPageTitle from '@/ui/UserPageTitle.svelte';

	export let data: {
		siteFooter: SiteFooterType;
		siteHeader: SiteHeaderType;
		pageContent: SinglePage;
	};

	let loading = true;
	onMount(() => {
		console.log('mounted: profile page', data);
		setTimeout(() => {
			loading = false;
		}, 1000);
	});
</script>

<svelte:head>
	<link
		rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
		integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
		crossorigin="anonymous"
		referrerpolicy="no-referrer"
	/>
</svelte:head>
{#if !loading}
	<UserPageTitle title={data?.pageContent?.title} />
	<Markdown source={data?.pageContent.content} />
{:else}
	<div class=" flex h-full items-center justify-center bg-white dark:bg-white">
		<div class="h-16 w-16 animate-spin rounded-full border-b-2 border-t-2 border-gray-900"></div>
	</div>
{/if}
