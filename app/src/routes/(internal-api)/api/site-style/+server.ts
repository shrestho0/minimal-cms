
import type { SiteStyle } from '@/types/customizations';
import { BackendApiEndpoints } from '@/utils/app-links';
import defaultCssData from '@/utils/default-css-data';
import { jsonToCSS } from '@/utils/index.server';
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ url, locals, fetch }) => {
    const u = url.searchParams.get('u');
    console.log("Site style request received", url.searchParams);


    let styleCss = "";


    if (u) {

        // find style from user
        console.log("User found inside ", u);

        // find the actual design from the user
        const ownerStyles = await fetch(BackendApiEndpoints.PUBLIC_DESIGN + `/${u}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        }).then((res) => res.json()).catch((err) => { return null; });



        if (ownerStyles?.siteStyle && ownerStyles?.siteStyle?.styleJson) {

            // ownerStyles.SiteStyle.styleJson = JSON.parse(ownerStyles.siteStyle.styleJson);

            ownerStyles.siteStyle.styleJson = JSON.parse(ownerStyles.siteStyle.styleJson);
            // console.log("Owner styles found", "ownerStyles", ownerStyles.siteStyle.styleJson);

            styleCss = jsonToCSS(ownerStyles.siteStyle as unknown as SiteStyle);
            // console.log("Owner styles found", "styleCss", styleCss, "ownerStyles", ownerStyles?.siteStyle);
        }

    }


    if (!styleCss) {

        console.log("No user found, using default styles");
        styleCss = jsonToCSS(defaultCssData);
    }




    // Return css

    const res = new Response(styleCss, {
        headers: {
            'Content-Type': 'text/css'
        }
    });

    return res;


    // Make this 

}