<script>
	import '$lib/ui/app.pcss';
	import { Button } from '@/components/ui/button';
	import { Toaster, toast } from 'svelte-sonner';
</script>

<slot />

<Toaster />
<!-- 
<pre>DEBUG</pre>
<div class="p-8">
	Debug pages:
	<ul class=" flex flex-col">
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
			- <a href="/concepts/site-customizer">Concept: Site Customizer </a>
		</li>
	</ul>
</div> -->
