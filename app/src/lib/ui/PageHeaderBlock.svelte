<script lang="ts">
	import { Button } from '@/components/ui/button';
	import LightSwitch from '@/ui/LightSwitch.svelte';
	import * as DropdownMenu from '@/components/ui/dropdown-menu';
	import Separator from '@/components/ui/separator/separator.svelte';
	import { onMount } from 'svelte';
	import * as Avatar from '@/components/ui/avatar';
	import { md5 } from '@/utils/common';

	import { page } from '$app/stores';
	import { fly, slide } from 'svelte/transition';
	import SidePanel from './SidePanel.svelte';
	import { Alert } from '@/components/ui/alert';
	import type { PageStatus } from '@/types/pages-and-stuff';

	export let user: {
		email: string;
		name?: string;
		collectionName?: string;
		username?: string;
	};
	export let title = '';
	export let pageStatus: PageStatus | null = null;

	let regularUser = false;

	onMount(() => {
		if (user?.name || user?.collectionName) {
			regularUser = true;
		}
		console.log(user);
	});

	let open = false;
	function toggleMobileMenu() {
		open = !open;
	}

	export let pages: any;
	export let customizationPages: any = null;
</script>

<div class="header h-16 border-b border-gray-200 bg-white">
	<div class="flex h-full items-center justify-between px-3 md:px-8">
		<div class="flex items-center justify-center gap-2">
			<div>
				<div class="flex items-center justify-center gap-2">
					<button
						class="relative h-10 w-10 text-gray-500 focus:outline-none md:hidden"
						on:click={toggleMobileMenu}
					>
						<span class="sr-only">Open main menu</span>
						<div
							class="absolute left-1/2 top-1/2 block w-5 -translate-x-1/2 -translate-y-1/2 transform"
						>
							<span
								aria-hidden="true"
								class=" {open
									? 'rotate-45'
									: '-translate-y-1.5'}  absolute block h-0.5 w-5 transform bg-current transition duration-200 ease-in-out"
							>
							</span>
							<span
								aria-hidden="true"
								class="{open
									? 'opacity-0'
									: ''} absolute block h-0.5 w-5 transform bg-current transition duration-200 ease-in-out"
							>
							</span>
							<span
								aria-hidden="true"
								class="{open
									? '-rotate-45'
									: 'translate-y-1.5'} absolute block h-0.5 w-5 transform bg-current transition duration-200 ease-in-out"
							>
							</span>
						</div>
					</button>
				</div>
			</div>
			{#if pageStatus == 'banned'}
				<Alert variant="destructive" class="  ">
					Status: <span class=" font-semibold">Banned</span>
				</Alert>
			{:else if pageStatus == null}
				<h1 class=" text-md break-words text-black md:text-2xl">{@html title}</h1>
			{:else}
				<Alert variant="default" class="  ">
					<span class=" ">{@html title}</span>
				</Alert>
			{/if}
		</div>

		<slot />
		<div class="flex h-full items-center justify-between gap-3 px-8">
			{#if !pageStatus}
				<Separator class="mx-1 hidden h-6 md:block" orientation="vertical" />
			{/if}
			<div
				class="greet hidden flex-col items-center
			 justify-center p-1 text-sm text-black md:flex"
			>
				<div class="">
					{regularUser ? 'Welcome, ' + user?.name : 'Welcome, admin'}!
				</div>
			</div>
			<div class="items center flex space-x-4">
				<DropdownMenu.Root>
					<DropdownMenu.Trigger asChild let:builder>
						<Button
							builders={[builder]}
							variant="outline"
							class="hover: border-none   bg-transparent px-0 text-base hover:bg-transparent md:mr-2"
						>
							<Avatar.Root>
								<Avatar.Image src={'https://gravatar.com/avatar/' + md5(user?.email)} alt="@" />
								<Avatar.Fallback>{user?.email?.slice(0, 2)}</Avatar.Fallback>
							</Avatar.Root>
						</Button>
					</DropdownMenu.Trigger>
					<DropdownMenu.Content class="w-56">
						<DropdownMenu.Label>{user?.email}</DropdownMenu.Label>
						<DropdownMenu.Separator />
						<DropdownMenu.Group>
							{#if regularUser}
								<DropdownMenu.Item>
									<a href={'/' + user.username} target="_blank"> Profile Page </a>
								</DropdownMenu.Item>
							{/if}

							<DropdownMenu.Item
								class={$page.url.pathname?.includes('dashboard')
									? 'bg-blue-600 hover:bg-blue-600 focus:bg-blue-600 '
									: ''}
							>
								<a href="/{regularUser ? '' : '_/'}dashboard"> Dashboard</a>
							</DropdownMenu.Item>
							<DropdownMenu.Item
								class={$page.url.pathname?.includes('change-password') ? 'bg-blue-600' : ''}
							>
								<a href="/{regularUser ? '' : '_/'}change-password"> Change Password </a>
							</DropdownMenu.Item>
						</DropdownMenu.Group>
						<DropdownMenu.Separator />
						<DropdownMenu.Item>
							<form action="/logout" method="post">
								<button class="w-full p-2" type="submit">Logout</button>
							</form>
						</DropdownMenu.Item>
					</DropdownMenu.Content>
				</DropdownMenu.Root>
			</div>
		</div>
	</div>
</div>

{#if open}
	<div class="border bg-white" transition:slide>
		<SidePanel {pages} {customizationPages} on:click={toggleMobileMenu} />
	</div>
{/if}
