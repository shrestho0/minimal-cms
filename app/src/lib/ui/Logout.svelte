<script lang="ts">
	import Button from '@/components/ui/button/button.svelte';
	export let formClasses: string = '';
	export let btnClasses: string = '';
</script>

<form class={formClasses} action="/logout" method="post">
	<Button class={btnClasses} type="submit">Logout</Button>
</form>
