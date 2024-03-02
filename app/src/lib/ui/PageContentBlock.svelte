<script>
	import Lorem3000 from '@/dev/Lorem3000.svelte';
</script>

<div
	class=" max-h-screen overflow-y-scroll px-4 py-2 text-black"
	style="height:calc(100vh - 4rem);"
>
	<!-- <Lorem3000 /> -->

	<slot />

	<br /><br /><br />
</div>
