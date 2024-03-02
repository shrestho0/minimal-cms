import { redirect, type Actions, fail } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { ErrorMessages } from "@/utils/messages";
import { setPBSiteKey } from "@/utils/index.server";
import { AppLinks } from "@/utils/app-links";
import DBTables from "@/utils/db-tables";

export const load: PageServerLoad = async ({ locals, parent }) => {
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
        // console.log(email, password); // DEBUG, this is illigal :3 :D

        if (!email || !password) return fail(400, { message: ErrorMessages.ALL_FIELDS_REQUIRED });
 

        /**
         * Login User
         */
        const loginUserRes = {
            success: false,
            message: "SOME MESSAGE"
        }

        if(!loginUserRes?.success){
            fail(400, {
                message: loginUserRes.message
            })
        }
        
        /**
         * Set Cookie
         * 
        */  
        
        return redirect(302, AppLinks.USER_DASHBOARD);




    }
};