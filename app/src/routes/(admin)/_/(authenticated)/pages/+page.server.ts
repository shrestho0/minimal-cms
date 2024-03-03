import type { SinglePage } from "@/types/pages-and-stuff";
import type { Actions, PageServerLoad } from "./$types";
import { dummyPages } from "@/dev/dummyPages";
import { fail } from "@sveltejs/kit";

export const load: PageServerLoad = async ({ locals, url }) => {
    let page = Number.parseInt(url.searchParams.get('page') || '1')
    let status = url.searchParams.get('status') || 'all'
    let limit = Number.parseInt(url.searchParams.get('limit') || '5')
    let sort = url.searchParams.get('sort') || '-created'
    let q = url.searchParams.get('q')?.trim()?.toLowerCase() || ''
    let qu = url.searchParams.get('qu')?.trim()?.toLowerCase() || ''




    let filter = '';
    // if (qu) {
    //     filter += `(user.id~"${qu}" || user.username~"${qu}" || user.email~"${qu}" || user.name~"${qu}")`
    // }

    // if (q) {
    //     q = q.toLowerCase()
    //     if (q) filter += " &&"
    //     filter += `( id~"${q}" ||  title~"${q}" || content~"${q}" || slug~"${q}" ) `
    // }


    // if (qu) {
    //     filter += ` ${filter ? ' && ' : ''} ( user.id~"${q}" || user.username~"${q}" || user.email~"${q}" || user.name~"${q}") `
    // }

    if (q && qu) {
        filter = ` (id~"${q}" || title~"${q}" || content~"${q}" || slug~"${q}") && (user.id~"${qu}" || user.username~"${qu}" || user.email~"${qu}" || user.name~"${qu}")`
    } else if (q) {
        filter = ` (id~"${q}" || title~"${q}" || content~"${q}" || slug~"${q}")`
    } else if (qu) {
        filter = ` (user.id~"${qu}" || user.username~"${qu}" || user.email~"${qu}" || user.name~"${qu}")`
    }


    if (status && status != 'all') {
        if (filter) filter += ' &&'
        filter += `(status = "${status}")`
    }

    // if (status && status != 'all') {
    //     filter += `${filter ? '&&' : ''} (status = "${status})"`
    // }


    return {
        totalItem: 100,
        items: dummyPages,
        page,
        limit,
        perPage: limit,
    }


};

export const actions: Actions = {
    deletePage: async ({ locals, request }) => {

        const { pageId } = Object.fromEntries(await request.formData())

        // Todo: Delete Page
        const pageDeleted = null

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
    updateStatus: async ({ locals, request }) => {
        const { pageId, status } = Object.fromEntries(await request.formData())

        // TODO: Update Page Status
        const pageUpdated = null;

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