<script lang="ts">
	import { enhance } from '$app/forms';
	import { Input } from '@/components/ui/input';
	import { Label } from '@/components/ui/label';
	import { Button } from '@/components/ui/button';
	import type { ActionResult } from '@sveltejs/kit';
	import { AlertTriangle, CircleDotDashed } from 'lucide-svelte';
	import { toast } from 'svelte-sonner';
	import { ErrorMessages, SuccessMessages } from '@/utils/messages';
	import * as Alert from '@/components/ui/alert';
	import { validRegex } from '@/utils/validations';

	let isLoading = false;
	let btnDisabled = false;
	let oldPasswordOk = false;

	let simpleError = '';

	const fields = [
		{ name: 'oldPassword', placeholder: 'Old Password', type: 'password', value: '' },
		{ name: 'password', placeholder: 'New Password', type: 'password', value: '' },
		{ name: 'passwordConfirm', placeholder: 'Confirm New Password', type: 'password', value: '' }
	];

	function enhancedSubmission() {
		return async ({ result }: { result: ActionResult }) => {
			switch (result.type) {
				case 'failure': {
					simpleError = result?.data?.message ?? ErrorMessages.DEFAULT_ERROR;
					break;
				}
				case 'success': {
					simpleError = '';
					toast.success(SuccessMessages.PASSWORD_CHANGE_SUCCESS, {
						duration: 3000,
						position: 'top-right'
					});
					// document.location.reload();
					break;
				}
			}
			console.log(result);
			isLoading = false;
		};
	}
	$: {
		btnDisabled = true;
		for (const field of fields) {
			if (field.value === '') {
				btnDisabled = true;
				break;
			}
			btnDisabled = false;
		}

		if (
			validRegex.password.test(fields[1].value) &&
			validRegex.password.test(fields[2].value) &&
			validRegex.password.test(fields[0].value) &&
			fields[1].value === fields[2].value
		) {
			btnDisabled = false;
		}
	}
</script>

<!-- Ask for all the passwords -->

<div class="flex max-w-lg flex-col space-y-6 lg:p-8">
	<div class="flex flex-col gap-8">
		<div class="">
			<form method="post" action="?/changePassword" use:enhance={enhancedSubmission}>
				<div class="grid gap-2">
					{#each fields as field}
						<div class="grid gap-1">
							<Label class="sr-only" for="password">{field.name}</Label>
							<Input
								class="bg-gray-100 focus:bg-gray-300"
								name={field.name}
								id={field.name}
								bind:value={field.value}
								placeholder={field.placeholder}
								type="password"
								disabled={isLoading}
								required
							/>
						</div>
					{/each}

					{#if simpleError}
						<Alert.Root variant="destructive">
							<!-- <Alert.Title>Error</Alert.Title> -->
							<Alert.Description class="flex items-center justify-center gap-4 ">
								<AlertTriangle class="h-4 w-4" />
								{simpleError}
							</Alert.Description>
						</Alert.Root>
					{/if}
					<Button
						type="submit"
						variant="default"
						class="w-full"
						disabled={isLoading || btnDisabled}
						on:click={() => {
							setTimeout(() => {
								isLoading = true;
							});
						}}
					>
						{#if isLoading}
							<CircleDotDashed color="white" class="mr-2 h-4 w-4 animate-spin" />
							Requesting Password Change
						{:else}
							Change Password
						{/if}
					</Button>
				</div>
			</form>
		</div>
	</div>
</div>
