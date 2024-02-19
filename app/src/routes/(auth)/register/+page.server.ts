import { redirect, type Actions, fail } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { ErrorMessages } from "@/utils/messages";
import { validRegex } from "@/utils/validations";
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
        const data = Object.fromEntries(await request.formData()) as {
            email: string,
            username: string,
            name: string,
            password: string,
            passwordConfirm: string,
        } as {
            [key: string]: string;
        };

        Object.keys(data).forEach((key: string) => {
            if (data[key]) {
                data[key] = data[key].trim();
            }
        }); // DEBUG



        const { email, username, name, password, passwordConfirm } = data;

        // // @request.headers.x_site_key="01HPV38C41AYV5KA6BGJJEWHBK"
        console.log(email, password); // DEBUG, this is illigal :3 :D

        // Trim all



        // if (!email || !password) return fail(400, { message: ErrorMessages.ALL_FIELDS_REQUIRED });
        if (!email || !username || !name || !password || !passwordConfirm) return fail(400, { message: ErrorMessages.ALL_FIELDS_REQUIRED });

        /** Stuff Validations */
        const fieldErrors = {
            email: "",
            username: "",
            name: "",
            password: "",
            passwordConfirm: "",
        }

        validRegex.username.test(username) || (fieldErrors.username = ErrorMessages.USERNAME_INVALID);
        validRegex.name.test(name) || (fieldErrors.name = ErrorMessages.NAME_INVALID);
        validRegex.email.test(email) || (fieldErrors.email = ErrorMessages.EMAIL_INVALID);
        validRegex.password.test(password) || (fieldErrors.password = ErrorMessages.PASSWORD_INVALID);
        password === passwordConfirm || (fieldErrors.passwordConfirm = ErrorMessages.PASSWORD_CONFIRM_INVALID);

        if (fieldErrors.email || fieldErrors.username || fieldErrors.name || fieldErrors.password || fieldErrors.passwordConfirm) {
            console.log(fieldErrors); // DEBUG
            return fail(400, { fieldErrors });
        }




        // Validations from server

        // Check if email exists in database
        // Check if username exists in database

        const userWithEmail = null; // should be empty to create user
        const userWithUsername = null; // should be empty to create user


        if (userWithEmail) {
            fieldErrors.email = ErrorMessages.EMAIL_EXISTS;
        }
        if (userWithUsername) {
            fieldErrors.username = ErrorMessages.USERNAME_EXISTS;
        }

        if (fieldErrors.email || fieldErrors.username) {
            console.log(fieldErrors); // DEBUG
            return fail(400, { fieldErrors });
        }

        // Create user
        try {
            const userX = null; // There will be newly created user;
            throw new Error("Not implemented");

        } catch (e: any) {
            return fail(400, { message: e.message });
        }
        const newUser = null // Create user and return user object 

        if (!newUser) {
            return fail(400, { message: "Failed to create user" });
        }

        return redirect(302, AppLinks.USER_LOGIN);




    }
};