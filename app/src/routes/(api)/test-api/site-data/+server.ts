import type { SiteDataTypes } from "@/types/customizations";
import { json } from "@sveltejs/kit";

export const GET = async () => {

    // Returns header, footer data
    const customizations: Partial<SiteDataTypes> = {
        header: {
            site_title: " A's Pages",
            logo_url: "",
            nav_json: [{ title: "Link 1", href: "#" }, { title: "Link 2", href: "#" }, { title: "Link 3", href: "#" }]
        },
        footer: {
            text: "Copyright 2021 | A's Page: B | All Rights Reserved",
            social_json: [{ href: "#", title: "Facebook", fa_icon: "facebook" }, { href: "#", title: "Twitter", fa_icon: "twitter" }]
        },
    }


    return json(customizations);
};