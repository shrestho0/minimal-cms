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
	} & EditPageLoadData;
	import { Button } from '@/components/ui/button';
	import Alert from '@/components/ui/alert/alert.svelte';
	import { slide } from 'svelte/transition';
	import { CircleDashed, Minus } from 'lucide-svelte';
	import { copyToClipboard } from '@/utils/common';
	import { applyAction } from '$app/forms';
	import type { ActionResult } from '@sveltejs/kit';
	import { invalidateAll } from '$app/navigation';
	import { toast } from 'svelte-sonner';
	import { onMount } from 'svelte';
	import type { EditPageLoadData, NewOrEditPageData } from '@/types/load-data';
	import { InternalApiEndpoints } from '@/utils/app-links';

	let pageData = {
		pageId: '',
		title: {
			value: '',
			error: ''
		},

		content: {
			value: '',
			error: ''
		}
	} as {
		pageId: string;
		title: {
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

	import macros from '@/utils/macros';

	let loadingButtonType: 'draft' | 'published' | '' = '';

	/**
	 * On save to draft or publish, validate till complete and edit/pageId on success
	 */

	function getSanitizeData() {
		return {
			id: pageData.pageId,
			title: pageData.title.value,
			content: pageData.content.value
		};
	}

	async function handleSubmissionClick(status: 'published') {
		loadingButtonType = status;
		const data = getSanitizeData();
		const res = await fetch(InternalApiEndpoints.EDIT_PROFILE, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				...data
			})
		});
		const resJson: {
			success: boolean;
			redirect_to: string;
			message: string;
			errors?: {
				title: string;
				slug: string;
				content: string;
			};
		} = await res.json();

		invalidateAll();

		if (resJson?.success) {
			toast.success(resJson?.message, {
				position: 'top-right',
				class: 'my-8'
			});
			// remove errors
			pageData.title.error = '';
			pageData.content.error = '';
			// Redirect to the page
			// window.location.href = resJson?.redirect_to;
		} else {
			// Show the error
			pageData.title.error = resJson?.errors?.title as string;
			pageData.content.error = resJson?.errors?.content as string;
		}

		loadingButtonType = '';
	}

	onMount(() => {
		pageData.pageId = data.page.id;
		pageData.title.value = data.page.title;
		pageData.content.value = data.page.content;
	});
	$: buttonsDisabled = !(
		pageData.title.value != data.page.title || pageData.content.value != data.page.content
	);
</script>

<svelte:head>
	<title>Profile | mCMS</title>
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
				title="Profile Page"
				pages={userPanelPages}
				{customizationPages}
			>
				<div class="flex w-auto flex-1 justify-end">
					<Button
						disabled={buttonsDisabled}
						on:click={async () => await handleSubmissionClick('published')}
					>
						{#if loadingButtonType == 'published'}
							<CircleDashed class="mx-2 h-5 w-5 animate-spin" />
						{/if}

						Update</Button
					>
				</div>
			</PageHeaderBlock>

			<!-- <UserPanelItemWrapper> -->
			<!-- Page Title Input-->
			<PageContentBlock>
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
								disabled={loadingButtonType != ''}
								placeholder="Enter the title of the page"
							/>

							{#if pageData.title.error}
								<div transition:slide class="error p-2 text-sm text-red-500/90">
									{pageData.title.error}
								</div>
							{/if}
						</div>
					</form>

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
