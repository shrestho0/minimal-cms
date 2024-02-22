<script lang="ts">
	import PreDebug from '@/dev/PreDebug.svelte';
	import type { SiteDataTypes } from '@/types/customizations';
	import { onMount } from 'svelte';

	export let data: SiteDataTypes;

	onMount(() => {
		console.log('data', data);
	});
</script>

<svelte:head>
	<title>{data?.header?.site_title ? data.header.site_title + '| mCMS' : 'mCMS'}</title>

	{#if data?.stylesheet_url}
		<link rel="stylesheet" type="text/css" href={data.stylesheet_url} />
	{/if}
</svelte:head>

<slot />

<div class="footer">
	<pre>
        (User-Page Group Layout)
        Pages will be light mode only with base tailwind styles
    </pre>
	<PreDebug data={data.header} title="Header" />
</div>

<h1>H1: Lorem ipsum dolor sit, amet consectetur adipisicing elit. Omnis, quo.</h1>
<h2>H2: Lorem ipsum dolor sit, amet consectetur adipisicing elit. Omnis, quo.</h2>
<h3>H3: Lorem ipsum dolor sit, amet consectetur adipisicing elit. Omnis, quo.</h3>
<h4>H4: Lorem ipsum dolor sit, amet consectetur adipisicing elit. Omnis, quo.</h4>
<h5>H5: Lorem ipsum dolor sit, amet consectetur adipisicing elit. Omnis, quo.</h5>
<h6>H6: Lorem ipsum dolor sit, amet consectetur adipisicing elit. Omnis, quo.</h6>
<p>P: Lorem ipsum dolor sit amet consectetur adipisicing elit</p>

<div class="footer">
	<PreDebug data={data.footer} title="Footer" />
</div>
