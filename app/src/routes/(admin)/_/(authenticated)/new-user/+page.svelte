<script lang="ts">
	import { applyAction, enhance } from '$app/forms';
	import Button from '@/components/ui/button/button.svelte';
	import Input from '@/components/ui/input/input.svelte';
	import Label from '@/components/ui/label/label.svelte';
	import PreDebug from '@/dev/PreDebug.svelte';
	import UserPanelItemWrapper from '@/ui/UserPanelItemWrapper.svelte';
	import * as Alert from '$lib/components/ui/alert';
	import { CircleDotDashed } from 'lucide-svelte';
	import { invalidateAll } from '$app/navigation';
	import { toast } from 'svelte-sonner';
	import { AppLinks } from '@/utils/app-links';

	let fieldErrors = {
		email: '',
		username: '',
		name: '',
		password: '',
		passwordConfirm: '',
		reset: function () {
			console.log('resetting fieldErrors!');
			if (this.keys && typeof this.keys === 'function') {
				this.keys().forEach((key: any) => {
					this[key] = '';
				});
			}
		}
	} as {
		[key: string]: string | Function;
	};

	const fields = [
		{ name: 'email', placeholder: 'Email', type: 'email', value: '' },
		{ name: 'username', placeholder: 'Username', type: 'username', value: '' },
		{ name: 'name', placeholder: 'John Doe', type: 'text', value: '' },
		{ name: 'password', placeholder: 'Password', type: 'password', value: '' },
		{
			name: 'passwordConfirm',
			placeholder: 'Confirm Password',
			type: 'password',
			error: fieldErrors.passwordConfirm,
			value: ''
		}
	];

	let isLoading = false;
	let finalErrorMessage = '';
	function enhancedSubmission() {
		isLoading = true;
		return async ({ result }: any) => {
			console.log(result);
			if (fieldErrors.reset && typeof fieldErrors.reset === 'function') fieldErrors.reset();

			switch (result.type) {
				case 'failure': {
					console.log('failure');
					finalErrorMessage = result?.data?.message ?? '';

					if (result?.data?.fieldErrors) {
						fieldErrors = { ...fieldErrors, ...result?.data?.fieldErrors };
					}

					break;
				}
				case 'success': {
					toast.success(result?.data?.message ?? 'New User Created', {
						duration: 3000,
						position: 'top-center',
						class: 'mt-4'
					});
					typeof fieldErrors.reset == 'function' && fieldErrors.reset();
					break;
				}
			}

			await applyAction(result);
			invalidateAll();
			isLoading = false;
		};
	}

	function resetForm() {
		typeof fieldErrors.reset == 'function' && fieldErrors.reset();
		fields.forEach((field) => {
			field.value = '';
		});
	}

	function populateDummyData() {
		resetForm();
		// email
		fields[1].value = Math.random().toString(36).substring(2, 15);
		// username
		fields[0].value = fields[1].value + '@example.com';
		// name
		fields[2].value = fields[1].value;
		// password
		fields[3].value = fields[1].value;
		// passwordConfirm
		fields[4].value = fields[1].value;
	}
</script>

<UserPanelItemWrapper title="New User">
	<div>
		Populate Dummy Data
		<Button on:click={populateDummyData}>Populate</Button>
	</div>
	<form
		method="post"
		action=""
		class=" flex max-w-lg flex-col gap-4"
		use:enhance={enhancedSubmission}
	>
		{#each fields as field}
			<Label for={field.name}>{field.placeholder}</Label>
			<Input
				type={field.type}
				id={field.name}
				name={field.name}
				bind:value={field.value}
				class="w-full"
			/>
			{#if fieldErrors[field.name]}
				<p class="text-sm text-red-500">
					{fieldErrors[field.name]}
				</p>
			{/if}
		{/each}

		{#if finalErrorMessage}
			<p>{finalErrorMessage}</p>
		{/if}

		<Button type="submit" disabled={isLoading}>
			{#if isLoading}
				<CircleDotDashed class=" mr-2 h-4 w-4 animate-spin stroke-white dark:stroke-stone-950" />
			{/if}
			Create new user
		</Button>
	</form>
</UserPanelItemWrapper>
