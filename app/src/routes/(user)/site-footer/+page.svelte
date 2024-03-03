<script lang="ts">
	import { applyAction, enhance } from '$app/forms';
	import { invalidateAll } from '$app/navigation';
	import { Button } from '@/components/ui/button';
	import Input from '@/components/ui/input/input.svelte';
	import type { SingleSocialItem, SiteFooterType } from '@/types/customizations';
	import type { User } from '@/types/users';

	import UserPanelItemWrapper from '@/ui/UserPanelItemWrapper.svelte';
	import type { ActionResult } from '@sveltejs/kit';
	import { CircleDashed, X } from 'lucide-svelte';
	import { toast } from 'svelte-sonner';

	const loadingStuff = {
		footerText: false,
		socialLinks: false
	};
	export let data: { siteFooter: SiteFooterType; user: User };
	const siteFooter = data?.siteFooter as SiteFooterType;

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
					// Do something
					break;
				case 'failure':
					toast.error(result?.data?.message, {
						duration: 3000,
						position: 'top-right',
						class: 'mt-8'
					});
					// Do something
					break;
				default:
					// Do something
					break;
			}

			loadingStuff.footerText = false;
			loadingStuff.socialLinks = false;

			invalidateAll();
			await applyAction(result);
		};
	}

	let socialLinks: SingleSocialItem[] = siteFooter?.social_json || [];

	$: social_json = JSON.stringify(socialLinks);
</script>

<UserPanelItemWrapper title="Footer Text">
	<div class="sec flex flex-col gap-3 py-3">
		<form
			action="?/changeFooterText"
			class="grid w-full max-w-sm items-center gap-1.5"
			method="post"
			use:enhance={enhancedFormSubmission}
		>
			<input type="hidden" name="siteFooterId" value={siteFooter?.id} />
			<Input name="text" id="text" type="text" value={siteFooter?.text} />

			<Button
				type="submit"
				on:click={() => {
					loadingStuff.footerText = true;
				}}
				class="bg-black text-white"
			>
				{#if loadingStuff.footerText}
					<CircleDashed class="mr-2 h-5 w-5 animate-spin" /> Updating...
				{:else}
					Update Footer Text
				{/if}
			</Button>
		</form>
	</div>
</UserPanelItemWrapper>

<UserPanelItemWrapper title="Social Links">
	<div class="sec flex flex-col gap-3 py-3">
		{#each socialLinks as link, idx}
			<span class="text-sm text-black dark:text-black">
				Link #{idx + 1}
			</span>
			<div class="flex flex-col items-center gap-2 md:flex-row">
				<Input type="text" maxlength={20} bind:value={link.title} />
				<Input type="text" bind:value={link.href} />
				<Input type="text" bind:value={link.fa_icon} />

				<button
					on:click={() => {
						socialLinks = socialLinks.filter((_, i) => i !== idx);
					}}
					class="text-bl h-6 w-6 rounded-md bg-stone-500 text-white"
				>
					<X class="" />
				</button>
			</div>
		{/each}
	</div>

	<div class=" flex gap-4">
		<!-- We'll send the ready json-->

		<Button
			variant="outline"
			class=" "
			on:click={() => {
				console.log('Add Link');
				socialLinks = [...socialLinks, { title: 'Link Title', href: '/', fa_icon: 'fa fa-[X]' }];
			}}
			>{socialLinks?.length > 0 ? 'Add another link' : 'Add link'}
		</Button>
		{#if socialLinks?.length > 0}
			<form action="?/changeSocialLinks" use:enhance={enhancedFormSubmission} method="post">
				<input type="hidden" name="siteFooterId" value={siteFooter?.id} />
				<input type="hidden" name="social_json" bind:value={social_json} />
				<Button
					type="submit"
					on:click={() => {
						loadingStuff.socialLinks = true;
					}}
					class="bg-black text-white"
				>
					{#if loadingStuff.socialLinks}
						<CircleDashed class="mr-2 h-5 w-5 animate-spin" /> Updating...
					{:else}
						Update Social Links
					{/if}
				</Button>
			</form>
		{/if}
	</div>
</UserPanelItemWrapper>
