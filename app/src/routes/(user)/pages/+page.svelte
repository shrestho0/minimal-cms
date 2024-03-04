<script lang="ts">
	import { Alert } from '@/components/ui/alert';
	import Button from '@/components/ui/button/button.svelte';
	import Input from '@/components/ui/input/input.svelte';
	import Label from '@/components/ui/label/label.svelte';
	import PreDebug from '@/dev/PreDebug.svelte';
	import UserPanelItemWrapper from '@/ui/UserPanelItemWrapper.svelte';
	import * as Table from '$lib/components/ui/table';
	import type { SinglePage } from '@/types/entity';
	import { ArrowLeft, ArrowRight, ChevronLeft, ChevronRight, CircleDotDashed } from 'lucide-svelte';
	import type { User } from '@/types/entity';
	import { beautiulDateTime } from '@/utils/common';
	import * as AlertDialog from '$lib/components/ui/alert-dialog';
	import type { ActionResult } from '@sveltejs/kit';
	import { applyAction, enhance } from '$app/forms';
	import { invalidateAll } from '$app/navigation';
	import { toast } from 'svelte-sonner';
	import { ErrorMessages } from '@/utils/messages';
	import { navigating, page } from '$app/stores';
	import * as Pagination from '$lib/components/ui/pagination';

	export let data: Partial<{
		now: Date;
		page: number;
		perPage: number;
		totalItems: number;
		totalPages: number;
		items: SinglePage[];
		user: User;
	}> = {
		now: new Date(),
		page: 1,
		perPage: 5,
		totalItems: 0,
		totalPages: 0
	};

	let deleteDrawerOpen = false;
	let deletingPage = false;
	let selectedForDeleteItem: SinglePage | null = null;

	function enhancedPageDelete() {
		return async ({ result }: { result: ActionResult }) => {
			deletingPage = true;
			// Do SOmething

			switch (result.type) {
				case 'success':
					toast.success(result?.data?.message, {
						duration: 3000,
						position: 'top-right',
						class: 'mt-4'
					});

					await applyAction(result);
					invalidateAll();
					break;
				case 'failure':
					toast.error(result?.data?.message || ErrorMessages.DEFAULT_ERROR, {
						duration: 3000,
						position: 'top-right',
						class: 'mt-4'
					});
					break;
				default:
					break;
			}

			deletingPage = false;
			deleteDrawerOpen = false;
		};
	}

	function sanitizePaginationLink(number: number) {
		let url = $page.url.toString();

		if (url.includes('page=')) {
			url = url.replace(/page=\d+/, `page=${number}`);
		} else if (url.includes('?')) {
			url = url + `&page=${number}`;
		} else {
			url = url + `?page=${number}`;
		}

		return url;
	}

	// let nextPageUrl = sanitizePaginationLink(data.page + 1);
	// let prevPageUrl = sanitizePaginationLink(data.page);

	let limitItems = [
		{ name: '5', value: 5 },
		{ name: '10', value: 10 },
		{ name: '25', value: 25 },
		{ name: '50', value: 50 }
	];

	let statusItems = [
		{ name: 'All', value: '' },
		{ name: 'Published', value: 'published' },
		{ name: 'Banned', value: 'banned' },
		{ name: 'Draft', value: 'draft' }
	];
</script>

<UserPanelItemWrapper>
	<div>
		<h1>Filter & Search</h1>
		<form class="mt-3 flex w-full flex-col justify-center gap-3">
			<div class="grid w-full grid-cols-3 gap-3">
				<div class="grid w-full max-w-sm items-center gap-1.5">
					<Label for="status">Status</Label>
					<select
						class="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50"
						name="status"
					>
						<!-- <option value="">All</option>
						<option value="published">Published</option>
						<option value="banned">Banned</option>
						<option value="draft">Draft</option> -->
						{#each statusItems as item}
							<option
								selected={$page.url.searchParams.get('status') == item.value}
								value={item.value}>{item.name}</option
							>
						{/each}
					</select>
				</div>
				<!-- order by -->
				<!-- <div class="grid w-full max-w-sm items-center gap-1.5">
					<Label for="sort">Sort By</Label>
					<select
						class="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50"
						name="sort"
					>
						{$page.url.searchParams.get('sort')}
						{#each sortItems as item}
							<option selected={$page.url.searchParams.get('sort') == item.value} value={item.value}
								>{item.name}</option
							>
						{/each} 
					</select>
				</div> -->
				<div class="grid w-full max-w-sm items-center gap-1.5">
					<Label for="limit">Limit</Label>
					<select
						class="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50"
						name="limit"
					>
						{#each limitItems as item}
							<option
								selected={($page.url.searchParams.get('limit') ?? 5) == item.value}
								value={item.value}>{item.name}</option
							>
						{/each}
					</select>
				</div>
			</div>
			<Input
				name="q"
				type="text"
				placeholder="Search for page, with titles, slugs, or contents"
				value={$page.url.searchParams.get('q')}
			/>
			<div class="grid grid-cols-4 gap-3">
				<Button
					variant="outline"
					class="w-full"
					type="reset"
					on:click={() => {
						// remove all query params from url
					}}>Reset</Button
				>
				<Button class="col-span-3 w-full" type="submit">Filter Pages</Button>
			</div>
		</form>
	</div>
</UserPanelItemWrapper>
<UserPanelItemWrapper>
	<!-- Data Table -->
	<h2>Total items <span>{data.totalItems}</span></h2>
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
			{#if $navigating}
				<!-- Loading Spinner -->
				<td colspan={6} class="w-full py-4 text-center">
					<div class="flex w-full items-center justify-center gap-3">
						<CircleDotDashed class="h-5 w-5 animate-spin" />
						Loading...
					</div>
				</td>
			{:else if data?.items?.length && data?.items?.length > 0}
				{#each data?.items as item}
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
							<Button
								variant="outline"
								class=" bg-indigo-400 text-white "
								on:click={() => {
									selectedForDeleteItem = item;
									deleteDrawerOpen = !deleteDrawerOpen;
								}}
								size="sm">Delete</Button
							>
						</Table.Cell>
					</Table.Row>
				{/each}
				<!-- Pagination -->
				<!-- page={data.page}
					perPage={data.perPage}
					totalItems={data.totalItems}
					totalPages={data.totalPages} -->
			{:else}
				<td colspan="4" class="w-full py-4 text-center">
					<p>No Data Found!</p>
				</td>
			{/if}
		</Table.Body>
	</Table.Root>
	{#if data?.totalItems && data?.totalItems > 0}
		<div class="pagination">
			<div class="buttons flex items-center justify-center gap-4">
				{#if data?.page && data?.page > 1}
					<Button variant="outline" href={sanitizePaginationLink(data?.page ? data.page - 1 : 1)}>
						<ChevronLeft />
					</Button>
				{:else}
					<Button variant="outline" disabled>
						<ChevronLeft />
					</Button>
				{/if}
				<div class="current">
					<Button variant="outline">
						{data.page} / {data.totalPages}
					</Button>
				</div>

				{#if data?.page && data?.totalPages && data.page < data.totalPages}
					<Button variant="outline" href={sanitizePaginationLink(data?.page ? data.page + 1 : 1)}>
						<ChevronRight />
					</Button>
				{:else}
					<Button variant="outline" disabled>
						<ChevronRight />
					</Button>
				{/if}
			</div>
		</div>
	{/if}
	<!-- <PreDebug {data} /> -->
</UserPanelItemWrapper>

<AlertDialog.Root bind:open={deleteDrawerOpen}>
	<AlertDialog.Content>
		<AlertDialog.Header>
			<AlertDialog.Title>Are you sure to delete this page?</AlertDialog.Title>
			<AlertDialog.Description class="font-sm">
				<div class="grid grid-cols-4 items-center gap-4">
					<Label for="ptitle" class="text-right">Page Title:</Label>
					<Label id="ptitle" class="col-span-3">{selectedForDeleteItem?.title}</Label>
				</div>
				<div class="grid grid-cols-4 items-center gap-4">
					<Label for="pslug" class="text-right">Page Slug:</Label>
					<Label id="pslug" class="col-span-3 disabled:text-black"
						>{selectedForDeleteItem?.slug}</Label
					>
				</div>
				<div class="grid grid-cols-4 items-center gap-4">
					<Label for="pstatus" class="text-right">Status:</Label>
					<Label id="ptitle" class="col-span-3">{selectedForDeleteItem?.status}</Label>
				</div>
			</AlertDialog.Description>
		</AlertDialog.Header>
		<AlertDialog.Footer>
			<AlertDialog.Cancel>Cancel</AlertDialog.Cancel>
			<!-- <AlertDialog.Action> -->
			<form action="?/deletePage" method="post" use:enhance={enhancedPageDelete}>
				<input type="hidden" name="id" value={selectedForDeleteItem?.id} />
				<Button
					type="submit"
					class="bg-red-500 text-white"
					on:click={() => {
						deletingPage = true;
					}}
				>
					{#if deletingPage}
						<CircleDotDashed class="mr-2 h-4 w-4 animate-spin stroke-white dark:stroke-stone-950" />
						<span>Deleting...</span>
					{:else}
						Delete
					{/if}
				</Button>
			</form>
			<!-- </AlertDialog.Action> -->
		</AlertDialog.Footer>
	</AlertDialog.Content>
</AlertDialog.Root>
<!-- <PreDebug {data} /> -->
<!-- <div>
	Dev
	<li><a href="/pages/published-page">Published Page</a></li>
	<li><a href="/pages/banned-page">Banned Page</a></li>
	<li><a href="/pages/draft-page">Saved as Draft Page</a></li>
</div> -->
