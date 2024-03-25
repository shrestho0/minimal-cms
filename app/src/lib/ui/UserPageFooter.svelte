<script lang="ts">
	import type { SiteFooterType } from '@/types/customizations';
	import Logo from './Logo.svelte';

	export let footerData: SiteFooterType;
</script>

<footer class="  text-black dark:text-black">
	<div
		class=" flex flex-col items-center justify-between border p-3 dark:border-border md:flex-row"
	>
		<div class="left">
			<p style="text-black">{footerData.text}</p>
		</div>
		<nav>
			<ul class="flex items-center justify-center gap-2">
				{#if footerData?.social_json?.length > 0}
					{#each footerData.social_json as item}
						<li>
							<a href={item.href} class="text-black hover:text-black/80 text-[20px]">
								{#if item.fa_icon}
									<i class=" fa-brands fa-{item.fa_icon}"></i>
								{:else}
									<span>{item.title}</span>
								{/if}
							</a>
						</li>
					{/each}
				{/if}
			</ul>
		</nav>
		<div class="w-auto">
			<div class="flex items-center justify-center gap-3">
				Powered By <Logo className="text-black dark:text-black" />
			</div>
		</div>
	</div>
</footer>
