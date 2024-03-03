<script lang="ts">
	import Button from '@/components/ui/button/button.svelte';
	import Input from '@/components/ui/input/input.svelte';
	import Label from '@/components/ui/label/label.svelte';
	import PreDebug from '@/dev/PreDebug.svelte';
	import { page } from '$app/stores';
	import UserPanelItemWrapper from '@/ui/UserPanelItemWrapper.svelte';
	import * as Table from '$lib/components/ui/table';
	import { ChevronLeft, ChevronRight, ExternalLink, CircleDotDashed } from 'lucide-svelte';
	import type { User } from '@/types/users';
	import { beautiulDateTime } from '@/utils/common';

	import * as AlertDialog from '$lib/components/ui/alert-dialog';
	import { applyAction, enhance } from '$app/forms';
	import type { ActionResult } from '@sveltejs/kit';
	import { toast } from 'svelte-sonner';
	import { invalidateAll } from '$app/navigation';
	import { ErrorMessages } from '@/utils/messages';

	export let data: {
		page: number;
		perPage: number;
		totalItems: number;
		totalPages: number;
		items: User[];
	} = {
		page: 1,
		perPage: 5,
		totalItems: 0,
		totalPages: 0,
		items: []
	};

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

	const params = {
		sort: {
			options: [
				{ value: '-created', label: 'Created (DESC)' },
				{ value: 'created', label: 'Created (ASC)' },
				{ value: '-updated', label: 'Updated (DESC)' },
				{ value: 'updated', label: 'Updated (ASC)' }
			],
			value: '-created'
		},
		limit: {
			options: [5, 10, 25, 50],
			value: 5
		},
		qu: ''
	};

	// selected user
	let selectedUser: User;
	let viewModalOpen = false;
	let deleteModalOpen = false;
	let deletingUser = false;
	let updatingUser = false;
	let updateModalOpen = false;
	let updatedMessage = '';
	let showChangePasswordField = false;

	const userUpdateErrors = {
		username: '',
		email: '',
		name: '',
		password: ''
	};

	function enhancedUserDelete() {
		return async ({ result }: { result: ActionResult }) => {
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

			deletingUser = false;
			deleteModalOpen = false;

			//@ts-ignore
			selectedUser = null;
		};
	}
	function enhancedUserUpdate() {
		return async ({ result }: { result: ActionResult }) => {
			// Do SOmething

			switch (result.type) {
				case 'success':
					// toast.success(result?.data?.message, {
					// 	duration: 3000,
					// 	position: 'top-right',
					// 	class: 'mt-4'
					// });
					updatedMessage = result?.data?.message;
					// updateModalOpen = false;

					selectedUser = result?.data?.updatedUser;

					invalidateAll();
					break;
				case 'failure':
					updatedMessage = result?.data?.message || ErrorMessages.DEFAULT_ERROR;
					// toast.error(result?.data?.message || ErrorMessages.DEFAULT_ERROR, {
					// 	duration: 3000,
					// 	position: 'top-right',
					// 	class: 'mt-4'
					// });
					break;
				default:
					break;
			}
			await applyAction(result);
			updatingUser = false;

			//@ts-ignore
			// selectedUser = null;
		};
	}
</script>

<UserPanelItemWrapper>
	<div>
		<h1>Filter & Search</h1>
		<form class="mt-3 flex w-full flex-col justify-center gap-3">
			<div class="grid w-full grid-cols-3 gap-3">
				<!-- order by -->
				<div class="grid w-full max-w-sm items-center gap-1.5">
					<Label for="sort">Sort By</Label>
					<select
						bind:value={params.sort.value}
						class="border-input placeholder:text-muted-foreground focus-visible:ring-ring flex h-9 w-full rounded-md border bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium focus-visible:outline-none focus-visible:ring-1 disabled:cursor-not-allowed disabled:opacity-50"
						name="sort"
					>
						{#each params.sort.options as option}
							<option value={option.value}>{option.label}</option>
						{/each}
					</select>
				</div>
				<div class="grid w-full max-w-sm items-center gap-1.5">
					<Label for="limit">Limit</Label>
					<select
						bind:value={params.limit.value}
						class="border-input placeholder:text-muted-foreground focus-visible:ring-ring flex h-9 w-full rounded-md border bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium focus-visible:outline-none focus-visible:ring-1 disabled:cursor-not-allowed disabled:opacity-50"
						name="limit"
					>
						{#each params.limit.options as option}
							<option value={option}>{option}</option>
						{/each}
					</select>
				</div>
			</div>
			<Input
				name="qu"
				type="text"
				bind:value={params.qu}
				placeholder="Search for user, with user's id, name, email, username"
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

	<Table.Root>
		<!-- <Table.Caption>A list of your recent invoices.</Table.Caption> -->
		<Table.Header>
			<Table.Row>
				<Table.Head class="w-[200px]">ID</Table.Head>
				<Table.Head class="">Username</Table.Head>
				<Table.Head class="w-[100px]">Created</Table.Head>
				<Table.Head class="w-[100px]">Updated</Table.Head>
				<Table.Head class="text-right">Actions</Table.Head>
			</Table.Row>
		</Table.Header>
		<Table.Body>
			{#if data?.items?.length > 0}
				{#each data?.items as item}
					<Table.Row>
						<Table.Cell class="font-medium">{item.id}</Table.Cell>
						<Table.Cell>{item.username}</Table.Cell>
						<Table.Cell>{beautiulDateTime(item.created)}</Table.Cell>
						<Table.Cell>{beautiulDateTime(item.updated)}</Table.Cell>
						<Table.Cell class="flex justify-end gap-2 ">
							<Button
								variant="outline"
								size="sm"
								on:click={() => {
									selectedUser = item;
									viewModalOpen = true;
								}}>Show Details</Button
							>
							<Button
								variant="default"
								on:click={() => {
									selectedUser = item;
									updateModalOpen = true;
								}}
								size="sm">Modify</Button
							>
							<Button
								variant="outline"
								class=" bg-indigo-400 text-white "
								size="sm"
								on:click={() => {
									selectedUser = item;
									deleteModalOpen = true;
								}}>Delete</Button
							>
							<Button
								class="flex items-center justify-center gap-1"
								variant="outline"
								size="sm"
								href={'/_/pages/?qu=' + item.id}
								>Pages
								<ExternalLink class="h-4 w-4 " />
							</Button>
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
</UserPanelItemWrapper>

<AlertDialog.Root bind:open={viewModalOpen}>
	<AlertDialog.Content>
		<AlertDialog.Header>
			<AlertDialog.Title>Details of {selectedUser.id}</AlertDialog.Title>
			<AlertDialog.Description class="font-md">
				<table class="w-full text-black">
					<tr>
						<td class="w-1/4">ID</td>
						<td>{selectedUser.id}</td>
					</tr>
					<tr>
						<td class="w-1/4">Username</td>
						<td>{selectedUser.username}</td>
					</tr>
					<tr>
						<td class="w-1/4">Email</td>
						<td>{selectedUser.email}</td>
					</tr>
					<tr>
						<td class="w-1/4">Name</td>
						<td>{selectedUser.name}</td>
					</tr>
					<tr>
						<td class="w-1/4">Created</td>
						<td>{beautiulDateTime(selectedUser.created)}</td>
					</tr>
					<tr>
						<td class="w-1/4">Updated</td>
						<td>{beautiulDateTime(selectedUser.updated)}</td>
					</tr>
				</table>
			</AlertDialog.Description>
		</AlertDialog.Header>
		<AlertDialog.Footer>
			<AlertDialog.Cancel>Close</AlertDialog.Cancel>
			<!-- <AlertDialog.Action> -->
			<!-- <form action="?/deletePage" method="post" use:enhance={enhancedUserDelete}>
				<input type="hidden" name="pageId" value={selectedForDeleteItem?.id} />
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
			</form> -->
			<!-- </AlertDialog.Action> -->
		</AlertDialog.Footer>
	</AlertDialog.Content>
</AlertDialog.Root>

<AlertDialog.Root bind:open={deleteModalOpen}>
	<AlertDialog.Content>
		<AlertDialog.Header>
			<AlertDialog.Title>Are you sure to delete user #{selectedUser.id}</AlertDialog.Title>
			<AlertDialog.Description class="font-md">
				<table class="w-full text-black">
					<tr>
						<td class="w-1/4">ID</td>
						<td>{selectedUser.id}</td>
					</tr>
					<tr>
						<td class="w-1/4">Username</td>
						<td>{selectedUser.username}</td>
					</tr>
					<tr>
						<td class="w-1/4">Email</td>
						<td>{selectedUser.email}</td>
					</tr>
					<tr>
						<td class="w-1/4">Name</td>
						<td>{selectedUser.name}</td>
					</tr>
					<tr>
						<td class="w-1/4">Created</td>
						<td>{beautiulDateTime(selectedUser.created)}</td>
					</tr>
					<tr>
						<td class="w-1/4">Updated</td>
						<td>{beautiulDateTime(selectedUser.updated)}</td>
					</tr>
				</table>
			</AlertDialog.Description>
		</AlertDialog.Header>
		<AlertDialog.Footer>
			<AlertDialog.Cancel>Close</AlertDialog.Cancel>
			<!-- <AlertDialog.Action> -->
			<form action="?/deleteUser" method="post" use:enhance={enhancedUserDelete}>
				<input type="hidden" name="userId" value={selectedUser.id} />
				<Button
					type="submit"
					on:click={() => {
						deletingUser = true;
					}}
				>
					{#if deletingUser}
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

<AlertDialog.Root bind:open={updateModalOpen}>
	<AlertDialog.Content>
		<AlertDialog.Header>
			<AlertDialog.Title>Modify user #{selectedUser.id}</AlertDialog.Title>
			<AlertDialog.Description class="font-md">
				<!-- user data form -->
				<form
					action="?/updateUser"
					class="flex flex-col gap-3 text-black"
					method="post"
					use:enhance={enhancedUserUpdate}
				>
					<input type="hidden" name="userId" value={selectedUser.id} />
					<div class="flex flex-col gap-2">
						<Label for="usernameX">Username</Label>
						<Input type="text" name="usernameX" placeholder={selectedUser.username} />
					</div>
					<div class="flex flex-col gap-2">
						<Label for="emailX">Email</Label>
						<Input type="email" name="emailX" placeholder={selectedUser.email} />
					</div>
					<div class="flex flex-col gap-2">
						<Label for="nameX">Name</Label>
						<Input type="text" name="nameX" placeholder={selectedUser.name} />
					</div>

					{#if showChangePasswordField}
						<div class="flex flex-col gap-2">
							<Label for="passwordX">New Password</Label>
							<Input
								disabled={!showChangePasswordField}
								type="password"
								name="passwordX"
								minlength={8}
								placeholder="**********"
							/>
						</div>
					{/if}
					<Button
						type="button"
						on:click={() => {
							showChangePasswordField = !showChangePasswordField;
						}}>{showChangePasswordField ? 'Close Password Field' : 'Change Password'}</Button
					>

					{#if updatedMessage}
						<p class="text-md rounded-lg border border-black p-2 text-black">{updatedMessage}</p>
					{/if}

					<Button
						type="submit"
						on:click={() => {
							updatedMessage = '';
							updatingUser = true;
						}}
					>
						{#if updatingUser}
							<CircleDotDashed
								class="mr-2 h-4 w-4 animate-spin stroke-white dark:stroke-stone-950"
							/>
							<span>Updating...</span>
						{:else}
							Update
						{/if}
					</Button>
				</form>
			</AlertDialog.Description>
		</AlertDialog.Header>
		<AlertDialog.Footer>
			<AlertDialog.Cancel>Close</AlertDialog.Cancel>
		</AlertDialog.Footer>
	</AlertDialog.Content>
</AlertDialog.Root>
