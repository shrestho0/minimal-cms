import { redirect, type Actions, fail } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { ErrorMessages } from "@/utils/messages";
import { AppLinks } from "@/utils/app-links";

export const load: PageServerLoad = async ({ locals, parent }) => {
    await parent();
    if (locals.user) {
        return redirect(302, AppLinks.USER_DASHBOARD);
    }

    if (locals.admin) {
        return redirect(302, AppLinks.ADMIN_ROUTER);
    }

};


export const actions: Actions = {
    default: async ({ locals, request }) => {
        const { email, password } = Object.fromEntries(await request.formData()) as {
            email: string;
            password: string;
        };

        // // @request.headers.x_site_key="01HPV38C41AYV5KA6BGJJEWHBK"
        console.log(email, password); // DEBUG, this is illigal :3 :D

        if (!email || !password) return fail(400, { message: ErrorMessages.ALL_FIELDS_REQUIRED });

        const adminX = {
            error: { message: "Not implemented" }
        }

        if (adminX.error) return fail(400, { message: adminX.error.message });



        // Everthing should be fine here
        // return {
        //     message: "Logged in successfully",
        // }
        return redirect(302, AppLinks.USER_DASHBOARD);




    }
};