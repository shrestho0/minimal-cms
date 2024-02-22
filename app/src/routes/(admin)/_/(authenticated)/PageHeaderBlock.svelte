<script lang="ts">
	import { Button } from '@/components/ui/button';
	import LightSwitch from '@/ui/LightSwitch.svelte';
	import * as DropdownMenu from '@/components/ui/dropdown-menu';
	import * as Avatar from '@/components/ui/avatar';
	import { page } from '$app/stores';
	import { md5 } from '@/utils/common';
	import Logout from '@/ui/Logout.svelte';
	import Separator from '@/components/ui/separator/separator.svelte';

	export let user = {
		email: ''
	};
	export let title = '';
</script>

<div class="header h-16 border-b border-gray-200 bg-white">
	<div class="flex h-full items-center justify-between px-8">
		<h1 class="text-2xl font-semibold">{title}</h1>
		<slot />
		<div class="flex h-full items-center justify-between gap-3 px-8">
			<LightSwitch />
			<Separator class="mx-1 hidden h-6 md:block" orientation="vertical" />
			<div class="items center flex space-x-4">
				<!-- Welcome, {data?.admin?.name}! -->
				<DropdownMenu.Root>
					<DropdownMenu.Trigger asChild let:builder>
						<Button builders={[builder]} variant="ghost" class="  px-0 text-base md:mr-2">
							<Avatar.Root>
								<!-- <Avatar.Image src="https://github.com/shrestho0.png" alt="@" /> -->
								<Avatar.Image src={'https://gravatar.com/avatar/' + md5(user?.email)} alt="@" />
								<Avatar.Fallback
									>{$page.data?.user_username?.slice(0, 2)?.toUpperCase()}</Avatar.Fallback
								>
							</Avatar.Root>
						</Button>
					</DropdownMenu.Trigger>

					<DropdownMenu.Content>
						<DropdownMenu.Group>
							<!-- <Button href=""> -->
							<DropdownMenu.Item class="hover:bg-transparent focus:bg-transparent">
								<Button variant="outline" href="/_/change-password">Change Password</Button>
							</DropdownMenu.Item>
							<DropdownMenu.Item>
								<Logout formClasses="w-full" btnClasses="w-full" />
							</DropdownMenu.Item>
							<!-- </Button> -->
						</DropdownMenu.Group>
					</DropdownMenu.Content>
				</DropdownMenu.Root>
			</div>
		</div>
	</div>
</div>
