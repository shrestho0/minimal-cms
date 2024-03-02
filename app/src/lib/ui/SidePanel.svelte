<script lang="ts">
	export let pages: any;
	export let customizationPages: any = null;
	import { page } from '$app/stores';
	import { Separator } from '@/components/ui/separator';
</script>

<div class="user-sidebar">
	{#each pages as p}
		<a
			on:click
			class="m-3 flex gap-3 rounded-md p-3 {$page.url.pathname?.includes(p.href)
				? 'bg-stone-950 text-white hover:bg-stone-800 dark:text-white  '
				: 'text-black hover:bg-stone-800 hover:text-white dark:text-black dark:hover:text-white'} "
			href={'/' + p.href}
		>
			<svelte:component this={p.icon} />
			{p.title}
		</a>
	{/each}
	{#if customizationPages}
		<Separator />
		<div class=" ml-3 flex items-center gap-3">
			<div class="text-muted-foreground ml-3 flex gap-3 pt-3">
				<span>Customizations</span>
			</div>
		</div>
		<div class="">
			{#each customizationPages as p}
				<a
					on:click
					class="m-3 flex gap-3 rounded-md p-3 {$page.url.pathname?.includes(p.href)
						? 'bg-stone-950 text-white hover:bg-stone-800 dark:text-white  '
						: 'text-black hover:bg-stone-800 hover:text-white dark:text-black dark:hover:text-white'} "
					href={'/' + p.href}
				>
					<svelte:component this={p.icon} />
					{p.title}
				</a>
			{/each}
		</div>
	{/if}
</div>
