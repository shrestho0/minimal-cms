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

	export let showMenuItems = true;

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
		},

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
<div class="fixed z-50 mx-auto grid w-full grid-cols-12 p-4 backdrop-blur">
	<div class="col-span-2 w-full flex justify-center  ">
		<a
			on:click={handleAnchorClick}
			href={AppLinks.HOME}
			class=""
		>
			<Logo className="text-white/90" />
		</a>
	</div>
	<div class="col-span-7 relative text-center"
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
	<div class="col-span-3 ">	{#if $page?.data.user}
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
	{/if}</div>
</div>

<style>
	/* .backdrop-blur {
    --tw-backdrop-blur: blur(8px);
    -webkit-backdrop-filter: var(--tw-backdrop-blur) var(--tw-backdrop-brightness) var(--tw-backdrop-contrast) var(--tw-backdrop-grayscale) var(--tw-backdrop-hue-rotate) var(--tw-backdrop-invert) var(--tw-backdrop-opacity) var(--tw-backdrop-saturate) var(--tw-backdrop-sepia);
    backdrop-filter: var(--tw-backdrop-blur) var(--tw-backdrop-brightness) var(--tw-backdrop-contrast) var(--tw-backdrop-grayscale) var(--tw-backdrop-hue-rotate) var(--tw-backdrop-invert) var(--tw-backdrop-opacity) var(--tw-backdrop-saturate) var(--tw-backdrop-sepia)
} */
</style>
