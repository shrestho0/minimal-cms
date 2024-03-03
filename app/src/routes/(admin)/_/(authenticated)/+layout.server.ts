
import { AppLinks } from "@/utils/app-links";
import type { LayoutServerLoad } from "./$types";
import { redirect } from "@sveltejs/kit";


export const load: LayoutServerLoad = async ({ locals, url }) => {
    // Only authenticated admins are allowed here

    //TODO: Uncomment this
    if (!locals.admin) {
        return redirect(302, AppLinks.LOGIN)
    }
};