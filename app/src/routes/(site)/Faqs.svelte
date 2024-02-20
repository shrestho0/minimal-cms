<script lang="ts">
	import { Minus, Plus } from 'lucide-svelte';
	import { slide } from 'svelte/transition';

	const faqs = [
		{
			title: 'What is this?',
			description: 'Something'
		},
		{
			title: "Why mCMS? Why don't just use something else?",
			description: 'This is minimal'
		},
		{
			title: 'How to use?',
			description: 'Just explore and use it'
		}
	];

	let openedIdx: number | null = null;
</script>

<div id="faqs" class="mx-auto max-w-5xl select-none px-6 py-24 sm:py-36 lg:px-8 lg:py-52 dark:bg-stone-950">
	<div class="mx-auto divide-y divide-gray-900/10 dark:divide-gray-100/10">
		<h2 class="text-2xl font-bold leading-10 tracking-tight text-gray-900 dark:text-white">FAQs</h2>
		<dl class="mt-10 space-y-6 divide-y divide-gray-900/10 dark:divide-gray-100/10">
			<!-- svelte-ignore a11y-click-events-have-key-events -->
			{#each faqs as faq, idx}
				<!-- svelte-ignore a11y-interactive-supports-focus -->
				<div
					class="pt-6"
					on:click={() => {
						console.log('clicked');
						openedIdx = openedIdx === idx ? null : idx;
					}}
					role="button"
				>
					<dt>
						<button
							type="button"
							class="flex w-full items-start justify-between text-left text-gray-900 dark:text-white"
						>
							<span class="text-base font-semibold leading-7">{faq.title}</span>
							<span class="ml-6 flex h-7 items-center">
								{#if openedIdx === idx}
									<Minus class="h-6 w-6" stroke-linecap="round" stroke-linejoin="round" />
								{:else}
									<Plus class="h-6 w-6" stroke-linecap="round" stroke-linejoin="round" />
								{/if}
							</span>
						</button>
					</dt>
					{#if openedIdx === idx}
						<dd in:slide out:slide class="mt-2 pr-12" id="faq-0">
							<span class="text-base leading-7 text-gray-600 dark:text-gray-100">
								{faq.description}
							</span>
						</dd>
					{/if}
				</div>
			{/each}
		</dl>
	</div>
</div>
