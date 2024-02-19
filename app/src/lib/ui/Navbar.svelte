<script lang="ts">
	import { AppLinks } from '@/utils/app-links';
	import Logo from './Logo.svelte';
	import Button from '@/components/ui/button/button.svelte';
	import { page } from '$app/stores';
	import Link from './Link.svelte';
	import { fly, slide } from 'svelte/transition';
	import { onMount } from 'svelte';

	let open = true;
	function toggleMobileMenu() {
		open = !open;
	}

	export const menuitems = [
		{
			title: 'Features',
			path: '/features'
		},
		{
			title: 'About',
			path: '/about'
		},
		{
			title: 'Contact',
			path: '/contact'
		}
	] as Array<{ title: string; path: string }>;

	let innerWidth: number;
	$: {
		if (innerWidth < 768) {
			open = false;
		} else {
			open = true;
		}
	}
</script>

<!-- svelte window size -->
<svelte:window bind:innerWidth />

<div
	class="relative z-10 mx-auto flex max-w-screen-xl flex-col px-4 md:flex-row md:items-center md:justify-between md:px-6 md:pt-2 lg:px-8"
>
	<div class="flex flex-row items-center justify-between p-4">
		<a
			href={AppLinks.HOME}
			class="focus:shadow-outline flex items-center gap-3 rounded-lg text-lg font-semibold tracking-widest text-gray-900 focus:outline-none"
		>
			<Logo />
		</a>
		<button
			class="relative h-10 w-10 text-gray-500 focus:outline-none md:hidden"
			on:click={toggleMobileMenu}
		>
			<span class="sr-only">Open main menu</span>
			<div class="absolute left-1/2 top-1/2 block w-5 -translate-x-1/2 -translate-y-1/2 transform">
				<span
					aria-hidden="true"
					class=" {open
						? 'rotate-45'
						: '-translate-y-1.5'}  absolute block h-0.5 w-5 transform bg-current transition duration-200 ease-in-out"
				>
				</span>
				<span
					aria-hidden="true"
					class="{open
						? 'opacity-0'
						: ''} absolute block h-0.5 w-5 transform bg-current transition duration-200 ease-in-out"
				>
				</span>
				<span
					aria-hidden="true"
					class="{open
						? '-rotate-45'
						: 'translate-y-1.5'} absolute block h-0.5 w-5 transform bg-current transition duration-200 ease-in-out"
				>
				</span>
			</div>
		</button>
	</div>

	<!-- x-transition:enter="transition ease-out duration-200"
    x-transition:enter-start="opacity-0 transform scale-90"
    x-transition:enter-end="opacity-100 transform scale-100"
    x-transition:leave="transition ease-in duration-200"
    x-transition:leave-start="opacity-100 transform scale-100"
    x-transition:leave-end="opacity-0 transform scale-90" -->
	{#if open}
		<nav
			class="flex w-full flex-grow flex-col gap-2 pb-4 transition-all ease-out md:mt-0 md:flex-row md:items-center md:justify-center md:pb-0"
			in:slide
			out:slide
		>
			<div
				class="flex flex-grow flex-col gap-2 text-center md:mx-auto md:flex md:flex-row md:items-center md:justify-center"
			>
				<!-- svelte-ignore a11y-click-events-have-key-events -->
				<!-- svelte-ignore a11y-no-static-element-interactions -->
				<div
					on:click={() => {
						open = false;
					}}
					class="relative"
				>
					{#each menuitems as items (items.title)}
						<Link
							on:click={toggleMobileMenu}
							href={items.path}
							style="hover"
							size="sm"
							className={$page.url.pathname.startsWith(items.path)
								? 'bg-gray-200 dark:bg-gray-600 '
								: ''}
						>
							{items.title}
						</Link>
					{/each}
				</div>
			</div>
		</nav>

		<div
			in:slide
			out:slide
			class=" mt-4 flex flex-col gap-4 transition-all ease-out md:mt-0 md:flex-row"
		>
			<!-- <Link href="/login" style="outline">Login</Link> -->
			<!-- <Link href="/register">Register</Link> -->
			<Button href="/login" variant="default" size="lg">Login</Button>
			<Button href="/login" variant="outline" size="lg">Login</Button>
		</div>
	{/if}
</div>
