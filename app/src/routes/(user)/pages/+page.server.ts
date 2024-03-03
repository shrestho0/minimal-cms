import type { SinglePage } from "@/types/pages-and-stuff";
import type { Actions, PageServerLoad } from "./$types";
import { dummyPages } from "@/dev/dummyPages";
import { fail } from "@sveltejs/kit";

export const load: PageServerLoad = async ({ locals, url }) => {
    let page = Number.parseInt(url.searchParams.get('page') || '1')
    let status = url.searchParams.get('status')
    let limit = Number.parseInt(url.searchParams.get('limit') || '5')
    let sort = url.searchParams.get('sort') || '-created'
    let q = url.searchParams.get('q')?.trim() || ''




    let filter = '';
    if (q) {
        q = q.toLowerCase()
        filter = `title~"${q}" || content~"${q}" || slug~"${q}" `
    }


    if (status && status != 'all') {
        filter += `${filter ? '&&' : ''} status = "${status}"`
    }



    const pages = dummyPages

    return {
        page: page,
        totalItems: pages.length,
        items: pages,
        totalPages: Math.ceil(pages.length / limit)

    }
    // return structuredClone(pages)


};

export const actions: Actions = {
    deletePage: async ({ locals, request }) => {

        const { pageId } = Object.fromEntries(await request.formData())

        // check if user owns this page
        // if yes, delete page and return success
        // else any other case return success: false

        const deletedPage = dummyPages.find(page => page.id === pageId)

        if (!deletedPage) {
            return fail(404, {
                message: "Page not found. Failed to delete page."
            })
        }

        return {
            success: true,
            message: "Page deleted successfully"
        }
    },
};