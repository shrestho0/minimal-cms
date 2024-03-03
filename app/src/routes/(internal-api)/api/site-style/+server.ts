
import type { SiteStyle } from '@/types/customizations';
import dbTables from '@/utils/db-tables';
import defaultCssData from '@/utils/default-css-data';
import { jsonToCSS, setPBSiteKey } from '@/utils/index.server';
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ url, locals }) => {
    const u = url.searchParams.get('u');
    console.log("Site style request received", url.searchParams);


    let styleCss = "";


    if (u) {

        // find style from user
        console.log("User found inside ", u);

        // find the actual design from the user
        setPBSiteKey(locals.pb);
        const ownerStyles = await locals.pb.collection(dbTables.style).getFirstListItem(`user.username = "${u}"`).catch((e) => {
            return null;
        });

        if (ownerStyles && ownerStyles?.styleJson) {

            styleCss = jsonToCSS(ownerStyles as unknown as SiteStyle);
            console.log("Owner styles found", "styleCss", styleCss, "ownerStyles", ownerStyles);
        }

    }


    if (!styleCss) {
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