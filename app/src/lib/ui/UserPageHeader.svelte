<script lang="ts">
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
	{#if headerData.logo}
		<img src={headerData.logo} alt={headerData.site_title} />
	{:else}
		<div class="text-2xl font-bold">{headerData.site_title}</div>
	{/if}
	{#if headerData?.nav_json?.length > 0}
		<nav class="nav">
			<ul class="flex items-center gap-3">
				{#each headerData.nav_json as item}
					<li>
						<a class="text-white hover:text-white/80" href={item.href}>{item.title}</a>
					</li>
				{/each}
			</ul>
		</nav>
	{/if}
</header>
<!-- {/if} -->
