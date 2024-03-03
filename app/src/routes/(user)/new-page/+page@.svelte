<script lang="ts">
	import LightSwitch from '@/ui/LightSwitch.svelte';
	import Logo from '@/ui/Logo.svelte';
	import { Input } from '@/components/ui/input';
	import { Textarea } from '@/components/ui/textarea';
	import { ModeWatcher } from 'mode-watcher';
	import PageHeaderBlock from '@/ui/PageHeaderBlock.svelte';
	import PageContentBlock from '@/ui/PageContentBlock.svelte';
	import { page } from '$app/stores';
	import Separator from '@/components/ui/separator/separator.svelte';
	import SidePanel from '@/ui/SidePanel.svelte';
	import { customizationPages, userPanelPages } from '@/utils/authenticated-links';
	import UserPanelItemWrapper from '@/ui/UserPanelItemWrapper.svelte';
	export let data: {
		user: any;
	};
	import { Button } from '@/components/ui/button';
	import Alert from '@/components/ui/alert/alert.svelte';
	import { slide } from 'svelte/transition';
	import { CircleDashed, Minus } from 'lucide-svelte';
	import { copyToClipboard } from '@/utils/common';
	import { applyAction } from '$app/forms';
	import type { ActionResult } from '@sveltejs/kit';
	import { invalidateAll } from '$app/navigation';
	import { toast } from 'svelte-sonner';
	import { AppLinks, InternalApiEndpoints } from '@/utils/app-links';
	import type { ResponseNewOrUpdatePage } from '@/types/load-data';
	import macros from '@/utils/macros';

	let pageData = {
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
	} as {
		title: {
			value: string;
			error: string;
		};
		slug: {
			value: string;
			error: string;
		};
		content: {
			value: string;
			error: string;
		};
	};

	// $: {
	// 	// Check for errors and set the error message

	// }

	$: buttonsDisabled =
		!pageData.title.value || !pageData.slug.value || !pageData.content.value?.length;

	let loadingButtonType: 'draft' | 'published' | '' = '';

	/**
	 * On save to draft or publish, validate till complete and edit/pageId on success
	 */

	function getSanitizeData() {
		let content_sanitized = pageData.content.value.trim();

		// we'll change the macros on page load on view
		// availableMacros.forEach((macroObj) => {
		// 	content_sanitized = content_sanitized.replaceAll(macroObj.macro, macroObj.value);
		// });

		return {
			title: pageData.title.value.trim(),
			slug: pageData.slug.value.trim(),
			content: content_sanitized
		};
	}

	async function handleSubmissionClick(status: 'draft' | 'published') {
		loadingButtonType = status;
		const data = getSanitizeData();
		const res = await fetch(InternalApiEndpoints.NEW_PAGE, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				...data,
				status
			})
		});
		const resJson: ResponseNewOrUpdatePage = await res.json();

		if (resJson?.success) {
			toast.success(resJson?.message, {
				position: 'top-center',
				class: 'my-16'
			});
			// Redirect to the page
			window.location.href = resJson?.redirect_to ?? AppLinks.USER_DASHBOARD;
		} else {
			// Show the error
			if (resJson?.message)
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

	function populateDummyData() {
		pageData.title.value = Math.random().toString(36).substring(2, 15);
		pageData.slug.value = Math.random().toString(36).substring(2, 15);
		pageData.content.value =
			Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
	}
</script>

<svelte:head>
	<title>Add New Page | mCMS</title>
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
				title="New Page"
				pages={userPanelPages}
				{customizationPages}
			>
				<div class="flex w-auto flex-1 justify-end gap-2">
					<Button
						variant="outline"
						on:click={async () => await handleSubmissionClick('draft')}
						disabled={buttonsDisabled}
					>
						{#if loadingButtonType == 'draft'}
							<CircleDashed class="mx-2 h-5 w-5 animate-spin" />
						{/if}
						Save to Draft</Button
					>
					<Button
						disabled={buttonsDisabled}
						on:click={async () => await handleSubmissionClick('published')}
					>
						{#if loadingButtonType == 'published'}
							<CircleDashed class="mx-2 h-5 w-5 animate-spin" />
						{/if}

						Publish</Button
					>
				</div>
			</PageHeaderBlock>

			<!-- <UserPanelItemWrapper> -->
			<!-- Page Title Input-->
			<PageContentBlock>
				<!--Dev -->
				<Button on:click={populateDummyData}>Populate Dummy Data</Button>
				<!--Dev -->

				<div class=" m-2 rounded-lg bg-white px-8 py-4 shadow-md md:m-4">
					<form method="post" on:submit|preventDefault class="mt-3">
						<label for="title" class="text-md block font-normal text-gray-700"> Title </label>
						<div class="mt-1">
							<Input
								type="text"
								bind:value={pageData.title.value}
								name="title"
								id="title"
								disabled={loadingButtonType != ''}
								required={true}
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
								disabled={loadingButtonType != ''}
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
								disabled={loadingButtonType != ''}
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
			</PageContentBlock>
		</aside>
	</div>
</div>
