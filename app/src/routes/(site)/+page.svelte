<svelte:head>
    <title>mCMS</title> 
</svelte:head>
<script>
	import Hero from '@/ui/Hero.svelte';
	import Faqs from './Faqs.svelte';
	import NavbarHomePage from '@/ui/NavbarHomePage.svelte';
</script>

<NavbarHomePage />
<Hero />
<Faqs />
<!-- 
<div id="debug" class="mb-32">
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
			- <a href="/a/b">Concept: User's page</a>
		</li>
	</ul>
</div> -->
