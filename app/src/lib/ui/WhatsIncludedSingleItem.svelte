<script lang="ts">
    export let title: string;
    export let desc: string;
    export let lucideIcon:ConstructorOfATypedSvelteComponent
</script>
<div
			class="    [&amp;>svg]:mb-2 [&amp;>svg]:size-10 flex flex-col rounded-lg border p-6 shadow-sm"
		>
			<div class="mb-2">
				<svelte:component this={lucideIcon} class="mb-2 size-10 stroke-current" />
			</div>
			<h3 class="mb-1 text-base font-semibold tracking-tight">{title}</h3>
			<p class="  text-[15px]">{@html desc}</p>
		</div>