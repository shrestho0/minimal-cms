import type { SinglePage } from "@/types/entity";
import type { Actions, PageServerLoad } from "./$types";
import { dummyPages } from "@/dev/dummyPages";
import { fail } from "@sveltejs/kit";
import { BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";

export const load: PageServerLoad = async ({ locals, url, fetch, cookies }) => {
    let page = Number.parseInt(url.searchParams.get('page') || '1')
    let status = url.searchParams.get('status') || 'all'
    let limit = Number.parseInt(url.searchParams.get('limit') || '5')
    let q = url.searchParams.get('q')?.trim() || ''


    const pagesRes = await fetch(BackendApiEndpoints.USER_PAGES + `?page=${page}&limit=${limit}&q=${q}&status=${status}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            "JWT": parseTokenFromCookie(cookies)
        }
    })

    const pages = await pagesRes.json()
    console.log('pages', pages)
    pages.totalPages = Math.ceil(pages.totalItems / limit)
    return pages

    // return structuredClone(pages)


};

export const actions: Actions = {
    deletePage: async ({ locals, request, fetch, cookies }) => {

        const { id } = Object.fromEntries(await request.formData())

        console.log('pageId', id)

        // Simply delete
        const deletedPageRes = await fetch(BackendApiEndpoints.USER_PAGES, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                "JWT": parseTokenFromCookie(cookies)
            },
            body: JSON.stringify({ id })
        })
        const deletedPage = await deletedPageRes.json() as {
            success: boolean,
            message: string
        }

        console.log('deletedPage', deletedPage)

        if (!deletedPage.success) {
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