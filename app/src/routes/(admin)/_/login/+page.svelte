<script lang="ts">
	import { applyAction, enhance } from '$app/forms';
	import { goto, invalidateAll } from '$app/navigation';
	import { Button } from '$lib/components/ui/button';
	import { Input } from '@/components/ui/input';
	import { Label } from '@/components/ui/label';
	import Logo from '@/ui/Logo.svelte';
	import { AlertTriangle, CircleDotDashed } from 'lucide-svelte';
	import * as Alert from '$lib/components/ui/alert';
	import { toast } from 'svelte-sonner';
	import { AppLinks } from '@/utils/app-links';
	import Hero from '@/ui/Hero.svelte';
	import HeroWrapper from '@/ui/HeroWrapper.svelte';
	import LightSwitch from '@/ui/LightSwitch.svelte';
	import { ModeWatcher } from 'mode-watcher';
	import type { ActionResult } from '@sveltejs/kit';
	import { SuccessMessages } from '@/utils/messages';

	let isLoading = false;
	let simpleError = ''; // could've done it better, but, that should be fine for this project.

	function enhancedSubmission() {
		isLoading = true;
		return async ({ result }: { result: ActionResult }) => {
			console.log(result);
			switch (result.type) {
				case 'failure': {
					console.log('failure');
					simpleError = result?.data?.message ?? 'Some error occured!';
					break;
				}
				case 'redirect': {
					toast.success(SuccessMessages.LOGIN_SUCCESS);
					goto(result.location);
					console.log('redirect');
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

<HeroWrapper />

<div class="container relative h-screen flex-col items-center justify-center">
	<div class="flex w-full items-center justify-between p-4 md:p-8">
		<Logo className="" />
		<div class="flex items-center justify-center gap-3">
			<LightSwitch />
		</div>
	</div>

	<div class="flex h-[80%] flex-col items-center justify-center space-y-6 lg:p-6 lg:pt-0">
		<div class="flex h-full flex-col items-center justify-center space-y-6 lg:p-8">
			<div class="flex flex-col gap-8">
				<div class="flex flex-col space-y-2 text-center">
					<h1 class="text-2xl font-semibold tracking-tight">Admin Login</h1>
					<p class="text-muted-foreground text-sm">Enter your credentials below to login</p>
				</div>

				<div class="">
					<form method="post" action="" on:submit={onSubmit} use:enhance={enhancedSubmission}>
						<div class="grid gap-2">
							<div class="grid gap-1">
								<Label class="sr-only" for="email">Email</Label>
								<Input
									name="email"
									id="email"
									placeholder="admin@example.com"
									type="email"
									disabled={isLoading}
									required
								/>
							</div>
							<div class="grid gap-1">
								<Label class="sr-only" for="password">Password</Label>
								<Input
									name="password"
									id="password"
									placeholder="***********"
									type="password"
									disabled={isLoading}
									required
								/>
							</div>

							{#if simpleError}
								<Alert.Root variant="destructive">
									<!-- <Alert.Title>Error</Alert.Title> -->
									<Alert.Description class="flex items-center justify-center gap-4 ">
										<AlertTriangle class="h-4 w-4" />
										{simpleError}
									</Alert.Description>
								</Alert.Root>
							{/if}
							<Button type="submit" disabled={isLoading}>
								{#if isLoading}
									<CircleDotDashed color="white" class="mr-2 h-4 w-4 animate-spin" />
								{/if}
								Sign In
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
	</div>
</div>
