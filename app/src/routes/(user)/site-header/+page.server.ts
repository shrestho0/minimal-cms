import type { Actions, PageServerLoad } from "./$types";
import { fail, redirect } from "@sveltejs/kit";
import { AppLinks } from "@/utils/app-links";

export const load: PageServerLoad = async ({ locals }) => {

    // TODO: Get site-header
    const siteHeader = {
        id: "",
        site_title: "",
        logo: "",
        nav_links_json: []
    }
    return {
        siteHeader: structuredClone(siteHeader)
    }
};


export const actions: Actions = {
    changeTitle: async ({ locals, request }) => {
        // if (!locals.user) return redirect(307, AppLinks.LOGIN)

        const { siteHeaderId, site_title } = Object.fromEntries(await request.formData()) as {
            siteHeaderId: string,
            site_title: string
        };

        console.log(siteHeaderId, site_title)

        if (!siteHeaderId || !site_title) return fail(403, { message: "Invalid request" })


        const updatedHeader = null

        console.log("Updated Header", updatedHeader)

        if (!updatedHeader) return fail(500, { message: "Site title could not be updated" })

        return {
            message: "Site title updated",
        }

    },

    removeLogo: async ({ locals, request }) => {
        const { siteHeaderId } = Object.fromEntries(await request.formData()) as {
            siteHeaderId: string,
        };


        //TODO: Remove logo
        const updatedHeader = null;

        if (!updatedHeader) return fail(500, { message: "Logo could not be removed" })

        return {
            message: "Logo removed",
        }
    },

    changeLogo: async ({ locals, request }) => {
        const formData = await request.formData()

        console.log(formData)

        const updatedHeader = null;

        if (!updatedHeader) return fail(500, { message: "Logo could not be updated" })

        return {
            message: "Logo updated",
            // logo: updatedHeader?.logo
        }
    },
    changeNavLinks: async ({ locals, request }) => {
        const formData = await request.formData()

        const updatedHeader = null;

        if (!updatedHeader) return fail(500, { message: "Nav links could not be updated" })

        return {
            message: "Nav links updated",
        }

    }

};
