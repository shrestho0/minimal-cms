import { redirect, type Actions } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";


export const load: PageServerLoad = async ({ locals }) => {
    // we don't care if it has a get request
    return redirect(302, "/");

};

export const actions: Actions = {
    default: async ({ locals }) => {
        const goto = locals?.admin ? "/_/" : "/login";
        /**
         * TODOS:
         *  Delete RefreshToken from DB
         *  Clear Cookies 
         */
        return redirect(302, goto);
    }
};