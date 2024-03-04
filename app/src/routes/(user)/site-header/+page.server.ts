import type { Actions, PageServerLoad } from "./$types";
import { fail, redirect } from "@sveltejs/kit";
import { AppLinks, BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";

export const load: PageServerLoad = async ({ locals, fetch, cookies }) => {

    // TODO: Get site-header
    const dummy = {
        id: "",
        site_title: "",
        logo: "ss",
        nav_links_json: []
    }


    const siteHeader = await fetch(BackendApiEndpoints.USER_SITE_HEADER, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "JWT": parseTokenFromCookie(cookies)
        }
    }).then(r => r.json()).catch(e => {
        console.error(e)
        return dummy
    })
    console.log(siteHeader, siteHeader.nav_json,)

    siteHeader.nav_json = JSON.parse(siteHeader.nav_json)

    return {
        siteHeader
    }
};


export const actions: Actions = {
    updateHeader: async ({ request, fetch, locals, cookies }) => {

        const { site_title, nav_json, logo } = Object.fromEntries(await request.formData())
        console.log(site_title, nav_json, logo, JSON.stringify(nav_json))

        const updateWithObj = {} as {
            site_title?: string,
            nav_json?: string,
            logo?: string
        }

        if (site_title) updateWithObj["site_title"] = site_title as string;
        if (nav_json) updateWithObj["nav_json"] = nav_json as string;
        if (logo != undefined) updateWithObj["logo"] = logo as string;

        let message = "Updated Site Footer";
        const updatedSiteHeader = await fetch(BackendApiEndpoints.USER_SITE_HEADER, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "JWT": parseTokenFromCookie(cookies)
            },
            body: JSON.stringify(updateWithObj)

        }).then(res => res.json()).catch(err => {
            message = "Failed to update site footer"
        });

        console.log("===== Updated Site Header =====")
        console.log(updatedSiteHeader, updatedSiteHeader.siteHeader.logo)
        console.log("===== Updated Site Header =====")

        return { message, logo: updatedSiteHeader.siteHeader.logo }

    }

};
