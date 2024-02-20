<script lang="ts">
	import { AppLinks } from '@/utils/app-links';
	import Logo from './Logo.svelte';
	import Button from '@/components/ui/button/button.svelte';
	import { page } from '$app/stores';
	import Link from './Link.svelte';
	import { fly, slide } from 'svelte/transition';
	import { onMount } from 'svelte';
	import LightSwitch from './LightSwitch.svelte';

	let open = true;
	function toggleMobileMenu() {
		open = !open;
	}

	export const menuitems = [
		{
			title: 'Features',
			id: 'features'
		},
		{
			title: 'About',
			id: 'about'
		},
		{
			title: 'FAQs',
			id: 'faqs'
		},
		{
			title: 'Contact',
			id: 'contact'
		}
	] as Array<{ title: string; id: string }>;

	let innerWidth: number;
	let scrollY: number;
	let onMobile = false;
	$: {
		if (innerWidth < 768) {
			open = false;
			onMobile = true;
		} else {
			open = true;
			onMobile = false;
		}
	}

	let selectedIdx = 0;
</script>

<!-- svelte window size -->
<svelte:window bind:innerWidth bind:scrollY />

<div
	class="fixed z-50 mx-auto flex w-full max-w-screen-xl flex-col bg-stone-950 px-4 md:flex-row md:items-center md:justify-between md:bg-transparent md:px-6 md:pt-2 lg:px-8"
>
	<div class="flex flex-row items-center justify-between p-4">
		<!-- Take Old Classes Back for dark mode -->
		<a
			href={AppLinks.HOME}
			class="focus:shadow-outline flex items-center gap-3 rounded-lg text-lg font-semibold tracking-widest text-gray-900 focus:outline-none"
		>
			<Logo mode="dark" />
		</a>
		<div class="flex items-center justify-center gap-2">
			<LightSwitch className="flex md:hidden" />
			<button
				class="relative h-10 w-10 text-gray-500 focus:outline-none md:hidden"
				on:click={toggleMobileMenu}
			>
				<span class="sr-only">Open main menu</span>
				<div
					class="absolute left-1/2 top-1/2 block w-5 -translate-x-1/2 -translate-y-1/2 transform"
				>
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
	</div>

	{#if open}
		<nav
			class="flex w-full {scrollY > 100
				? 'rounded border border-gray-900 bg-gray-50/80 md:mx-16 lg:mx-32 xl:mx-48 dark:border-gray-100 dark:bg-stone-950/80 '
				: ''} flex-grow flex-col gap-2 pb-4 transition-all ease-out md:mt-0 md:flex-row md:items-center md:justify-center md:pb-0"
			in:slide
			out:slide
		>
			<div
				class="flex flex-grow flex-col gap-2 text-center md:mx-auto md:flex md:flex-row md:items-center md:justify-center"
			>
				<!-- svelte-ignore a11y-interactive-supports-focus -->
				<!-- svelte-ignore a11y-click-events-have-key-events -->
				<div
					role="button"
					on:click={() => {
						if (onMobile) toggleMobileMenu();
					}}
					class="relative"
				>
					{#each menuitems as items (items.title)}
						<Link
							on:click={() => {
								console.log('Scrolling to ', items.id);
							}}
							style="hover"
							size="sm"
							className={$page.url.pathname.startsWith(items.id)
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
			<!-- Hide On Mobile -->
			<LightSwitch className="hidden md:flex" />
			<!-- <Link href="/login" style="outline">Login</Link> -->
			<!-- <Link href="/register">Register</Link> -->
			<Button href="/login" variant="default">Login</Button>
			<Button href="/login" variant="outline">Register</Button>
		</div>
	{/if}
</div>
