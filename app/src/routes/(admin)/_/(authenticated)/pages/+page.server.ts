import type { Actions, PageServerLoad } from "./$types";
import type { User } from "@/types/entity";
import { fail } from "@sveltejs/kit";
import { BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";
import { validRegex } from "@/utils/validations";
export const load: PageServerLoad = async ({ locals, url, fetch, cookies }) => {
    let page = Number.parseInt(url.searchParams.get('page') || '1')
    let limit = Number.parseInt(url.searchParams.get('limit') || '5')

    let q = url.searchParams.get('q') || ''
    let qu = url.searchParams.get('qu') || ''
    let status = url.searchParams.get('status') || 'all'

    const users = await fetch(BackendApiEndpoints.ADMIN_PAGES + `?qu=${qu}&q=${q}&limit=${limit}&page=${page}&status=${status}`, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            "JWT": parseTokenFromCookie(cookies)
        }
    }).then(res => res.json()).catch(err => {
        console.error(err)
        return {
            page: page,
            totalPages: 0,
            totalItems: 0,
            perPage: limit,
            items: []

        }
    })

    // console.log(JSON.stringify(users))
    return users;


};


export const actions: Actions = {
    deletePage: async ({ locals, request, fetch, cookies }) => {

        const { pageId } = Object.fromEntries(await request.formData())

        // Todo: Delete Page
        const pageDeleted = await fetch(BackendApiEndpoints.ADMIN_PAGES + `/${pageId}`, {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json",
                "JWT": parseTokenFromCookie(cookies)
            },

        })

        if (!pageDeleted) {
            return fail(403, {
                message: "Failed to delete page."
            })
        }

        return {
            success: true,
            message: "Page deleted successfully"
        }
    },
    updateStatus: async ({ locals, request, cookies, fetch }) => {
        const { pageId, status } = Object.fromEntries(await request.formData())

        // TODO: Update Page Status
        const pageUpdated = await fetch(BackendApiEndpoints.ADMIN_PAGES + `/${pageId}`, {
            method: 'PATCH',
            headers: {
                "Content-Type": "application/json",
                "JWT": parseTokenFromCookie(cookies)
            },
            body: JSON.stringify({
                status: status
            })
        })

        if (!pageUpdated) {
            return fail(403, {
                message: "Failed to ban/unban page."
            })
        }

        return {
            success: true,
            message: `Page ${status == 'banned' ? 'banned' : 'unbanned'} successfully`
        }
    }
};