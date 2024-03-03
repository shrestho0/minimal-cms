import { AppLinks } from "@/utils/app-links";
import { json, redirect, type RequestHandler } from "@sveltejs/kit";

export const POST: RequestHandler = async ({ locals, request, url }) => {
    if (!locals.user) {
        return redirect(307, AppLinks.USER_LOGIN)
    }

    return json({ message: "Invalid request" }, {
        status: 403,
    })
};