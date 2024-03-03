
import { fail, redirect } from "@sveltejs/kit";
import type { PageServerLoad, Actions } from "./$types";
import { AppLinks } from "@/utils/app-links";
import { ErrorMessages } from "@/utils/messages";

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

        // setPBSiteKey(locals.pb); // To tell pb that this is from web app

        const adminX = await locals.pb.admins.authWithPassword(email, password).catch((e) => {
            // console.error(e);
            return { error: { message: e.message } };
        });
        if (adminX.error) return fail(400, { message: adminX.error.message });



        // Everthing should be fine here
        // return {
        //     message: "Logged in successfully",
        // }
        return redirect(302, AppLinks.USER_DASHBOARD);




    }
};