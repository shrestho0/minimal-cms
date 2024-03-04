<script lang="ts">
	import { page } from '$app/stores';
	import PreDebug from '@/dev/PreDebug.svelte';
	import { onMount } from 'svelte';
	import NavbarHomePage from '@/ui/NavbarHomePage.svelte';
	import HeroWrapper from '@/ui/HeroWrapper.svelte';
	import { ModeWatcher } from 'mode-watcher';

	let loading = true;
	onMount(() => {
		loading = false;
		console.log('page', $page);
	});
</script>

<div class=" mx-auto max-w-screen-xl dark:bg-stone-950">
	{#if !loading}
		<HeroWrapper />
		<NavbarHomePage showMenuItems={false} />
		<section class="flex h-full items-center py-32 md:py-48 xl:py-56">
			<div class="container mx-auto my-8 flex flex-col items-center justify-center px-5">
				<div class="max-w-md text-center">
					<h2 class="mb-8 text-9xl font-bold">
						<span class="sr-only">Error</span> <span class="text-primary">{$page.status}</span>
					</h2>
					<p class="text-3xl font-semibold md:text-3xl">Sorry, {$page.error?.message}</p>
					<p class=" mb-8 mt-4 text-lg text-muted-foreground dark:text-slate-400">
						But dont worry, you can find plenty of other things on our homepage.
					</p>
					<a rel="noopener noreferrer" href="/" class="btn ml-4">Back to homepage</a>
				</div>
			</div>
		</section>
	{:else}
		Loading...
	{/if}
</div>
<ModeWatcher defaultMode="dark" />
