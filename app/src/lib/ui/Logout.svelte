<script lang="ts">
	import Button from '@/components/ui/button/button.svelte';
	import { AppLinks } from '@/utils/app-links';
	export let formClasses: string = '';
	export let btnClasses: string = '';

	async function handleLogoutSubmission() {
		//
		const response = await fetch('/api/auth/logout', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			}
		});

		window.location.href = AppLinks.LOGIN;
	}
</script>

<Button class={btnClasses} on:click={handleLogoutSubmission}>Logout</Button>
