<script>
	import { ModeWatcher, setMode, mode } from 'mode-watcher';
	import { onMount } from 'svelte';
	onMount(() => {
		setMode('dark');
	});
</script>

<div class=" mx-auto max-w-screen-xl dark:bg-stone-950">
	<slot />
</div>
<ModeWatcher defaultMode="dark" />
