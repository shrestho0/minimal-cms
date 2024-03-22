<script>
	import HeroWrapper from '@/ui/HeroWrapper.svelte';
	import Logo from '@/ui/Logo.svelte';
	import { AppLinks } from '@/utils/app-links';
	import { Button } from '@/components/ui/button';
	import { page } from '$app/stores';
	import { ModeWatcher, setMode } from 'mode-watcher';
	import LightSwitch from '@/ui/LightSwitch.svelte';
	import { onMount } from 'svelte';
 
</script>

<!-- <HeroWrapper /> -->

<div class="home-bg h-screen p-8 w-full ">
	<div class="flex w-full items-center justify-between p-4 md:p-8">
		<Logo className="text-new-ui  "   />
		<div class="flex items-center justify-center gap-3 z-10 text-new-ui">
			<!-- <LightSwitch /> -->
			{#if $page.url.pathname?.includes('login')}
				<Button href={AppLinks.USER_REGISTER} variant="outline" class="bg-transparent text-new-ui">Register</Button>
			{:else if $page.url.pathname?.includes('register')}
				<Button href={AppLinks.LOGIN} variant="outline" class="bg-transparent  text-new-ui ">Login</Button>
			{/if}
		</div>
	</div>
	<slot />
</div>
