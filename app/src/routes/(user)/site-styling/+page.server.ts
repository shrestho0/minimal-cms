import type { Actions, PageServerLoad } from "./$types";
import { fail } from "@sveltejs/kit";
import type { SiteStyle } from "@/types/customizations";
import { BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";

export const load: PageServerLoad = async ({ locals, fetch, cookies }) => {
    // const siteStyle: SiteStyle = {
    //     id: "",
    //     fontFamily: "",
    //     fontLoadUrl: "",
    //     styleJson: {}
    // }

    const siteStyle: SiteStyle = await fetch(BackendApiEndpoints.USER_SITE_STYLE, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "JWT": parseTokenFromCookie(cookies)
        }
    }).then(res => res.json()).catch(err => {
        console.error(err);
        return {
            id: "",
            fontFamily: "",
            fontLoadUrl: "",
            styleJson: {}
        }
    });

    console.log(siteStyle);
    siteStyle.styleJson = JSON.parse((siteStyle?.styleJson as unknown as string) || "{}");

    return {
        siteStyle: siteStyle
    }
};


export const actions: Actions = {
    updateStyle: async ({ locals, request, cookies, fetch }) => {
        const { fontFamily, fontLoadUrl, styleJson } = Object.fromEntries(await request.formData());

        const updateWithObj = {} as {
            fontFamily?: string,
            fontLoadUrl?: string,
            styleJson?: string
        }

        if (fontFamily) updateWithObj["fontFamily"] = fontFamily as string;
        if (fontLoadUrl) updateWithObj["fontLoadUrl"] = fontLoadUrl as string;
        if (styleJson) updateWithObj["styleJson"] = styleJson as string;


        let message = "Updated Site Footer";
        const updatedSiteHeader = await fetch(BackendApiEndpoints.USER_SITE_STYLE, {
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
    // changeFont: async ({ locals, request }) => {
    //     const { fontFamily, styleId, fontLoadUrl } = Object.fromEntries(await request.formData());

    //     const updatedStyle = null;

    //     if (!updatedStyle) {
    //         return fail(400, { message: "Error updating style" })
    //     }
    //     return {
    //         message: "Font Updated",
    //     }
    // },

    // changeStyle: async ({ locals, request }) => {
    //     const { styleId, styleJson } = Object.fromEntries(await request.formData());

    //     const updatedStyle = null;

    //     if (!updatedStyle) {
    //         return fail(400, { message: "Error updating style" })
    //     }
    //     return {
    //         message: "Style Updated",
    //     }
    // }


};