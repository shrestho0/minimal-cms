<script lang="ts">
	import Hero from '@/ui/Hero.svelte';
	import Faqs from './Faqs.svelte';
	import NavbarHomePage from '@/ui/NavbarHomePage.svelte';
	import Sparkles from '@/components/ui/Sparkles/Sparkles.svelte';
	import SparklesHeroSection from '@/ui/SparklesHeroSection.svelte';
	import HeroWrapper from '@/ui/HeroWrapper.svelte';
	import Spotlight from '@/components/ui/Spotlight/Spotlight.svelte';
	import TabsSection from '@/ui/TabsSection.svelte';
	import TextGenerateEffect from '@/components/ui/TextGenerateEffect/TextGenerateEffect.svelte';
	import { fade, slide } from 'svelte/transition';
	import InfiniteQuoteSection from '@/ui/InfiniteQuoteSection.svelte';
	import Button from '@/components/ui/button/button.svelte';
	import { spring } from 'svelte/motion';
	import { onMount } from 'svelte';
	import { Github } from 'lucide-svelte';
	import LightSwitch from '@/ui/LightSwitch.svelte';
	import GradientButton from '@/ui/GradientATag.svelte';

	// temp x-buttons delayed loading
	let loadXButtons = false;
	onMount(() => {
		setTimeout(() => {
			loadXButtons = true;
		}, 2000);
	});
</script>

<svelte:head>
	<title>mCMS</title>
</svelte:head>

<div class="h-screen w-full bg-black">
	<section class="  relative mx-auto max-w-screen-xl bg-black">
		<NavbarHomePage />
		<div
			class="bg-grid-white/[0.02] font-poppins relative flex h-[40rem] overflow-hidden rounded-md px-16 antialiased md:items-center md:justify-center lg:px-32"
		>
			<Spotlight className="-top-40 left-0 md:left-60 md:-top-20" fill="white" />
			<div class=" relative z-10 mx-auto w-full max-w-7xl p-4 pt-20 text-center md:pt-0">
				<!-- <h2
			class="bg-opacity-50 bg-gradient-to-b from-neutral-50 to-neutral-400 bg-clip-text text-center text-4xl font-bold text-transparent md:text-7xl"
		> -->
				<!-- <TextGenerateEffect
				className="text-center "
				motionClassName="font-light"
				words="The road to freedom starts from here"
			/> -->
				<!-- <TextGenerateEffect
				words=" mCMS "
				className="bg-opacity-50 tracking-widest bg-gradient-to-b from-neutral-50 to-neutral-400 bg-clip-text text-center text-4xl font-bold text-transparent md:text-7xl"
			/> -->
				<TextGenerateEffect
					words=" mCMS "
					motionClassName="relative z-10 bg-gradient-to-tr from-neutral-200 to-neutral-600 bg-clip-text text-center
				font-sans text-3xl md:text-7xl font-bold text-transparent  "
				/>

				<!-- mCMS -->

				<!-- <br /> -->
				<!-- </h2> -->

				<p class="mx-auto mt-6 max-w-lg text-center text-base font-normal">
					<!-- Make your place in the Internet -->
					<TextGenerateEffect
						className="text-md tracking-wide "
						motionClassName=" text-white  font-light"
						words="         An idiot admires complexity, a genius admires simplicity. Make simple web pages with markdown."
					/>
				</p>
				{#if loadXButtons}
					<div in:fade class="x-buttons mt-4 flex h-16 items-center justify-center gap-4">
						<!-- Load these buttons after previous designs are loaded-->
						<GradientButton title="Github Link" href="https://github.com/shrestho0/minimal-cms">
							<div slot="left">
								<Github size={20} />
							</div>
						</GradientButton>

						<GradientButton title="Start Now" href="/register">
							<div slot="right">
								<svg
									width="16"
									height="16"
									viewBox="0 0 24 24"
									fill="none"
									xmlns="http://www.w3.org/2000/svg"
								>
									<path
										stroke="currentColor"
										stroke-linecap="round"
										stroke-linejoin="round"
										stroke-width="1.5"
										d="M10.75 8.75L14.25 12L10.75 15.25"
									></path>
								</svg>
							</div>
						</GradientButton>
						<!-- <Button
							variant="default"
							class="flex gap-2 border border-white/90 bg-transparent py-4 "
						>
							<Github size={24} /> Find on Github
						</Button>
						<Button class="flex gap-2 border border-white/90 bg-transparent py-4 " href="/register"
							>Get Started</Button
						> -->
					</div>
				{:else}
					<div class="mt-4 h-16">&nbsp;</div>
				{/if}
			</div>
		</div>

		<Sparkles
			minSize={0.8}
			maxSize={2}
			particleDensity={300}
			particleColor="#FFFFFF"
			className="w-full h-auto my-40 absolute inset-0 z-0 opacity-45"
		/>
	</section>
</div>
<LightSwitch />
<TabsSection />
<InfiniteQuoteSection />
<!-- <Hero /> -->

<!-- <SparklesHeroSection /> -->
<Faqs />
<!-- 
<div id="debug" class="mb-32">
	<div>Temp site urls for testing</div>
	<ul>
		Links:
		<li>
			- <a href="/login">User Login</a>
		</li>
		<li>
			- <a href="/register">User Register</a>
		</li>
		<li>
			- <a href="/_/login">Admin Login</a>
		</li>
		<li>
			- <a href="/a/b">Concept: User's page</a>
		</li>
	</ul>
</div> -->
