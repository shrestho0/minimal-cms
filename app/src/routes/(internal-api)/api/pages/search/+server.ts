
import { BackendApiEndpoints } from '@/utils/app-links';
import { parseTokenFromCookie } from '@/utils/index.server';
import { json } from '@sveltejs/kit';

import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async ({ url, locals, fetch, cookies }) => {
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


            const searchPageRes = await fetch(BackendApiEndpoints.USER_PAGES + "/search?q=" + q, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "JWT": parseTokenFromCookie(cookies)
                }
            }).then(r => r.json()).catch(e => {
                console.error(e)
                return {
                    success: false,
                    message: "Error fetching pages",
                    items: []
                }
            })


            return json({
                success: true,
                query: q,
                results: searchPageRes.items
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

