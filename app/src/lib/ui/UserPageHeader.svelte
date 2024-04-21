<script lang="ts">
	import { page } from '$app/stores';
	import type { SingleNavItem, SiteHeaderType } from '@/types/customizations';
	import { onMount } from 'svelte';

	export let headerData: SiteHeaderType;

	// let loading = true;
	// onMount(() => {
	// 	console.log('mounted: header', headerData.nav_json);
	// 	headerData.nav_json = JSON.parse((headerData.nav_json as unknown as string) || '[]');
	// 	loading = false;
	// });
</script>

<!-- {#if !loading} -->
<header
	class="flex flex-col items-center justify-center gap-3 bg-black p-4 text-gray-100 dark:bg-black dark:text-gray-100"
>
	<a href="/{$page.url.pathname.split('/')[1]}">
		{#if headerData.logo}
			<img src={headerData.logo} alt={headerData.site_title} />
		{:else}
			<div class="break-all text-2xl font-bold text-white hover:text-white/80">
				{headerData.site_title}
			</div>
		{/if}
	</a>
	{#if headerData?.nav_json?.length > 0}
		<nav class="nav">
			<ul class="flex items-center gap-3">
				{#each headerData.nav_json as item}
					<li>
						<a class="text-[20px] text-white hover:text-white/80" href={item.href}>{item.title}</a>
					</li>
				{/each}
			</ul>
		</nav>
	{/if}
</header>
<!-- {/if} -->
