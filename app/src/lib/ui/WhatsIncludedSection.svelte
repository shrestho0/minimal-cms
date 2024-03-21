<script>
	import { BookOpen, Contact2, Key, LayoutDashboard, Palette } from 'lucide-svelte';
	import WhatsIncludedSingleItem from './WhatsIncludedSingleItem.svelte';

    const whatsItems = [
        {title: 'Authentication', desc:'Built-in User and admin authentication', iconComp: Key},
        {title: 'Dashboards', desc: 'User and admin dashboards included in the system', iconComp: LayoutDashboard},
        {title: 'Theme/Styling', desc: 'Design of pages html tags can be modified.', iconComp: Palette},
        {title: 'Pages', desc: 'User can create and modify their public web pages', iconComp: BookOpen},
        {title: 'Pages', desc: 'User can create and modify their public profiles', iconComp: Contact2},
        {title: 'Source Code', desc: 'Triple Bam!!! Source code is available <a href="https://github.com/shrestho0/minimal-cms/" style="font-weight: normal;" >here</a>. ', iconComp: Contact2},



    ]
</script>

<section class="mx-auto max-w-5xl select-none px-6 py-16 sm:py-24 lg:px-8 lg:py-36 " id="features">
	<h2 class="mb-10 text-center text-3xl font-bold tracking-tight text-[#D9D9D9] lg:text-4xl">
		What's included
	</h2>
	<div class="grid grid-cols-1 gap-4 text-white/80 sm:grid-cols-2 sm:gap-6 lg:grid-cols-3 lg:gap-8">


        {#each whatsItems as what }
        <WhatsIncludedSingleItem title={what.title} desc={what.desc} lucideIcon={what.iconComp} />
        {/each}
     
	</div>
</section>
