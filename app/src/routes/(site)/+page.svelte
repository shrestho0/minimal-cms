<script lang="ts">
	import NavbarHomePage from '@/ui/NavbarHomePage.svelte';
	import Faqs from './Faqs.svelte';
	import InfiniteMovingCards from '@/components/ui/InfiniteMovingCards/InfiniteMovingCards.svelte';
	import InfiniteQuoteSection from '@/ui/InfiniteQuoteSection.svelte';
	import WhatsIncludedSection from '@/ui/WhatsIncludedSection.svelte';
	import Button from '@/components/ui/button/button.svelte';
	import { ArrowRightToLine, ExternalLink, HeartHandshake } from 'lucide-svelte';

	console.log('hwllo');
</script>

<div class=" lg:container">
	<NavbarHomePage />
</div>

<main class="home-bg h-auto w-full">
	<div class=" container max-w-5xl pt-48">
		<div class="  mx-auto grid select-none grid-cols-1 md:grid-cols-2">
			<div class="left-side space-y-8">
				<div class="big-text montserrat text-6xl font-bold text-[#D9D9D9]">
					Make simple
					<br />
					web pages
					<br />
					with markdown
				</div>
				<div class="smaller-text break-words text-lg text-[#D9D9D9]/50">
					An idiot admires complexity, a genius admires simplicity. Make simple web pages with
					markdown
					<!-- </span> -->
				</div>
			</div>
			<div
				class="items-right flex justify-end grayscale-0 transition-all duration-500 hover:grayscale"
			>
				<img src="/right-image.png" alt="" class=" h-[350px] w-auto pr-6 pt-6" />
				<img
					class="  absolute h-[370px] rounded-xl grayscale"
					src="https://www.shuttle.rs/_next/image?url=%2Fimages%2Fsections%2Fhero%2Fbg.png&w=750&q=75"
					alt=""
				/>
			</div>
		</div>
	</div>

	<WhatsIncludedSection />
	<InfiniteQuoteSection />
	<Faqs />
	<div class="mx-auto w-full max-w-5xl px-6 text-[#D9D9D9] lg:px-8">
		<div id="contribute" class=" grid grid-cols-1 gap-3 p-[2rem] md:grid-cols-2">
			<div class="left-side">
				<div class="big-text montserrat text-4xl font-semibold text-[#D9D9D9]">
					Get started with
					<br />
					mCMS now
				</div>
			</div>
			<div class="   ">
				<div class="xtext">
					Keep notes, share your thoughts, track tasks and do everything can be done with markdown
					effortlessly here.
				</div>
				<div class="buttons flex items-center gap-4 py-4">
					<a
						href="/register"
						class=" flex items-center justify-center gap-1 rounded border border-[#5F5BF1] bg-[#5F5BF1] p-2.5 text-[#D9D9D9]/90 hover:bg-[#5F5BF1]/80"
					>
						Get Started <ExternalLink class="h-4 w-4" />
					</a>

					<a
						href="https://github.com/shrestho0/minimal-cms"
						class=" flex items-center justify-center gap-1 rounded border p-2.5 text-[#D9D9D9]/90"
					>
						Contribute <HeartHandshake class="h-4 w-4" />
					</a>
				</div>
			</div>
		</div>
		<hr class="col-span-2" />

		<div class="  flex w-full flex-col items-center justify-center gap-2 py-4">
			<div>Copyright &copy; mCMS 2024. All rights reserved.</div>
			<div>
				Built with <span class="font-bold text-[#5F5BF1]">&nbsp;{'<3'}&nbsp;</span> by &nbsp;<a
					href="https://github.com/shrestho0"
					class=" underline hover:text-[#D9D9D9]">shrestho0</a
				>.
			</div>
		</div>
	</div>
</main>

<style>
</style>
