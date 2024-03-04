import { BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie, parseUserFromJWTVerifyResult } from "@/utils/index.server";
import { ErrorMessages } from "@/utils/messages";
import { validRegex } from "@/utils/validations";
import { fail, type Actions } from "@sveltejs/kit";

export const actions: Actions = {
    default: async ({ locals, request, cookies, fetch }) => {
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
        // TODO: Register user

        data.passwordHash = password
        const newUserRes = await fetch(BackendApiEndpoints.ADMIN_NEW_USER, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "JWT": parseTokenFromCookie(cookies)
            },
            body: JSON.stringify(data),
        }).then(res => res.json()).catch(err => {
            return {
                success: false,
                message: "Server error",
            }
        });

        if (!newUserRes.success) {
            return fail(400, { message: newUserRes.message, fieldErrors: newUserRes.errors });
        }

        return newUserRes;




    }
};