<script lang="ts">
	import Button from '@/components/ui/button/button.svelte';
	import { Input } from '$lib/components/ui/input';
	import { Label } from '$lib/components/ui/label';
	import { applyAction, enhance } from '$app/forms';
	import { invalidateAll } from '$app/navigation';
	import type { ActionResult } from '@sveltejs/kit';
	import { CircleDashed, X } from 'lucide-svelte';

	import UserPanelItemWrapper from '@/ui/UserPanelItemWrapper.svelte';
	import type { SingleNavItem, SiteHeaderType } from '@/types/customizations';
	import type { User } from '@/types/users';
	import { toast } from 'svelte-sonner';
	import PreDebug from '@/dev/PreDebug.svelte';
	import type { SinglePage } from '@/types/pages-and-stuff';
	import { fly, slide } from 'svelte/transition';

	const loadingStuff = {
		changeTitle: false,
		changeLogo: false,
		removeLogo: false,
		updateNavLinks: false
	};

	const getLogoUrl = (...data: string[]) => {
		return `/api/site-header/logo/${data.join('/')}`;
	};

	export let data: { siteHeader: SiteHeaderType; user: User };
	const siteHeader = data?.siteHeader as SiteHeaderType;

	let logoUrl = siteHeader.logo
		? getLogoUrl(siteHeader?.collectionId, siteHeader.id, siteHeader.logo)
		: '';

	function enhancedLogoRemoval() {
		return async ({ result }: { result: ActionResult }) => {
			// Do Something
			await applyAction(result);
			invalidateAll();
		};
	}

	function enhancedFormSubmission() {
		return async ({ result }: { result: ActionResult }) => {
			// Do Something

			switch (result.type) {
				case 'success':
					toast.success(result?.data?.message, {
						duration: 3000,
						position: 'top-right',
						class: 'mt-8'
					});
					await applyAction(result);
					invalidateAll();
					if (result?.data?.logo) {
						logoUrl = getLogoUrl(siteHeader?.collectionId, siteHeader.id, result.data.logo);

						noImageSelected = true;

						// clear image field
						imageField.value = '';
					} else {
						logoUrl = '';
					}
					break;
				case 'failure':
					toast.error(result?.data?.message, {
						duration: 3000,
						position: 'top-right',
						class: 'mt-8'
					});
					await applyAction(result);
					break;
				default:
					break;
			}

			loadingStuff.changeLogo = false;
			loadingStuff.removeLogo = false;
			loadingStuff.updateNavLinks = false;
		};
	}

	let navLinks: SingleNavItem[] = siteHeader?.nav_json || [];

	console.log('siteHeader.nav_json', siteHeader.nav_json);

	$: nav_json_internal = JSON.stringify(navLinks);

	const searchObj = {
		q: '',
		results: [],
		status: 'idle',
		message: ''
	} as {
		q: string;
		results: SinglePage[];
		status: 'idle' | 'loading' | 'success' | 'error';
		message?: string;
	};

	async function searchPages() {
		searchObj.status = 'loading';
		const endpoint = `/api/pages/search/?t=page&q=${searchObj.q}`;
		const res = await fetch(endpoint);
		const data = await res.json();
		if (data?.success === true) {
			searchObj.status = 'success';
			searchObj.results = data?.results;
		} else if (data?.success === false) {
			searchObj.status = 'error';
			searchObj.message = data?.message;
		}
	}

	/**
	 * User Searches for links
	 */

	let noImageSelected = true;
	function handleImageChange(event: Event) {
		const target = event.target as HTMLInputElement;
		if (target.files?.length) {
			noImageSelected = false;
		} else {
			noImageSelected = true;
		}
	}

	let imageField: HTMLInputElement;
	$: console.log('noImageSelected', noImageSelected);
</script>

<UserPanelItemWrapper title="Site Title">
	<div class="sec flex flex-col gap-3 py-3">
		<div class="user-logo">
			<form
				action="?/changeTitle"
				class="flex items-center gap-2 text-black dark:text-black"
				method="post"
				use:enhance={enhancedFormSubmission}
			>
				<input type="hidden" name="siteHeaderId" value={siteHeader.id} />
				<Input name="site_title" type="text" bind:value={siteHeader.site_title} />
				<Button type="submit" class="bg-black text-white">Update Title</Button>
			</form>
		</div>
	</div>
</UserPanelItemWrapper>

<!-- <PreDebug data={siteHeader} /> -->

<UserPanelItemWrapper title="Site Logo">
	<div class="sec flex flex-col gap-3 py-3">
		<div class="user-logo">
			<form
				action="?/removeLogo"
				class="flex items-center gap-2 text-black dark:text-black"
				method="post"
				use:enhance={enhancedFormSubmission}
			>
				<input type="hidden" name="siteHeaderId" value={siteHeader.id} />
				{#key logoUrl}
					{#if logoUrl}
						<div class="flex flex-col gap-2 py-3">
							<!-- [[LOGO IMAGE]] -->
							<img src={logoUrl} alt="Site Logo" class="w-20 border bg-gray-200 p-2" />

							<Button
								type="submit"
								on:click={() => {
									loadingStuff.removeLogo = true;
								}}
								variant="outline"
							>
								{#if loadingStuff.removeLogo}
									<CircleDashed class=" mr-2 h-4 w-4 animate-spin" />
									Removing Logo
								{:else}
									Remove
								{/if}
							</Button>
						</div>
					{:else}
						<p class="py-2 text-black dark:text-black">No logo uploaded</p>
					{/if}
				{/key}
			</form>
		</div>

		<form
			action="?/changeLogo"
			class="grid w-full max-w-sm items-center gap-1.5"
			method="post"
			use:enhance={enhancedFormSubmission}
			enctype="multipart/form-data"
		>
			<input type="hidden" name="siteHeaderId" value={siteHeader.id} />

			<Label for="picture" class="py-2 text-black dark:text-black">Upload new logo</Label>

			<input
				bind:this={imageField}
				on:change={() => {
					noImageSelected = false;
				}}
				required
				name="logo"
				id="picture"
				type="file"
				accept="image/*"
				class="hover:file w-full rounded-sm border p-2 file:border-r file:border-transparent file:border-r-black file:bg-transparent"
			/>

			{#if !noImageSelected}
				<button
					type="submit"
					on:click={(e) => {
						loadingStuff.changeLogo = true;
					}}
					disabled={noImageSelected}
					class="flex w-32 items-center justify-center rounded-md bg-black py-1 text-white transition-colors duration-300 ease-in-out hover:bg-gray-800"
				>
					{#if loadingStuff.changeLogo}
						<CircleDashed class=" mr-2 h-4 w-4 animate-spin" />
						Uploading
					{:else}
						Upload
					{/if}
				</button>
			{/if}
		</form>
	</div>
</UserPanelItemWrapper>

<UserPanelItemWrapper title="Navbar Links">
	<div class="sec flex flex-col gap-3 py-3">
		<div class="w-full">
			<h2>Search for pages and add links:</h2>
			<form class="flex gap-3" on:submit|preventDefault>
				<Input
					bind:value={searchObj.q}
					type="text"
					placeholder="Search for pages with title or slug"
					minlength={1}
				/>

				<Button
					type="submit"
					on:click={searchPages}
					disabled={searchObj.q?.trim()?.length < 1}
					class="bg-black text-white">Search</Button
				>
				{#if searchObj.status === 'success'}
					<div in:fly>
						<Button
							type="button"
							on:click={() => {
								searchObj.q = '';
								searchObj.results = [];
								searchObj.status = 'idle';
							}}
							class="bg-red-500/80 text-white">Reset</Button
						>
					</div>
				{/if}
			</form>

			{#if searchObj.status === 'loading'}
				<!-- Loading Spinner-->
				<div class="flex w-full items-center justify-center p-4">
					<div class="h-10 w-10 animate-spin rounded-full border-b-2 border-t-2 border-black"></div>
				</div>
			{:else if searchObj.status === 'success'}
				{#if searchObj.results.length < 1}
					<p class="text-black dark:text-black">No results found</p>
				{:else}
					<table class="table table-fixed items-center gap-2">
						<tr>
							<th>Page Title</th>
							<th>Slug</th>
							<th class="w-200">Actions</th>
						</tr>
						{#each searchObj.results as result}
							<tr class="m-3 py-3">
								<td>
									<span class="text-sm text-black dark:text-black">{result.title}</span>
								</td>
								<td>
									<span class="text-sm text-black dark:text-black">/{result.slug}</span>
								</td>
								<td>
									<Button
										variant="outline"
										class=""
										href={`/${data?.user?.username}/${result.slug}`}
										target="_blank">Preview</Button
									>
								</td>
								<td>
									<Button
										on:click={() => {
											navLinks = [
												...navLinks,
												{ title: result.title, href: `/${data?.user?.username}/${result.slug}` }
											];
										}}
										class="bg-black text-white">Add</Button
									>
								</td>
							</tr>
						{/each}
					</table>
				{/if}
			{:else if searchObj.status === 'error'}
				<p class="text-red-500">Error fetching pages</p>
			{:else}{/if}
		</div>
		{#if navLinks?.length > 0}
			{#each navLinks as link, idx}
				<span class="text-sm text-black dark:text-black">
					Link #{idx + 1}
				</span>
				<div class="flex flex-col items-center gap-2 md:flex-row">
					<Input type="text" maxlength={20} bind:value={link.title} />
					<Input type="text" bind:value={link.href} />
					<button
						on:click={() => {
							navLinks = navLinks.filter((_, i) => i !== idx);
						}}
						class="text-bl h-6 w-6 rounded-md bg-stone-500 text-white"
					>
						<X class="" />
					</button>
				</div>
			{/each}
		{/if}
	</div>
	<div class=" flex gap-4">
		<Button
			variant="outline"
			class=" "
			on:click={() => {
				console.log('Add Link');
				navLinks = [...navLinks, { title: 'Link Title', href: '/' }];
			}}>{navLinks?.length > 0 ? 'Add another link' : 'Add link'}</Button
		>
		<!-- We'll send the ready json-->
		<form action="?/changeNavLinks" method="post" use:enhance={enhancedFormSubmission}>
			<input type="hidden" name="siteHeaderId" value={siteHeader.id} />

			<input type="hidden" name="nav_json" bind:value={nav_json_internal} />
			<Button
				type="submit"
				on:click={() => {
					loadingStuff.updateNavLinks = true;
				}}
				class="bg-black text-white"
			>
				{#if loadingStuff.updateNavLinks}
					<CircleDashed class=" mr-2 h-4 w-4 animate-spin" />
					Updating Links
				{:else}
					Update Links
				{/if}
			</Button>
		</form>
	</div>
</UserPanelItemWrapper>
