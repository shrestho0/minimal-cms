import type { Actions, PageServerLoad } from "./$types";
import { fail } from "@sveltejs/kit";
import type { SiteFooterType } from "@/types/customizations";

export const load: PageServerLoad = async ({ locals }) => {

    // TODO: Get site-footer 
    const siteFooter: SiteFooterType = {
        id: "",
        text: "",
        social_json: [],
        updated: "",
        created: ""
    }

    return {
        siteFooter: siteFooter
    }
};


export const actions: Actions = {
    changeFooterText: async ({ request, locals }) => {
        const formData = await request.formData();

        // TODO: Update footer text
        const updatedFooter = {
            success: true,
            footer_text: formData.get('footer_text')
        }

        if (!updatedFooter?.success) {
            return fail(400, { message: 'Failed to update footer' })
        }

        return {
            message: 'Footer updated',
        }
    },
    changeSocialLinks: async ({ locals, request }) => {
        const { social_json, siteFooterId } = Object.fromEntries(await request.formData())

        console.log("Form Data", social_json, siteFooterId)


        //TODO: Update social links
        const updatedFooter = {
            success: true,
            social_json: social_json
        }


        if (!updatedFooter.success) return fail(500, { message: "Social links could not be updated" })

        return {
            message: "Social links updated",
        }

    }

};