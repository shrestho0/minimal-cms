<script lang="ts">
	import { applyAction, enhance } from '$app/forms';
	import { invalidateAll } from '$app/navigation';
	import { Button } from '@/components/ui/button';
	import { Input } from '@/components/ui/input';
	import { Label } from '@/components/ui/label';
	import { Select } from '@/components/ui/select';
	import type { SiteStyle } from '@/types/customizations';
	import type { User } from '@/types/entity';
	import UserPanelItemWrapper from '@/ui/UserPanelItemWrapper.svelte';
	import fontData from '@/utils/font-data';
	import type { ActionResult } from '@sveltejs/kit';
	import { CircleDashed } from 'lucide-svelte';
	import { onMount } from 'svelte';
	import { toast } from 'svelte-sonner';

	function enhancedFormSubmission() {
		return async ({ result }: { result: ActionResult }) => {
			switch (result.type) {
				case 'success':
					toast.success(result?.data?.message, {
						duration: 3000,
						position: 'top-right',
						class: 'mt-8'
					});
					break;
				case 'failure':
					toast.error(result?.data?.message, {
						duration: 3000,
						position: 'top-right',
						class: 'mt-8'
					});
					break;
			}

			await applyAction(result);
			invalidateAll();
			loadingStuff.fonts = false;
			loadingStuff.cstyles = false;
			changeFont = false;
		};
	}
	let allowedTags = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'p', 'a'];

	export let data: {
		siteStyle: SiteStyle;
		user: User;
	};
	let siteStyles: SiteStyle = data.siteStyle;

	$: fontLoadUrl = fontData.find((font) => font.fontFamily === siteStyles.fontFamily)?.fontLoadUrl;

	$: styleJson = JSON.stringify(siteStyles.styleJson);

	onMount(() => {
		// populate tag styles if not present
		allowedTags.forEach((tag) => {
			if (!siteStyles.styleJson[tag]) {
				siteStyles.styleJson[tag] = {
					color: 'black',
					'font-size': ''
				};
			}
		});
	});

	let loadingStuff = {
		fonts: false,
		cstyles: false
	};
	let changeFont = false;
</script>

<UserPanelItemWrapper title="Font">
	<div class="sec flex flex-col gap-3 py-3">
		<form
			action="?/updateStyle"
			class="grid w-full max-w-sm items-center gap-1.5"
			method="post"
			use:enhance={enhancedFormSubmission}
		>
			{#if changeFont}
				<select
					name="fontFamily"
					class="rounded bg-gray-200 p-2"
					bind:value={siteStyles.fontFamily}
				>
					{#each fontData as { fontFamily, fontLoadUrl }}
						<option value={fontFamily}>{fontFamily}</option>
					{/each}
				</select>
				<input type="hidden" name="styleId" value={siteStyles.id} />
				<input type="hidden" name="fontLoadUrl" bind:value={fontLoadUrl} />

				<Button
					on:click={() => {
						loadingStuff.fonts = true;
					}}
					type="submit"
					class="bg-black text-white"
				>
					{#if loadingStuff.fonts}
						<CircleDashed class="h-6 w-6 animate-spin" />
						Saving Font
					{:else}
						Save Font
					{/if}
				</Button>
			{:else}
				<select disabled={true} class="rounded bg-gray-200 p-2" value={siteStyles.fontFamily}>
					<option value={siteStyles.fontFamily}>{siteStyles.fontFamily}</option>
				</select>
				<Button
					on:click={() => {
						changeFont = true;
					}}
					class="bg-black text-white"
				>
					Change Font
				</Button>
			{/if}
		</form>
	</div>
</UserPanelItemWrapper>

<UserPanelItemWrapper title="Colors and Font Sizes">
	<div class="sec flex flex-col gap-3 py-3">
		<form
			action="?/updateStyle"
			class="grid w-full max-w-sm items-center gap-1.5"
			method="post"
			use:enhance={enhancedFormSubmission}
		>
			{#each Object.keys(siteStyles.styleJson) as tag}
				<div class="flex items-center justify-center gap-1">
					<Label for={tag} class="w-10 font-bold text-black dark:text-black">{tag}</Label>
					<Input id={tag} type="color" bind:value={siteStyles.styleJson[tag]['color']} />
					<Input id={tag} type="text" bind:value={siteStyles.styleJson[tag]['font-size']} />
				</div>
			{/each}
			<input type="hidden" name="styleId" value={siteStyles.id} />
			<input type="hidden" name="styleJson" bind:value={styleJson} />

			<Button
				on:click={() => {
					loadingStuff.cstyles = true;
				}}
				type="submit"
				class="bg-black text-white"
			>
				{#if loadingStuff.cstyles}
					<CircleDashed class="h-6 w-6 animate-spin" />
					Saving Styles
				{:else}
					Save Styles
				{/if}
			</Button>
		</form>
	</div>
</UserPanelItemWrapper>
