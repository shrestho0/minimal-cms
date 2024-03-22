import { redirect, type Actions, fail } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { ErrorMessages } from "@/utils/messages";
import { AppLinks, BackendApiEndpoints } from "@/utils/app-links";
import { JWT_COOKIE_EXPIRES, JWT_COOKIE_NAME } from "$env/static/private";

export const load: PageServerLoad = async ({ locals, parent }) => {
    if (locals.user) {
        return redirect(302, AppLinks.USER_DASHBOARD);
    }

    if (locals.admin) {
        return redirect(302, AppLinks.ADMIN_ROUTER);
    }

};


export const actions: Actions = {
    default: async ({ locals, request, fetch, cookies }) => {
        const { email, password } = Object.fromEntries(await request.formData()) as {
            email: string;
            password: string;
        };

        // // @request.headers.x_site_key="01HPV38C41AYV5KA6BGJJEWHBK"
        // console.log(email, password); // DEBUG, this is illigal :3 :D

        if (!email || !password) return fail(400, { message: ErrorMessages.ALL_FIELDS_REQUIRED });


        /**
         * Login User
         */
        const loginUser = await fetch(BackendApiEndpoints.LOGIN, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password })

        })

        const loginUserRes = await loginUser.json();
        console.log("LOGIN USER RES:", loginUserRes);

        if (!loginUserRes?.success) {
            return fail(400, {
                message: loginUserRes.message,
            })
        }


        // Safe here
        // Set cookies
        cookies.set(JWT_COOKIE_NAME, JSON.stringify(loginUserRes.tokens), {
            maxAge: Number.parseInt(JWT_COOKIE_EXPIRES) / 1000,
            path: "/",
            httpOnly: true,
            sameSite: "lax",
        });

        return redirect(302, AppLinks.USER_DASHBOARD);




    }
};