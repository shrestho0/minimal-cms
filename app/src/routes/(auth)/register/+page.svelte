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
		{ name: 'email', placeholder: 'Email', type: 'email' },
		{ name: 'username', placeholder: 'Username', type: 'username' },
		{ name: 'name', placeholder: 'John Doe', type: 'text' },
		{ name: 'password', placeholder: 'Password', type: 'password' },
		{
			name: 'passwordConfirm',
			placeholder: 'Confirm Password',
			type: 'password',
			error: fieldErrors.passwordConfirm
		}
	];

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
						document.location.href = AppLinks.USER_LOGIN;
					}, 1000);
					break;
				}
			}

			isLoading = false;

			await applyAction(result);
			invalidateAll();
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
			<p class="text-muted-foreground text-sm">Fill out the form below to create your account</p>
		</div>

		<!-- <PreDebug data={{ fieldErrors }} /> -->
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
							<CircleDotDashed color="white" class="mr-2 h-4 w-4 animate-spin" />
						{/if}
						Sign Up
					</Button>
				</div>
			</form>
		</div>
		<p class="text-muted-foreground px-8 text-center text-sm">
			By clicking continue, you agree to our{' '}
			<a href="/terms" class="hover:text-primary underline underline-offset-4">
				Terms of Service
			</a>{' '}
			and{' '}
			<a href="/privacy" class="hover:text-primary underline underline-offset-4">
				Privacy Policy
			</a>
			.
		</p>
	</div>
</div>
