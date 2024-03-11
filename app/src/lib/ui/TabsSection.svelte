<script lang="ts">
	import Tabs from '@/components/ui/Tabs/Tabs.svelte';
	const tabs = [
		{
			title: 'Product',
			value: 'product',
			content:
				'<div className="w-full overflow-hidden relative h-full rounded-2xl p-10 text-xl md:text-4xl font-bold text-white bg-gradient-to-br from-purple-700 to-violet-900"> <p>Product Tab</p> <img src="/new-page.png" alt="dummy image" width="1000" height="1000" class="object-cover object-left-top h-[60%]  md:h-[90%] absolute -bottom-10 inset-x-0 w-[90%] rounded-xl mx-auto" /> </div>'
		},
		{
			title: 'Services',
			value: 'services',
			content:
				'<div className="w-full overflow-hidden relative h-full rounded-2xl p-10 text-xl md:text-4xl font-bold text-white bg-gradient-to-br from-purple-700 to-violet-900"> <p>Services tab</p> <img src="/new-page.png" alt="dummy image" width="1000" height="1000" class="object-cover object-left-top h-[60%]  md:h-[90%] absolute -bottom-10 inset-x-0 w-[90%] rounded-xl mx-auto" /> </div>'
		},
		{
			title: 'Playground',
			value: 'playground',
			content:
				'<div className="w-full overflow-hidden relative h-full rounded-2xl p-10 text-xl md:text-4xl font-bold text-white bg-gradient-to-br from-purple-700 to-violet-900"> <p>Playground tab</p> <img src="/new-page.png" alt="dummy image" width="1000" height="1000" class="object-cover object-left-top h-[60%]  md:h-[90%] absolute -bottom-10 inset-x-0 w-[90%] rounded-xl mx-auto" /> </div>'
		},
		{
			title: 'Content',
			value: 'content',
			content:
				'<div className="w-full overflow-hidden relative h-full rounded-2xl p-10 text-xl md:text-4xl font-bold text-white bg-gradient-to-br from-purple-700 to-violet-900"> <p>Content tab</p> <img src="/new-page.png" alt="dummy image" width="1000" height="1000" class="object-cover object-left-top h-[60%]  md:h-[90%] absolute -bottom-10 inset-x-0 w-[90%] rounded-xl mx-auto" /> </div>'
		},
		{
			title: 'Random',
			value: 'random',
			content:
				'<div className="w-full overflow-hidden relative h-full rounded-2xl p-10 text-xl md:text-4xl font-bold text-white bg-gradient-to-br from-purple-700 to-violet-900"> <p>Random tab</p> <img src="/new-page.png" alt="dummy image" width="1000" height="1000" class="object-cover object-left-top h-[60%]  md:h-[90%] absolute -bottom-10 inset-x-0 w-[90%] rounded-xl mx-auto" /> </div>'
		}
	];
</script>

<div
	class="relative mx-auto mb-24 flex h-[20rem] w-[80vw] max-w-5xl flex-col items-start justify-start [perspective:1000px] md:h-[40rem] lg:w-[60vw]"
>
	<Tabs
		propTabs={tabs}
		contentClassName="rounded-2xl bg-gradient-to-b from-stone-900 to-black p-10 text-xl font-bold md:text-4xl mt-24"
	/>
</div>
