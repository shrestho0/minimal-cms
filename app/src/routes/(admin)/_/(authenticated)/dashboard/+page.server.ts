import { BackendApiEndpoints } from "@/utils/app-links";
import type { PageServerLoad } from "./$types";
import { parseTokenFromCookie } from "@/utils/index.server";

export const load: PageServerLoad = async ({ locals, fetch, cookies }) => {
    const dashboardRes = await fetch(BackendApiEndpoints.ADMIN_DASHBOARD, {
        method: "GET",
        headers: {
            "JWT": parseTokenFromCookie(cookies),
        }
    }).then(res => res.json()).catch(err => {
        return {
            pageCount: 0,
            userCount: 0,
            todaysPageCount: 0,
            todaysUserCount: 0,
            last5Pages: [],
            last5Users: [],
        }
    });

    return dashboardRes;
};