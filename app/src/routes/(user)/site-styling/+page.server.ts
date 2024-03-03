import type { Actions, PageServerLoad } from "./$types";
import { fail } from "@sveltejs/kit";
import type { SiteStyle } from "@/types/customizations";

export const load: PageServerLoad = async ({ locals }) => {
    const siteStyle: SiteStyle = {
        id: "",
        fontFamily: "",
        fontLoadUrl: "",
        styleJson: {}
    }

    return {
        siteStyle: siteStyle
    }
};


export const actions: Actions = {
    changeFont: async ({ locals, request }) => {
        const { fontFamily, styleId, fontLoadUrl } = Object.fromEntries(await request.formData());

        const updatedStyle = null;

        if (!updatedStyle) {
            return fail(400, { message: "Error updating style" })
        }
        return {
            message: "Font Updated",
        }
    },

    changeStyle: async ({ locals, request }) => {
        const { styleId, styleJson } = Object.fromEntries(await request.formData());

        const updatedStyle = null;

        if (!updatedStyle) {
            return fail(400, { message: "Error updating style" })
        }
        return {
            message: "Style Updated",
        }
    }


};