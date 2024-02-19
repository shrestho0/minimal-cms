<script lang="ts">
	export let href = '';
	export let block = false;
	export let size = 'md';
	export let style = 'primary';
	export let className = 'leading-6 md:px-3 md:py-2';

	const sizes: any = {
		xs: '',
		sm: 'px-3 py-1.5',
		md: 'px-4 py-2.5',
		lg: 'px-5 py-2.5'
	};

	const styles: any = {
		outline:
			'text-center border outline-2 transition-colors border-gray-300 text-gray-700 dark:text-gray-100 hover:border-gray-400 dark:text-white active:outline dark:hover:bg-dark dark:hover:border-white dark:hover:text-white',
		primary:
			'text-center bg-stone-950 outline-2 dark:bg-gray-100 hover:bg-stone-800 dark:hover:bg-white focus-visible:bg-stone-500 dark:focus-visible:bg-gray-100 text-white dark:text-black border-transparent active:outline shadow-sm',
		muted: 'text-sm font-semibold leading-6 text-gray-800 dark:text-gray-100',
		hover:
			'text-center text-stone-950 hover:text-gray-900 focus:text-gray-900 focus:bg-gray-200 focus:outline-none focus:shadow-outline hover:bg-gray-200 dark:hover:bg-gray-600 dark:focus:bg-gray-600 dark:focus:text-white dark:hover:text-white dark:text-gray-200'
	};
</script>

<a
	href={href.startsWith('http') ? href : href}
	class="rounded-md text-sm font-semibold transition focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 {block
		? 'w-full'
		: ''} {size ? sizes[size] : ''} {style ? styles[style] : ''} {className}"
>
	<slot />
</a>
