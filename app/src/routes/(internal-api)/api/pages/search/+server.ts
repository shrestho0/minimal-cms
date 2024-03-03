
import { json } from '@sveltejs/kit';

import dbTables from '$lib/utils/db-tables';

import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ url, locals }) => {
    const q = url.searchParams.get('q'); // query
    const t = url.searchParams.get('t'); // type
    if (!q) {
        return json({
            success: false,
            message: 'Invalid request - query is required',
            results: []
        }, {
            status: 403
        });

    }

    switch (t) {
        case 'page':

            const pages = await locals.pb.collection(dbTables.pages).getFullList({
                filter: `title ~ "${q}" || slug ~ "${q}"`,
            });

            return json({
                success: true,
                query: q,
                results: structuredClone(pages)
            });
        default:
            return json({
                success: false,
                message: 'Invalid request - valid type is required',
                results: []
            }, {
                status: 403
            });

    }
};

