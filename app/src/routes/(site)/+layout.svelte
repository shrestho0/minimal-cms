<script>
	import Hero from '@/ui/Hero.svelte';
	import Navbar from '@/ui/Navbar.svelte';
</script>

<div class="mx-auto max-w-screen-xl px-5">
	<Navbar />
	<Hero />

	<slot />
</div>
