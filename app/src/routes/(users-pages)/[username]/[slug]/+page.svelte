<script lang="ts">
	import { onMount } from 'svelte';

	import Markdown from '@magidoc/plugin-svelte-marked';
	import UserPageHeader from '@/ui/UserPageHeader.svelte';
	import UserPageFooter from '@/ui/UserPageFooter.svelte';
	import type { SiteFooterType, SiteHeaderType } from '@/types/customizations';
	import type { SinglePage, PageStatus } from '@/types/entity';
	import UserPageTitle from '@/ui/UserPageTitle.svelte';
	import Alert from '@/components/ui/alert/alert.svelte';

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
		}, 500);
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
	<title>
		{data?.pageContent?.title} | {data?.siteHeader?.site_title
			? data?.siteHeader?.site_title
			: 'mCMS'}
	</title>
</svelte:head>
{#if !loading}
	{#if data?.pageContent?.status != 'published'}
		<div class="flex w-full items-center justify-center py-4">
			{#if data?.pageContent?.status == 'draft'}
				<Alert variant="default">
					<p>This page is a draft and is not yet published.</p>
				</Alert>
			{:else if data?.pageContent?.status == 'banned'}
				<Alert variant="destructive">
					<p>This page is banned.</p>
				</Alert>
			{/if}
		</div>
	{/if}

	<!-- <UserPageTitle title={data?.pageContent?.title} /> -->
	<Markdown source={data?.pageContent.content} />
{:else}
	<div class=" flex h-full items-center justify-center bg-white dark:bg-white">
		<div class="h-16 w-16 animate-spin rounded-full border-b-2 border-t-2 border-gray-900"></div>
	</div>
{/if}
