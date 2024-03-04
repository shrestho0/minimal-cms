<script lang="ts">
	import Logo from '@/ui/Logo.svelte';
	import { Input } from '@/components/ui/input';
	import { Textarea } from '@/components/ui/textarea';
	import PageHeaderBlock from '@/ui/PageHeaderBlock.svelte';
	import PageContentBlock from '@/ui/PageContentBlock.svelte';
	import SidePanel from '@/ui/SidePanel.svelte';
	import { customizationPages, userPanelPages } from '@/utils/authenticated-links';

	import { Button } from '@/components/ui/button';
	import Alert from '@/components/ui/alert/alert.svelte';
	import { slide } from 'svelte/transition';
	import { CircleDashed, ExternalLink, Minus } from 'lucide-svelte';
	import { copyToClipboard, toTitleCase } from '@/utils/common';
	import { toast } from 'svelte-sonner';
	import { onMount } from 'svelte';
	import type { NewOrEditPageData, ResponseNewOrUpdatePage } from '@/types/load-data';
	import { InternalApiEndpoints } from '@/utils/app-links';
	import type { SinglePage } from '@/types/entity';
	import { invalidateAll } from '$app/navigation';
	import { navigating } from '$app/stores';
	import macros from '@/utils/macros';

	export let data: {
		user: any;
		page: SinglePage;
		success: boolean;
		message?: string;
	};
	let pageData: NewOrEditPageData = {
		pageId: '',
		updating: false,
		status: 'draft',

		title: {
			value: '',
			error: ''
		},
		slug: {
			value: '',
			error: ''
		},
		content: {
			value: '',
			error: ''
		}
	};

	// $: {
	// 	// Check for errors and set the error message

	// }

	let availableMacros = [
		{ name: 'Name', value: data?.user?.name, macro: '{{name}}' },
		{ name: 'Username', value: data?.user?.username, macro: '{{username}}' },
		{ name: 'Email', value: data?.user?.email, macro: '{{email}}' }
	];

	// $: buttonsDisabled = !pageData.title.value || !pageData.slug.value || !pageData.content.value;
	$: buttonsDisabled = !(
		pageData.title.value != data.page.title ||
		pageData.slug.value != data.page.slug ||
		pageData.content.value != data.page.content
	);

	let loadingButtonType: 'draft' | 'published' | '' = '';

	/**
	 * On save to draft or publish, validate till complete and edit/pageId on success
	 */

	function getSanitizeData() {
		return {
			id: pageData.pageId,
			title: pageData.title.value.trim(),
			slug: pageData.slug.value.trim(),
			content: pageData.content.value.trim()
		};
	}

	async function handleSubmissionClick(status: 'draft' | 'published') {
		loadingButtonType = status;

		const dataX = getSanitizeData();
		const res = await fetch(InternalApiEndpoints.EDIT_PAGE + `?pageId=${pageData.pageId}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				...dataX,
				status
			})
		});
		const resJson: ResponseNewOrUpdatePage = await res.json();

		console.log('resJson', resJson);
		invalidateAll();

		if (resJson?.success) {
			toast.success(resJson?.message, {
				position: 'top-center',
				class: 'my-8'
			});
			pageData.title.error = '';
			pageData.slug.error = '';
			pageData.content.error = '';
			// Toast should be enough
		} else {
			// Show the error
			resJson?.message &&
				toast.error(resJson?.message, {
					position: 'top-center',
					class: 'mt-8'
				});
			pageData.title.error = resJson?.errors?.title as string;
			pageData.slug.error = resJson?.errors?.slug as string;
			pageData.content.error = resJson?.errors?.content as string;
		}

		loadingButtonType = '';
	}

	onMount(() => {
		if (data?.success && data?.page) {
			pageData.pageId = data.page.id;
			pageData.title.value = data.page.title;
			pageData.slug.value = data.page.slug;
			pageData.content.value = data.page.content;
			pageData.status = data.page.status;
		}
	});
</script>

<svelte:head>
	<title>{data?.page?.id ? `Edit: #${data?.page?.id}` : 'Invalid Page'} | mCMS</title>
</svelte:head>
<div class="h-screen w-full">
	<div class="flex h-screen w-full overflow-y-hidden">
		<aside class="hidden h-full w-[30%] max-w-[300px] border-r border-gray-200 bg-white md:block">
			<div class="flex h-16 items-center justify-center gap-3 border-b border-gray-200">
				<Logo className="text-black" />
			</div>

			<SidePanel pages={userPanelPages} {customizationPages} />
		</aside>
		<aside class="  w-full bg-gray-50">
			<PageHeaderBlock
				user={data?.user}
				title={data?.page?.id ? `Status: <strong>${toTitleCase(data?.page?.status)}</strong>` : ''}
				pageStatus={data?.page?.status}
				pages={userPanelPages}
				{customizationPages}
			>
				<div class="flex w-auto flex-1 justify-end gap-2">
					{#if pageData.status == 'banned'}
						<!-- Simply No Actions -->
					{:else}
						<Button
							variant="ghost"
							target="_blank"
							href={'/' + data?.user?.username + '/' + data?.page?.slug}
						>
							<ExternalLink />
						</Button>

						<Button
							variant="outline"
							disabled={buttonsDisabled && data?.page?.status == 'draft'}
							on:click={async () => await handleSubmissionClick('draft')}
						>
							{#if loadingButtonType == 'draft'}
								<CircleDashed class="mx-2 h-5 w-5 animate-spin" />
							{/if}
							{data?.page?.status == 'draft' ? 'Update Draft' : 'Draft'}</Button
						>
						<Button
							disabled={buttonsDisabled && data?.page?.status == 'published'}
							on:click={async () => await handleSubmissionClick('published')}
						>
							{#if loadingButtonType == 'published'}
								<CircleDashed class="mx-2 h-5 w-5 animate-spin" />
							{/if}

							{data?.page?.status == 'published' ? 'Update' : 'Publish'}</Button
						>
					{/if}
				</div>
			</PageHeaderBlock>

			<!-- <UserPanelItemWrapper> -->
			<!-- Page Title Input-->
			<PageContentBlock>
				{#if data.success}
					<div class=" m-2 rounded-lg bg-white px-8 py-4 shadow-md md:m-4">
						<form method="post" on:submit|preventDefault class="mt-3">
							<label for="title" class="text-md block font-normal text-gray-700"> Title </label>
							<div class="mt-1">
								<Input
									type="text"
									bind:value={pageData.title.value}
									name="title"
									id="title"
									required={true}
									disabled={pageData.status === 'banned' || Boolean(loadingButtonType)}
									placeholder="Enter the title of the page"
								/>

								{#if pageData.title.error}
									<div transition:slide class="error p-2 text-sm text-red-500/90">
										{pageData.title.error}
									</div>
								{/if}
							</div>
						</form>
						<!-- Page Slug Input-->
						<div class="mt-4">
							<label for="slug" class="text-md block font-normal text-gray-700"> Slug </label>
							<div class="mt-1">
								<Input
									required={true}
									type="text"
									name="slug"
									id="slug"
									disabled={pageData.status === 'banned' || Boolean(loadingButtonType)}
									bind:value={pageData.slug.value}
									placeholder="Enter the slug of the page"
								/>
								{#if pageData.slug.error}
									<div transition:slide class="error p-2 text-sm text-red-500/90">
										{pageData.slug.error}
									</div>
								{/if}
							</div>
						</div>
						<!-- Page Content Input-->
						<div class="mt-4">
							<label for="content" class="text-md block font-normal text-gray-700"> Content </label>
							<div class="mt-1">
								<Textarea
									name="content"
									id="content"
									rows={15}
									required={true}
									disabled={pageData.status === 'banned' || Boolean(loadingButtonType)}
									bind:value={pageData.content.value}
									placeholder="Enter the content of the page"
								></Textarea>
								{#if pageData.content.error}
									<div transition:slide class="error p-2 text-sm text-red-500/90">
										{pageData.content.error}
									</div>
								{/if}
							</div>
						</div>
						<!-- Available macros -->
						<!-- Available macros -->
						<div class="mt-4">
							<label for="macros" class="text-md block font-normal text-gray-700">
								Available Macros:
							</label>
							<div class="mt-1 text-sm text-gray-700">
								<div class="">
									{#each macros as macroObj}
										<div class="m-1 flex gap-2">
											<Button
												title="Copy to clipboard"
												on:click={() => {
													copyToClipboard(macroObj.macro);
												}}
												variant="outline"
												size="sm"
											>
												{macroObj.macro}
												<Minus />
												{macroObj.name} ({data?.user[macroObj.userKey]})
											</Button>
										</div>
									{/each}
								</div>
							</div>
						</div>
						<!-- </UserPanelItemWrapper> -->
					</div>
				{:else}
					<Alert>
						<p>{data?.message ? data.message : 'Page not found'}</p>
					</Alert>
				{/if}
			</PageContentBlock>
		</aside>
	</div>
</div>
