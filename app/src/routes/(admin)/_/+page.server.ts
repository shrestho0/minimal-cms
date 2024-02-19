import { redirect } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { AppLinks } from "@/utils/app-links";


/**
 * This page routes admins
 * If admin logged in, sends to /_/dashboard
 * If admin not logged in, sends to /_/login
 * If user logged in, sends to /
 */
export const load: PageServerLoad = async ({ locals }) => {
    if (locals.admin) {
        return redirect(302, AppLinks.ADMIN_DASHBOARD);
    }
    if (locals.user) {
        return redirect(302, AppLinks.HOME);
    }

    return redirect(302, AppLinks.ADMIN_LOGIN);

};