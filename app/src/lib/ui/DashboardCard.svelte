<script lang="ts">
	import { LucideAArrowDown, LucideAArrowUp } from 'lucide-svelte';
	import type { SvelteComponent } from 'svelte';

	export let cardData: {
		title: string;
		value: string;
		icon: ConstructorOfATypedSvelteComponent;
		type: 'default' | 'warning';
	}[];

	const typeCSS = {
		default: 'bg-card text-card-foreground',
		warning: 'bg-red-500/80 text-white'
	};
</script>

<div class="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
	{#each cardData as card}
		<div class="rounded-xl border {typeCSS[card.type]}  shadow">
			<div class="flex flex-row items-center justify-between space-y-0 p-6 pb-2">
				<h3 class="text-sm font-medium tracking-tight">{card.title}</h3>

				<!-- <LucideAArrowDown class="h-4 w-4 text-muted-foreground" /> -->
				<svelte:component this={card.icon} class="h-4 w-4 text-muted-foreground" />
			</div>
			<div class="p-6 pt-0">
				<div class="text-2xl font-bold">{card.value}</div>
				<!-- <p class="text-xs text-muted-foreground">+20.1% from last month</p> -->
			</div>
		</div>
	{/each}
</div>
