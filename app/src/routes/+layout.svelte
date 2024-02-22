<script lang="ts">
	import { navigating } from '$app/stores';
	import '$lib/ui/app.pcss';
	import { Button } from '@/components/ui/button';
	import { Toaster, toast } from 'svelte-sonner';
	import { cubicOut } from 'svelte/easing';
	import { tweened } from 'svelte/motion';
	import { fade } from 'svelte/transition';

	// progress bar value
	const p = tweened(0, {
		duration: 200,
		easing: cubicOut
	});

	let isVisible = false;

	function increase() {
		if ($p >= 0 && $p < 0.2) {
			p.update((_) => _ + 0.04);
		} else if ($p >= 0.2 && $p < 0.5) {
			p.update((_) => _ + 0.02);
		} else if ($p >= 0.5 && $p < 0.8) {
			p.update((_) => _ + 0.002);
		} else if ($p >= 0.8 && $p < 0.99) {
			p.update((_) => _ + 0.0005);
		} else {
			p.set(0);
		}

		if ($navigating) {
			const rand = Math.round(Math.random() * (300 - 50)) + 50;
			setTimeout(function () {
				increase();
			}, rand);
		}
	}

	$: {
		if ($navigating) {
			increase();
			isVisible = true;
		}
		if (!$navigating) {
			p.update((_) => _ + 0.3);
			setTimeout(function () {
				p.set(1);
				setTimeout(function () {
					isVisible = false;
					p.set(0);
				}, 100);
			}, 100);
		}
	}
</script>

{#if $p > 0 && $p < 1 && isVisible}
	<progress value={$p} transition:fade={{ duration: 300 }} />
{/if}
<slot />
<!-- 
<div>
	<div>Temp site urls for testing</div>
	<ul>
		Links:
		<li>
			- <a href="/login">User Login</a>
		</li>
		<li>
			- <a href="/register">User Register</a>
		</li>
		<li>
			- <a href="/_/login">Admin Login</a>
		</li>
		<li>
			- <a href="/concepts/site-customizer">Concept: Site Customizer </a>
		</li>
		<li>
			- <a href="/a/b">Concept: User's page</a>
		</li>
	</ul>
</div> -->

<Toaster />

<style>
	progress {
		--bar-color: rgba(255, 255, 255, 0.3);
		--val-color: rgb(41, 41, 41);
		position: fixed;
		top: 0;
		z-index: 99999;
		left: 0;
		height: 3px;
		width: 100vw;
		border-radius: 0;
	}
	/* bar: */
	progress::-webkit-progress-bar {
		background-color: var(--bar-color);
		width: 100%;
	}
	progress {
		background-color: var(--bar-color);
	}

	/* value: */
	progress::-webkit-progress-value {
		background-color: var(--val-color) !important;
	}
	progress::-moz-progress-bar {
		background-color: var(--val-color) !important;
	}
	progress {
		color: var(--val-color);
	}
</style>
