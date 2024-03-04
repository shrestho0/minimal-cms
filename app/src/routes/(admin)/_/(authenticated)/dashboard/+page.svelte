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
		{
			title: "Today's Page Count",
			value: data?.todaysPageCount ?? 0,
			icon: LucideAArrowDown,
			type: 'default'
		},
		{
			title: "Today's User Count",
			value: data?.todaysUserCount ?? 0,
			icon: LucideAArrowDown,
			type: 'default'
		},
		{
			title: 'Total Pages',
			value: data?.pageCount ?? 0,
			icon: LucideAArrowDown,
			type: 'default'
		},
		{
			title: 'Total Users',
			value: data?.userCount ?? 0,
			icon: LucideAArrowDown,
			type: 'default'
		}
	];
</script>

<DashboardCard {cardData} />

<div class="grid grid-cols-8 gap-4">
	<!-- Last 5 Updated Pages -->
	<UserPanelItemWrapper className=" col-span-4 my-6 rounded-lg bg-white px-8 py-4 shadow-md">
		<h2 class="py-2 text-lg text-black">Last 5 Users</h2>
		<Table.Root class=" w-full overflow-x-scroll">
			<!-- <Table.Caption>A list of your recent invoices.</Table.Caption> -->
			<Table.Header>
				<Table.Row>
					<Table.Head class="">Username</Table.Head>
					<Table.Head class="">Email</Table.Head>
					<Table.Head>Created</Table.Head>
					<!-- <Table.Head>Created</Table.Head> -->
					<!-- <Table.Head>Last Updated</Table.Head> -->
					<!-- <Table.Head class="text-right">Actions</Table.Head> -->
				</Table.Row>
			</Table.Header>
			<Table.Body>
				{#if data?.last5Users?.length && data?.last5Users?.length > 0}
					{#each data?.last5Users as item}
						<Table.Row>
							<Table.Cell class="font-medium">{item.username}</Table.Cell>
							<Table.Cell>{item.email}</Table.Cell>
							<Table.Cell>{item.created}</Table.Cell>
							<!-- <Table.Cell>{beautiulDateTime(item.created)}</Table.Cell> -->

							<!-- <Table.Cell>{beautiulDateTime(item.updated)}</Table.Cell> -->
							<!-- <Table.Cell class="flex justify-end gap-2 ">
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
							</Table.Cell> -->
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
	<UserPanelItemWrapper className=" col-span-4 my-6 rounded-lg bg-white px-8 py-4 shadow-md">
		<h2 class="py-2 text-lg text-black">Last 5 Users</h2>
		<Table.Root class=" w-full overflow-x-scroll">
			<!-- <Table.Caption>A list of your recent invoices.</Table.Caption> -->
			<Table.Header>
				<Table.Row>
					<Table.Head class="">Title</Table.Head>
					<Table.Head class="">Slug</Table.Head>
					<Table.Head>Created</Table.Head>
					<!-- <Table.Head>Created</Table.Head> -->
					<!-- <Table.Head>Last Updated</Table.Head> -->
					<!-- <Table.Head class="text-right">Actions</Table.Head> -->
				</Table.Row>
			</Table.Header>
			<Table.Body>
				{#if data?.last5Pages?.length && data?.last5Pages?.length > 0}
					{#each data?.last5Pages as item}
						<Table.Row>
							<!-- <Table.Cell class="font-medium">{item.title}</Table.Cell> -->
							<Table.Cell>{item.title}</Table.Cell>
							<Table.Cell>{item.slug}</Table.Cell>
							<Table.Cell>{item.created}</Table.Cell>

							<!-- <Table.Cell>{beautiulDateTime(item.updated)}</Table.Cell> -->
							<!-- <Table.Cell class="flex justify-end gap-2 ">
								<Button
									variant="outline"
									size="sm"
									data-sveltekit-reload
									target="_blank"
									href={'/' + data.user?.username + '/' + item.slug}>View</Button
								>
							</Table.Cell> -->
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
</div>
