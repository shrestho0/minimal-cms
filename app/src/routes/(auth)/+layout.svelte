<script>
	import HeroWrapper from '@/ui/HeroWrapper.svelte';
	import Logo from '@/ui/Logo.svelte';
	import { AppLinks } from '@/utils/app-links';
	import { Button } from '@/components/ui/button';
	import { page } from '$app/stores';
	import { ModeWatcher } from 'mode-watcher';
	import LightSwitch from '@/ui/LightSwitch.svelte';
</script>

<HeroWrapper />

<div class="container relative h-screen flex-col items-center justify-center">
	<div class="flex w-full items-center justify-between p-4 md:p-8">
		<Logo className="" />
		<div class="flex items-center justify-center gap-3">
			<LightSwitch />
			{#if $page.url.pathname?.includes('login')}
				<Button href={AppLinks.USER_REGISTER} variant="outline" class="">Register</Button>
			{:else if $page.url.pathname?.includes('register')}
				<Button href={AppLinks.USER_LOGIN} variant="outline" class="">Login</Button>
			{/if}
		</div>
	</div>
	<slot />
</div>
<ModeWatcher defaultMode="dark"/>