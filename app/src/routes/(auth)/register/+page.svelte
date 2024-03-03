<script lang="ts">
	import { applyAction, enhance } from '$app/forms';
	import { invalidateAll } from '$app/navigation';
	import { Button } from '$lib/components/ui/button';
	import { Input } from '@/components/ui/input';
	import { Label } from '@/components/ui/label';
	import Logo from '@/ui/Logo.svelte';
	import { AlertTriangle, CircleDotDashed } from 'lucide-svelte';
	import * as Alert from '$lib/components/ui/alert';
	import { toast } from 'svelte-sonner';
	import { AppLinks } from '@/utils/app-links';
	import dummyData from '@/dev/dummyData';
	import DummyDataSection from '@/dev/dummyDataSection.svelte';

	let isLoading = false;
	let finalErrorMessage = '';
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

	function populateRandomData(idx: number) {
		const randomData = dummyData[idx];

		fields[0].value = randomData.email;
		fields[1].value = randomData.username;
		fields[2].value = randomData.name;
		fields[3].value = randomData.password;
		fields[4].value = randomData.password;
	}

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
				case 'redirect': {
					toast.success(result?.data?.message ?? 'Account created. Login to proceed!');
					console.log('redirect');
					setTimeout(() => {
						document.location.href = AppLinks.LOGIN;
					}, 1000);
					break;
				}
			}

			await applyAction(result);
			invalidateAll();

			isLoading = false;
		};
	}

	function onSubmit() {
		// setTimeout(() => {
		// isLoading = true;
		// }, 100);
	}
</script>

<div class="flex h-[80%] flex-col items-center justify-center space-y-6 lg:p-6 lg:pt-0">
	<div class="flex flex-col gap-8 md:gap-16">
		<div class="flex flex-col space-y-2 text-center">
			<h1 class="text-2xl font-semibold tracking-tight">Create an account</h1>
			<p class="text-sm text-muted-foreground">Fill out the form below to create your account</p>
		</div>
		<DummyDataSection {populateRandomData} />

		<div class="">
			<form
				method="post"
				action=""
				on:submit={onSubmit}
				use:enhance={enhancedSubmission}
				autocomplete="off"
			>
				<div class="grid gap-2">
					<!-- <div class="grid gap-1">
							<Label class="sr-only" for="email">Email</Label>
							<Input
								name="email"
								id="email"
								placeholder="hello@example.com"
								type="email"
								disabled={isLoading}
								required
							/>
						</div> -->
					{#each fields as inputF (inputF.name)}
						<div class="grid gap-1">
							<Label class="sr-only" for={inputF.name}>{inputF.name}</Label>
							<Input
								name={inputF.name}
								id={inputF.name}
								bind:value={inputF.value}
								placeholder={inputF.placeholder}
								type={inputF.type}
								disabled={isLoading}
								autocomplete="off"
							/>
							{#if fieldErrors[inputF.name]}
								<p class="text-sm text-red-500">
									{fieldErrors[inputF.name]}
								</p>
							{/if}
							<!-- required -->
						</div>
					{/each}

					{#if finalErrorMessage}
						<Alert.Root variant="destructive">
							<!-- <Alert.Title>Error</Alert.Title> -->
							<Alert.Description class="flex items-center justify-center gap-4 ">
								<AlertTriangle class="h-4 w-4" />
								{finalErrorMessage}
							</Alert.Description>
						</Alert.Root>
					{/if}
					<Button type="submit" disabled={isLoading}>
						{#if isLoading}
							<CircleDotDashed
								class=" mr-2 h-4 w-4 animate-spin stroke-white dark:stroke-stone-950"
							/>
						{/if}
						Sign Up
					</Button>
				</div>
			</form>
		</div>
		<p class="px-8 text-center text-sm text-muted-foreground">
			By clicking continue, you agree to our{' '}
			<a href="/terms" class="underline underline-offset-4 hover:text-primary">
				Terms of Service
			</a>{' '}
			and{' '}
			<a href="/privacy" class="underline underline-offset-4 hover:text-primary">
				Privacy Policy
			</a>
			.
		</p>
	</div>
</div>
