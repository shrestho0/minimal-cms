import { redirect, type Actions } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { AppLinks } from "@/utils/app-links";

export const load: PageServerLoad = async ({ locals }) => {
    if (!locals?.user) {
        redirect(307, AppLinks.LOGIN);
    }
};
