import { AppLinks } from "@/utils/app-links";
import { redirect } from "@sveltejs/kit";
import type { LayoutServerLoad } from "./$types";

export const load: LayoutServerLoad = async ({ locals, url }) => {
    // Only authenticated admins are allowed here
    if (!locals.admin) {
        return redirect(302, AppLinks.ADMIN_LOGIN)
    }
};