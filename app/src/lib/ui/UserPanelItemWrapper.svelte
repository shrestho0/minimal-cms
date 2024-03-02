<script lang="ts">
	export let title: string = '';
	export let className: string = ' my-3 rounded-lg bg-white px-8 py-4 shadow-md';
</script>

<div class={className}>
	<h2 class=" font-semibold">{title}</h2>
	<slot />
</div>
