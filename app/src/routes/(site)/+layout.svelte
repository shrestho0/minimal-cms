<script>
	import Hero from '@/ui/HeroWrapper.svelte';
	import Navbar from '@/ui/NavbarHomePage.svelte';
	import { ModeWatcher } from 'mode-watcher';
</script>

<div class=" mx-auto max-w-screen-xl dark:bg-stone-950">
	<slot />
</div>
<ModeWatcher />
