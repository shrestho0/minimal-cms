<script lang="ts">
	import { Button } from '@/components/ui/button';
	import PageContentBlock from '../PageContentBlock.svelte';
	import PageHeaderBlock from '../PageHeaderBlock.svelte';

	export let data: {
		admin: import('pocketbase').AdminModel;
	};
</script>

<p>Dashboard content</p>
