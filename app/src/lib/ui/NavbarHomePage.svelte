<script lang="ts">
	import { AppLinks } from '@/utils/app-links';
	import Logo from '@/ui/Logo.svelte';
	import Button from '@/components/ui/button/button.svelte';
	import LightSwitch from '@/ui/LightSwitch.svelte';
	import { toast } from 'svelte-sonner';
	import { browser } from '$app/environment';
	import { fly, slide } from 'svelte/transition';
	import { page } from '$app/stores';
	import Logout from './Logout.svelte';

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
			title: 'Testimonials',
			id: 'testimonials'
		},
		{
			title: 'FAQs',
			id: 'faqs'
		},
		{
			title: 'Contribute',
			id: 'contribute'
		}
	] as Array<{ title: string; id: string }>;

	let innerWidth: number;
	let scrollY: number;
	let onMobile = false;
	let navbareKajHobe = false;

	$: open = innerWidth > 768;
	$: onMobile = innerWidth <= 768;

	$: navbareKajHobe = scrollY > 100;

	let selectedIdx = -1; // -1

	function scrollToPosTop(top: number = 0) {
		if (!browser) return;
		window.scrollTo({
			top: top,
			behavior: 'smooth'
		});

		return;
	}

	function handleAnchorClick(event: any) {
		event.preventDefault();

		const link = event.currentTarget;

		const anchorId = new URL(link.href).hash.replace('#', '');

		const anchor = document.getElementById(anchorId);

		if (anchor) {
			// Find id from the list
			menuitems.forEach((el, idx) => {
				// if(el.id) ==
				if (el.id == anchorId) {
					selectedIdx = idx;
				}
				return;
			});

			scrollToPosTop(anchor.offsetTop);
			return;
		}

		const end = link?.href?.split('/')?.pop();
		if (end == '') {
			selectedIdx = -1;
			scrollToPosTop(0);
			return;
		}

		toast.warning('page with id is not implemented');
	}
</script>

<svelte:window bind:innerWidth bind:scrollY />
<div
	class="fixed left-0 z-50 mx-auto grid w-full grid-cols-2 md:grid-cols-12 {navbareKajHobe
		? 'border-b border-gray-100/80 bg-primary/50'
		: ''} p-4 backdrop-blur"
>
	<div class="col-span-1 flex w-full items-center md:col-span-2 md:justify-center">
		<a on:click={handleAnchorClick} href={AppLinks.HOME} class="">
			<Logo className="text-white/90" />
		</a>
	</div>
	<div
		class="relative hidden text-center md:col-span-7 md:flex items-center justify-center "
		role="button"
		tabindex="0"
		on:click={() => {
			if (onMobile) toggleMobileMenu();
		}}
		on:keypress
	>
		{#each menuitems as items, idx (items.title)}
			<a
				href={'#' + items.id}
				on:click={handleAnchorClick}
				class="rounded-md px-3 py-1.5 text-sm font-semibold text-white transition focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2
			{selectedIdx != undefined && selectedIdx == idx ? 'underline' : ''}
			">{items.title}</a
			>
		{/each}
	</div>


	<div class="block w-full text-right md:hidden">
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

		{#if open}
		<div
			role="button"
			on:click={() => {
				if (onMobile) toggleMobileMenu();
			}}
			tabindex="0"
			on:keypress

			in:slide
			out:slide


			class="mobile-menu focus:shadow-outline absolute left-0 w-full flex-col text-left transition-all ease-out flex my-3 py-6 items-center gap-3 rounded-lg border-gray-100/80 bg-primary/80 text-lg font-semibold tracking-widest text-white focus:outline-none"
		>
			{#each menuitems as items, idx (items.title)}
				<a
					href={'#' + items.id}
					on:click={handleAnchorClick}
					class="rounded-md px-3 py-1.5 text-sm font-semibold transition focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2
			{selectedIdx != undefined && selectedIdx == idx ? 'underline' : ''}
			">{items.title}</a
				>
			{/each}

			{#if $page?.data.user}
			<Button
				data-sveltekit-reload
				href={AppLinks.USER_DASHBOARD}
				variant="outline"
				class="w-full bg-transparent text-white/90">Dashboard</Button
			>
			<Logout formClasses="w-full " btnClasses="w-full bg-transparent text-white/90" />
		{:else if $page?.data.admin}
			<Button href={AppLinks.ADMIN_DASHBOARD} variant="outline">Dashboard</Button>
		{:else}
			<Button href="/login" variant="outline" class="bg-transparent  w-full text-white/90">Login</Button>
			<Button href="/register" class="w-full text-black" variant="outline">Register</Button>
		{/if}
		</div>
		{/if}
	</div>

	
	<div class=" col-span-3 hidden gap-3 md:flex">
		{#if $page?.data.user}
			<Button
				data-sveltekit-reload
				href={AppLinks.USER_DASHBOARD}
				variant="outline"
				class="bg-transparent text-white/90">Dashboard</Button
			>
			<Logout btnClasses="bg-transparent text-white/90" />
		{:else if $page?.data.admin}
			<Button href={AppLinks.ADMIN_DASHBOARD} variant="outline">Dashboard</Button>
		{:else}
			<Button href="/login" variant="outline" class="bg-transparent text-white/90">Login</Button>
			<Button href="/register" variant="outline">Register</Button>
		{/if}
	</div>

</div>
