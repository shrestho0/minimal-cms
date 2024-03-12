<script>
	import { ModeWatcher, setMode, mode } from 'mode-watcher';
	import { onMount } from 'svelte';
	onMount(() => {
		setMode('dark');
	});
</script>

<slot />
<!-- <ModeWatcher defaultMode="dark" /> -->
