import { redirect, type Actions, fail } from "@sveltejs/kit";
import { ErrorMessages } from "@/utils/messages";

export const actions: Actions = {
    changePassword: async ({ locals, request }) => {
        const data = Object.fromEntries(await request.formData()) as any;
        const { oldPassword, password, passwordConfirm } = data

        try {

            // TODO: Update password
            // In Backend api, 
            // Validate old password 
            // Update password
            // Or Return error message
            const user = null


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