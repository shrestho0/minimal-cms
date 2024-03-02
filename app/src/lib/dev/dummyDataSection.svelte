<script lang="ts">
	import { Button } from '@/components/ui/button';

	export let populateRandomData = (idx: number) => {
		console.log('populateRandomData', idx);
	};
</script>

<div class="dummy_data_btn border p-3 text-center">
	Populate Random Data
	<div class=" flex items-center justify-center gap-2">
		{#each Array(5) as _, idx}
			<Button on:click={() => populateRandomData(idx)} class="  text-center text-sm underline"
				>{idx + 1}</Button
			>
		{/each}
	</div>
</div>
