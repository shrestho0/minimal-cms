

import { redirect, type Actions, fail } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { ErrorMessages } from "@/utils/messages";
import { AppLinks } from "@/utils/app-links";



export const actions: Actions = {
    default: async ({ locals, request }) => {
        const { email, password } = Object.fromEntries(await request.formData()) as {
            email: string;
            password: string;
        };

        // // @request.headers.x_site_key="01HPV38C41AYV5KA6BGJJEWHBK"
        console.log(email, password); // DEBUG, this is illigal :3 :D

        if (!email || !password) return fail(400, { message: ErrorMessages.ALL_FIELDS_REQUIRED });

        // Login Logic Here
        // Call api and set cookies

        try {
            const userX = null;// There will be our logged in user or error
            throw new Error("Not implemented");
        } catch (e: any) {
            return fail(400, { message: e.message });
        }



        // Everthing should be fine here
        // return {
        //     message: "Logged in successfully",
        // }
        return redirect(302, AppLinks.USER_DASHBOARD);




    }
};