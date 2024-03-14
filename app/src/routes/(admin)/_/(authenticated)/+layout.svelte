<script lang="ts">
	import LightSwitch from '@/ui/LightSwitch.svelte';
	import Logo from '@/ui/Logo.svelte';
	import { ModeWatcher } from 'mode-watcher';
	import PageHeaderBlock from '@/ui/PageHeaderBlock.svelte';
	import PageContentBlock from '@/ui/PageContentBlock.svelte';
	import { page } from '$app/stores';
	import Separator from '@/components/ui/separator/separator.svelte';
	import SidePanel from '@/ui/SidePanel.svelte';
	import { adminPanelPages, userPanelPages } from '@/utils/authenticated-links';
	import type { Admin } from '@/types/entity';
	export let data: {
		admin: Admin;
	};
	function sanitizeTitle(title: string) {
		// - to space
		title = title.replace(/-/g, ' ').replace(/\b\w/g, (l) => l.toUpperCase());
		// / to none
		title = title.replace(/\//g, '');
		// _ to none
		title = title.replace(/_/g, '');

		return title;
	}
</script>

<div class="h-screen w-full">
	<div class="flex h-screen w-full overflow-y-hidden">
		<aside class="hidden h-full w-[30%] max-w-[300px] border-r border-gray-200 bg-white md:block">
			<div class="flex h-16 items-center justify-center gap-3 border-b border-gray-200">
				<Logo className="text-black" />
			</div>

			<SidePanel pages={adminPanelPages} />
		</aside>
		<aside class=" w-full bg-gray-50">
			<PageHeaderBlock
				user={data?.admin}
				title={sanitizeTitle($page.url.pathname)}
				pages={adminPanelPages}
			/>
			<PageContentBlock>
				<slot />
			</PageContentBlock>
		</aside>
	</div>
</div>
