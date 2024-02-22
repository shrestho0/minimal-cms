
import type { SiteStyle } from '@/types/customizations';
import defaultCssData from '@/utils/default-css-data';
import { jsonToCSS } from '@/utils/index.server';
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ url }) => {
    const u = url.searchParams.get('u');

    let styleCss = "";

    if (u) {
        console.log("User found", u);
        // Check if user is valid and has css
        const userValidAndHasCss = true;

        if (userValidAndHasCss) {
            console.log("Processing user css")
            // fetch user css
            const userStyles: SiteStyle = {
                fontFamily: "Railway, sans-serif",
                fontLoadUrl: "https://fonts.googleapis.com/css2?family=Railway:wght@300;400;500;700&display=swap",
                styleJson: {
                    "h1": {
                        "font-size": "2rem",
                        "color": "yellow"
                    },
                    "h2": {
                        "font-size": "1.5rem",
                        "color": "pink"
                    },
                    "h3": {
                        "font-size": "1.25rem",
                        "color": "green"
                    },
                    "h4": {
                        "font-size": "1rem",
                        "color": "purple"
                    }
                }
            }
            styleCss = jsonToCSS(userStyles);
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