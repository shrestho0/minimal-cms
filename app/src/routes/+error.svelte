<script lang="ts">
	import { page } from '$app/stores';
	import PreDebug from '@/dev/PreDebug.svelte';
</script>

Error Page
<PreDebug data={$page} />
