import { redirect, type Actions, fail } from "@sveltejs/kit";
import { ErrorMessages } from "@/utils/messages";

export const actions: Actions = {
    changePassword: async ({ locals, request }) => {
        const data = Object.fromEntries(await request.formData());
        const { oldPassword, password, passwordConfirm } = data

        try {

            // Change Password 
            // Return success message


        } catch (err: any) {
            if (err?.data?.data?.oldPassword) {
                return fail(403, {
                    message: err?.data?.data?.oldPassword?.message
                });
            }
            return fail(500, {
                message: ErrorMessages.DEFAULT_ERROR
            })
        }
    }
};