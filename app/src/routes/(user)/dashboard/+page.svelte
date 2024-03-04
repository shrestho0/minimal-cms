<script lang="ts">
	import PreDebug from '@/dev/PreDebug.svelte';
	import DashboardCard from '@/ui/DashboardCard.svelte';
	import { LucideAArrowDown } from 'lucide-svelte';
	import * as Table from '$lib/components/ui/table';
	import { beautiulDateTime } from '@/utils/common';
	import { Button } from '@/components/ui/button';
	import UserPanelItemWrapper from '@/ui/UserPanelItemWrapper.svelte';

	export let data;
	let cardData: {
		title: string;
		value: string;
		icon: ConstructorOfATypedSvelteComponent;
		type: 'default' | 'warning';
	}[] = [
		{ title: 'Total Pages', value: data?.totalPages ?? 0, icon: LucideAArrowDown, type: 'default' },
		{
			title: 'Published Pages',
			value: data?.publishedPages ?? 0,
			icon: LucideAArrowDown,
			type: 'default'
		},
		{ title: 'Draft Pages', value: data?.draftPages ?? 0, icon: LucideAArrowDown, type: 'default' },
		{
			title: 'Banned Pages',
			value: data?.bannedPages ?? 0,
			icon: LucideAArrowDown,
			type: data?.bannedPages ? 'warning' : 'default'
		}
	];
</script>

<DashboardCard {cardData} />

<!-- Last 5 Updated Pages -->
<UserPanelItemWrapper className=" my-6 rounded-lg bg-white px-8 py-4 shadow-md">
	<h2 class="py-2 text-lg text-black">Last 5 Updated Pages</h2>
	<Table.Root class=" w-full overflow-x-scroll">
		<!-- <Table.Caption>A list of your recent invoices.</Table.Caption> -->
		<Table.Header>
			<Table.Row>
				<Table.Head class="w-[200px]">Title</Table.Head>
				<Table.Head class="w-[100px]">Slug</Table.Head>
				<Table.Head>Status</Table.Head>
				<Table.Head>Created</Table.Head>
				<Table.Head>Last Updated</Table.Head>

				<Table.Head class="text-right">Actions</Table.Head>
			</Table.Row>
		</Table.Header>
		<Table.Body>
			{#if data?.last5Pages?.length && data?.last5Pages?.length > 0}
				{#each data?.last5Pages as item}
					<Table.Row>
						<Table.Cell class="font-medium">{item.title}</Table.Cell>
						<Table.Cell>{item.slug}</Table.Cell>
						<Table.Cell>{item.status}</Table.Cell>
						<Table.Cell>{beautiulDateTime(item.created)}</Table.Cell>

						<Table.Cell>{beautiulDateTime(item.updated)}</Table.Cell>
						<Table.Cell class="flex justify-end gap-2 ">
							<Button
								variant="outline"
								size="sm"
								data-sveltekit-reload
								target="_blank"
								href={'/' + data.user?.username + '/' + item.slug}>View</Button
							>

							<Button
								variant="default"
								size="sm"
								data-sveltekit-reload
								href={'/pages' + '/' + item.id}>Edit</Button
							>
						</Table.Cell>
					</Table.Row>
				{/each}
			{:else}
				<td colspan="4" class="w-full py-4 text-center">
					<p>No Data Found!</p>
				</td>
			{/if}
		</Table.Body>
	</Table.Root>
</UserPanelItemWrapper>
<!-- <PreDebug {data} /> -->
