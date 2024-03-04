import { BackendApiEndpoints } from "@/utils/app-links";
import type { PageServerLoad } from "./$types";
import { parseTokenFromCookie } from "@/utils/index.server";

export const load: PageServerLoad = async ({ locals, fetch, cookies }) => {
    const dashboardRes = await fetch(BackendApiEndpoints.USER_DASHBOARD, {
        method: "GET",
        headers: {
            "JWT": parseTokenFromCookie(cookies),
        }
    }).then(res => res.json()).catch(err => {
        return {
            totalPages: 0,
            publishedPages: 0,
            draftPages: 0,
            bannedPages: 0,
            last5Pages: [],
        }
    });

    return dashboardRes;
};