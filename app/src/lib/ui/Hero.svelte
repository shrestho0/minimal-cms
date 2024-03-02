<script>
	import HeroWrapper from './HeroWrapper.svelte';
	import Link from './Link.svelte';
</script>

<HeroWrapper>
	<div class=" pt-20 sm:pt-32">
		<div class="mx-auto max-w-7xl">
			<div class="mx-auto max-w-2xl text-center">
				<h1 class="text-4xl font-bold tracking-tight text-black sm:text-6xl dark:text-white">
					mCMS
				</h1>
				<p class="mt-6 text-lg leading-8 text-gray-900 dark:text-gray-100">Make your place in the Internet</p>
				<div class="mt-10 flex items-center justify-center gap-x-2 md:gap-x-6">
					<Link href="https://github.com/shrestho0/p471">Github</Link>

					<Link href="https://p471.pockethost.io/_/">
						PocketBase
						<!-- <span aria-hidden="true" class="pl-1 text-black dark:text-white"> → </span> -->
					</Link>
				</div>
			</div>
			<div class="mt-4 flex items-center justify-center md:mt-8">
				<!-- <img
					src="/blogging-bro.svg"
					alt=""
					class="mt-12 inline-block md:mt-20"
					width={600}
					height={600}
					loading="eager"
				/> -->
				<!-- There will be something -->
			</div>
		</div>
	</div>
</HeroWrapper>
