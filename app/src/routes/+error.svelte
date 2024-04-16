<script lang="ts">
	import { page } from '$app/stores';
	import PreDebug from '@/dev/PreDebug.svelte';
	import { onMount } from 'svelte';
	import NavbarHomePage from '@/ui/NavbarHomePage.svelte';
	import HeroWrapper from '@/ui/HeroWrapper.svelte';
	import { ModeWatcher } from 'mode-watcher';
	import { Button } from '@/components/ui/button';
	import Logo from '@/ui/Logo.svelte';
	import { AppLinks } from '@/utils/app-links';

	let loading = true;
	onMount(() => {
		loading = false;
		console.log('page', $page);
	});
</script>

<span class="hero-gradient"></span>
<div class="home-bg h-screen w-full p-8">
	<div class="flex w-full items-center justify-between p-4 md:p-8">
		<Logo className="text-new-ui  " />
		<div class="z-10 flex items-center justify-center gap-3 text-new-ui">
			{#if $page?.data?.user}
				<Button
					href={AppLinks.USER_DASHBOARD}
					variant="outline"
					class="bg-transparent  text-new-ui ">Dashboard</Button
				>{:else}
				<!-- <LightSwitch /> -->
				<Button href={AppLinks.USER_REGISTER} variant="outline" class="bg-transparent text-new-ui"
					>Register</Button
				>
				<Button href={AppLinks.LOGIN} variant="outline" class="bg-transparent  text-new-ui "
					>Login</Button
				>
			{/if}
		</div>
	</div>
	<div class="mx-auto flex h-[80%] flex-col items-center justify-center px-5 text-new-ui">
		<div class="max-w-md text-center">
			<!-- 
			<h2 class="mb-8 text-9xl font-bold">
				<span class="sr-only">Error</span> <span class=" text-white/90">{$page.status}</span>
			</h2>
			-->
			<p class="text-3xl font-semibold md:text-3xl">Sorry, {$page.error?.message}</p>
			<p class=" mb-8 mt-4 text-lg text-new-ui/90">
				But dont worry, you can find plenty of other things on our homepage.
			</p>
			<a
				rel="noopener noreferrer"
				href="/"
				class="btn ml-4 rounded border p-2 hover:bg-new-accent hover:text-white">Back to homepage</a
			>
		</div>
	</div>
</div>
