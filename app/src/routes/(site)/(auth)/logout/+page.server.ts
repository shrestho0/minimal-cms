import { JWT_COOKIE_NAME } from "$env/static/private";
import { redirect } from "@sveltejs/kit";
import type { Actions, PageServerLoad } from "./$types";
import { AppLinks, BackendApiEndpoints } from "@/utils/app-links";

export const load: PageServerLoad = async ({ locals }) => {
    console.log("LOGOUT PAGE SERVER");
};

export const actions: Actions = {
    default: async ({ request, locals, cookies, fetch }) => {

        console.log("LOGOUT PAGE DEFAULT ACTION");


        console.log("LOGOUT SERVER");

        try {

            const refresh = JSON.parse(cookies.get(JWT_COOKIE_NAME) ?? "")?.refresh
            if (!refresh) {
                console.log("NO REFRESH IN COOKIE");
            }

            const logoutUser = await fetch(BackendApiEndpoints.LOGOUT, {
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

    }
};

