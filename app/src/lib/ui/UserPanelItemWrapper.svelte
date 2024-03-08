<script lang="ts">
	export let title: string = '';
	export let className: string = ' my-3 rounded-lg bg-white px-8 py-4 shadow-md';
	// export let className: string =
	// 	'   w-full rounded-lg border my-3 px-4 py-3 text-sm [&>svg~*]:pl-7 [&>svg+div]:translate-y-[-3px] [&>svg]:absolute [&>svg]:left-4 [&>svg]:top-4 [&>svg]:text-foreground bg-background text-foreground  ';
</script>

<div class={className}>
	<h2 class=" font-semibold">{title}</h2>
	<slot />
</div>
