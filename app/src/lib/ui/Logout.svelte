<script lang="ts">
	import { applyAction, enhance } from '$app/forms';
	import { invalidateAll } from '$app/navigation';
	import Button from '@/components/ui/button/button.svelte';
	import { AppLinks } from '@/utils/app-links';
	import { LogOut } from 'lucide-svelte';
	export let formClasses: string = '';
	export let btnClasses: string = '';

	// async function handleLogoutSubmission() {
	// 	window.location.href = AppLinks.LOGIN;
	// }
</script>

<form
	action="/logout"
	method="post"
	use:enhance={() => {
		return async ({ result }) => {
			applyAction(result);
		};
	}}
>
	<Button variant="outline" type="submit" class={btnClasses}>
		<LogOut class="h-4 w-4" />
	</Button>
</form>
