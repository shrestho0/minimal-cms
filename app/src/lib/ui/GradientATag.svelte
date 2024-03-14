<script lang="ts">
	import type { SvelteComponent } from 'svelte';

	export let title = 'Tailwind Connect';
	export let href = '';
</script>

<a
	{href}
	class="group relative inline-block cursor-pointer rounded-full bg-slate-800 p-px text-xs font-semibold leading-6 text-white no-underline shadow-2xl shadow-zinc-900"
>
	<span class="absolute inset-0 overflow-hidden rounded-full">
		<span
			class="absolute inset-0 rounded-full bg-gradient-to-r from-emerald-400/0 via-white/90 to-emerald-400/0 opacity-0 transition-opacity duration-500 group-hover:opacity-100"
		></span>
	</span>
	<div
		class="relative z-10 flex items-center space-x-2 rounded-full bg-zinc-950 px-4 py-0.5 ring-1 ring-white/10"
	>
		<slot name="left" />
		<span>{title}</span>

		<!-- <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
			<path
				stroke="currentColor"
				stroke-linecap="round"
				stroke-linejoin="round"
				stroke-width="1.5"
				d="M10.75 8.75L14.25 12L10.75 15.25"
			></path>
		</svg> -->
		<slot name="right" />
	</div>
	<span
		class="absolute -bottom-0 left-[1.125rem] h-px w-[calc(100%-2.25rem)] bg-gradient-to-r from-emerald-400/0 via-white/90 to-emerald-400/0 transition-opacity duration-500 group-hover:opacity-40"
	></span>
</a>
