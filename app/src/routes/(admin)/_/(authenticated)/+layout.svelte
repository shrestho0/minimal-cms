<script lang="ts">
	import LightSwitch from '@/ui/LightSwitch.svelte';
	import Logo from '@/ui/Logo.svelte';
	import { ModeWatcher } from 'mode-watcher';
	import PageHeaderBlock from './PageHeaderBlock.svelte';
	import PageContentBlock from './PageContentBlock.svelte';
	export let data: { admin: import('pocketbase').AdminModel };
</script>

<div class="h-screen w-full">
	<div class="flex h-screen w-full overflow-y-hidden">
		<aside
			class="hidden h-full w-[30%] max-w-[300px] border-r border-gray-200 bg-white md:block dark:border-stone-800 dark:bg-stone-950"
		>
			<div class="flex h-16 items-center justify-center gap-3 border-b border-gray-200">
				<Logo />
			</div>
		</aside>
		<aside class=" w-full bg-gray-50">
			<PageHeaderBlock user={data?.admin} title="Dashboard" />
			<PageContentBlock>
				<slot />
			</PageContentBlock>
		</aside>
	</div>
</div>
