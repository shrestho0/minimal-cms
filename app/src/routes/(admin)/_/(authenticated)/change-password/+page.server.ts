import { redirect, type Actions, fail } from "@sveltejs/kit";
import { ErrorMessages } from "@/utils/messages";
import { BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";

export const actions: Actions = {
    changePassword: async ({ locals, request, fetch, cookies }) => {
        const data = Object.fromEntries(await request.formData());
        const { oldPassword, password, passwordConfirm } = data


        const changePasswordRes = await fetch(BackendApiEndpoints.ADMIN_CHANGE_PASSWORD, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "JWT": parseTokenFromCookie(cookies)
            },
            body: JSON.stringify({ oldPassword, password, passwordConfirm })
        }).then(res => res.json()).catch(err => ({ success: false, message: ErrorMessages.SERVER_ERROR }));


        if (!changePasswordRes.success) {
            return fail(400, { message: changePasswordRes.message });
        }

        return changePasswordRes;


    }
};