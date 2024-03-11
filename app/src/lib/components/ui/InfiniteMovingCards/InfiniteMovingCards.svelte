<script lang="ts">
	import { cn } from '$lib/utils';
	import { onMount } from 'svelte';
	export let items: { name: string; quote: string; title: string }[] = [];
	export let direction = 'left';
	export let speed = 'fast';
	export let pauseOnHover = true;
	export let className = void 0;
	let containerRef: HTMLDivElement;
	let scrollerRef: HTMLUListElement;
	onMount(() => {
		addAnimation();
	});
	let start = false;
	function addAnimation() {
		if (containerRef && scrollerRef) {
			const scrollerContent = Array.from(scrollerRef.children);
			scrollerContent.forEach((item) => {
				const duplicatedItem = item.cloneNode(true);
				if (scrollerRef) {
					scrollerRef.appendChild(duplicatedItem);
				}
			});
			getDirection();
			getSpeed();
			start = true;
		}
	}
	const getDirection = () => {
		if (containerRef) {
			if (direction === 'left') {
				containerRef.style.setProperty('--animation-direction', 'forwards');
			} else {
				containerRef.style.setProperty('--animation-direction', 'reverse');
			}
		}
	};
	const getSpeed = () => {
		if (containerRef) {
			if (speed === 'fast') {
				containerRef.style.setProperty('--animation-duration', '20s');
			} else if (speed === 'normal') {
				containerRef.style.setProperty('--animation-duration', '40s');
			} else {
				containerRef.style.setProperty('--animation-duration', '80s');
			}
		}
	};
</script>

<div
	bind:this={containerRef}
	class={cn(
		'scroller relative z-20  max-w-7xl overflow-hidden  [mask-image:linear-gradient(to_right,transparent,white_20%,white_80%,transparent)]',
		className
	)}
>
	<ul
		bind:this={scrollerRef}
		class={cn(
			' flex w-max min-w-full shrink-0 flex-nowrap gap-4 py-4',
			start && 'animate-scroll ',
			pauseOnHover && 'hover:[animation-play-state:paused]'
		)}
	>
		{#each items as item, idx (item.name)}
			<li
				class="relative w-[350px] max-w-full flex-shrink-0 rounded-2xl border border-b-0 border-slate-700 px-8 py-6 md:w-[450px]"
				style="background: linear-gradient(180deg, var(--slate-800), var(--slate-900));"
			>
				<blockquote>
					<div
						aria-hidden="true"
						class="user-select-none -z-1 pointer-events-none absolute -left-0.5 -top-0.5 h-[calc(100%_+_4px)] w-[calc(100%_+_4px)]"
					></div>
					<span class=" relative z-20 text-sm font-normal leading-[1.6] text-gray-100">
						{item.quote}
					</span>
					<div class="relative z-20 mt-6 flex flex-row items-center">
						<span class="flex flex-col gap-1">
							<span class=" text-sm font-normal leading-[1.6] text-gray-400">
								{item.name}
							</span>
							<span class=" text-sm font-normal leading-[1.6] text-gray-400">
								{item.title}
							</span>
						</span>
					</div>
				</blockquote>
			</li>
		{/each}
	</ul>
</div>
