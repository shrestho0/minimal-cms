import type { LayoutServerLoad } from "../$types";

export const load: LayoutServerLoad = async ({ locals, parent, fetch, params }) => {
    await parent();
    const siteData = await fetch("/test-api/site-data"); // Fetching Header, Footer
    const siteDataJson = await siteData.json();
    const stylesheet_url = `/test-api/site-style/?u=${params.username}&v=${Math.random()}`

    console.log("siteDataJson", siteDataJson, "stylesheet_url", stylesheet_url)

    return {
        ...siteDataJson,
        stylesheet_url

    };
};