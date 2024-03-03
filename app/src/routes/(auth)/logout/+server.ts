import { redirect, type RequestHandler } from "@sveltejs/kit";
import { JWT_COOKIE_NAME } from "$env/static/private";
import { AppLinks } from "@/utils/app-links";

export const POST: RequestHandler = async ({ locals, cookies, fetch }) => {
    try {

        const refresh = JSON.parse(cookies.get(JWT_COOKIE_NAME) ?? "")?.refresh
        if (!refresh) {
            console.log("NO REFRESH IN COOKIE");
        }

        const logoutUser = await fetch("/auth/logout", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "JWT": refresh
            },
            body: JSON.stringify({ refreshToken: cookies.get(JWT_COOKIE_NAME) })
        });

        const logoutUserRes = await logoutUser.json();
        console.log("LOGOUT USER RES:", logoutUserRes);

        // Clear Cookies
        cookies.delete(JWT_COOKIE_NAME, {
            path: "/",
        });

        // return redirect(302, AppLinks.LOGIN);
    } catch (e) {
        console.error(e);
        // return redirect(302, AppLinks.LOGIN);
    }

    return redirect(302, AppLinks.LOGIN);
};