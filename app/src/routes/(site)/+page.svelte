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
<span class="hero-gradient"></span>

<main class="home-bg">
    

<div class=" container max-w-5xl   pt-48">
	<div class="  grid grid-cols-1 md:grid-cols-2 mx-auto select-none     ">
		<div class="left-side space-y-8">
			<div class="big-text montserrat text-6xl font-bold text-[#D9D9D9]">
				Make simple
				<br />
				web pages
				<br />
				with markdown
			</div>
			<div class="smaller-text break-words text-lg text-white/50">
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


<WhatsIncludedSection/>
<InfiniteQuoteSection />
<Faqs />
<div class="mx-auto max-w-5xl   px-6  lg:px-8  text-[#D9D9D9] w-full h-56">

<div id="contribute" class=" grid grid-cols-1 md:grid-cols-2">
    <div class="left-side ">
        <div class="big-text montserrat text-4xl font-semibold text-[#D9D9D9]">
            Get started with
            <br>
            mCMS now
        </div>
    </div>
    <div class="   ">
        <div class="xtext ">Keep notes, share your thoughts, track tasks and do everything can be done with markdown effortlessly here.</div>
        <div class="buttons py-4 flex items-center  gap-4 ">
            <a href="/register" class=" flex bg-[#5F5BF1] border border-[#5F5BF1] hover:bg-[#5F5BF1]/80 p-2.5 rounded text-white/90 items-center justify-center gap-1">
            Get Started <ExternalLink class="w-4 h-4"/>
            </a>

            <a href="https://github.com/shrestho0/minimal-cms" class=" flex border  p-2.5 rounded text-white/90 items-center justify-center gap-1">
                Contribute <HeartHandshake class="w-4 h-4"/>
                </a>
        </div>
    </div>

</div>
<hr>
<div class="flex items-center justify-center pt-8">
    copyright (c) mCMS 2024. All rights reserved. Built with <span class="text-[#5F5BF1] font-bold ">&nbsp;{'<3'}&nbsp;</span> by &nbsp; <a href="https://github.com/shrestho0" class=" underline hover:text-white">shrestho0</a>.
</div>
</div>

</main>


<style>
	.home-bg {
		background: rgb(16, 16, 25);
		background: -moz-linear-gradient(180deg, rgba(16, 16, 25, 1) 50%, rgba(34, 38, 60, 1) 100%);
		background: -webkit-linear-gradient(180deg, rgba(16, 16, 25, 1) 50%, rgba(34, 38, 60, 1) 100%);
		background: linear-gradient(180deg, rgba(16, 16, 25, 1) 50%, rgba(34, 38, 60, 1) 100%);
	}

	.home-bg-rev {
		background: rgb(34, 38, 60);
		background: -moz-linear-gradient(180deg, rgba(34, 38, 60, 1) 50%, rgba(16, 16, 25, 1) 100%);
		background: -webkit-linear-gradient(180deg, rgba(34, 38, 60, 1) 50%, rgba(16, 16, 25, 1) 100%);
		background: linear-gradient(180deg, rgba(34, 38, 60, 1) 50%, rgba(16, 16, 25, 1) 100%);
	}

	.hero-gradient {
		background: radial-gradient(circle 50vh at 50% 25%, #101019, #5f5bf1 60%);
		filter: blur(180px);
		height: 30vh;
		opacity: 1;
		position: fixed;
		top: -5vh;
		left: 0vw;
		width: 100%;
		/* z-index: -1; */
	}
</style>
