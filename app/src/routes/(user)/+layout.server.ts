import { redirect } from "@sveltejs/kit";
import type { LayoutServerLoad } from "./$types";
import { AppLinks } from "@/utils/app-links";

export const load: LayoutServerLoad = async ({ locals }) => {

    if (locals?.admin) {
        return redirect(307, AppLinks.ADMIN_ROUTER)
    }

    if (!locals.user) {
        return redirect(307, AppLinks.LOGIN)
    }

};