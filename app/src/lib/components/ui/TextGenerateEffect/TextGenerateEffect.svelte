<script lang="ts">
	import { Motion } from 'svelte-motion';
	import { cn } from '$lib/utils';
	export let words = '';
	export let className = '';
	export let beforeMotionClassName = 'text-black dark:text-white';
	export let motionClassName = 'text-black dark:text-white';
	const variants = {
		visible: (i: number) => ({
			opacity: 1,
			transition: {
				delay: i * 0.2,
				duration: 2
			}
		}),
		hidden: { opacity: 0 }
	};
</script>

<div class={cn('', className)}>
	<div class="mt-4">
		<div class=" leading-snug tracking-wide {beforeMotionClassName}">
			<Motion let:motion custom={0} {variants} initial="hidden" animate={'visible'}>
				<div use:motion>
					{#each words.split(' ') as word, idx (`${word}${idx}`)}
						<Motion let:motion {variants} custom={idx + 1} initial="hidden" animate={'visible'}>
							<span use:motion class={cn('font-bold', motionClassName)}>
								{word}{' '}
							</span>
						</Motion>
					{/each}
				</div>
			</Motion>
		</div>
	</div>
</div>
