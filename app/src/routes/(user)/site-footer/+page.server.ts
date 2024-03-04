import type { Actions, PageServerLoad } from "./$types";
import { fail } from "@sveltejs/kit";
import type { SiteFooterType } from "@/types/customizations";
import { BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";

export const load: PageServerLoad = async ({ locals, fetch, cookies }) => {

    // TODO: Get site-footer 
    const siteFooterRes = await fetch(BackendApiEndpoints.USER_SITE_FOOTER, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "JWT": parseTokenFromCookie(cookies)
        }
    }).then(res => res.json()) as SiteFooterType & {
        social_json: string
    };

    // const socialJson = siteFooterRes?.social_json || {};
    siteFooterRes.social_json = JSON.parse(siteFooterRes?.social_json ?? "[]")

    // console.log("Site Footer: ", siteFooterRes)
    return {
        siteFooter: siteFooterRes
    }
};


export const actions: Actions = {

    updateFooter: async ({ locals, request, cookies }) => {
        const { text, social_json } = Object.fromEntries(await request.formData())

        console.log("Form Data", JSON.stringify(social_json))

        const updateWithObj = {} as {
            text?: string,
            social_json?: string
        }

        if (text) updateWithObj["text"] = text as string;
        if (social_json) updateWithObj["social_json"] = social_json as string;

        let message = "Updated Site Footer";
        const updatedSiteFooter = await fetch(BackendApiEndpoints.USER_SITE_FOOTER, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "JWT": parseTokenFromCookie(cookies)
            },
            body: JSON.stringify(updateWithObj)

        }).then(res => res.json()).catch(err => {
            message = "Failed to update site footer"
        });

        return { message }

    }

};