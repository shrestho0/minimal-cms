import { redirect, type Actions } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { AppLinks, BackendApiEndpoints } from "@/utils/app-links";
import type { EditPageLoadData } from "@/types/load-data";
import type { SinglePage } from "@/types/entity";
import { dummyPages } from "@/dev/dummyPages";
import { parseTokenFromCookie } from "@/utils/index.server";

export const load: PageServerLoad = async ({ locals, params, fetch, cookies }) => {
    if (!locals?.user) {
        redirect(307, AppLinks.LOGIN);
    }

    const resObj = {
        pageExists: false,
        message: "Web page does not exist!",
        page: {}
    } as EditPageLoadData;

    // Find user's profile page

    const profilePage = await fetch(BackendApiEndpoints.USER_PROFILE, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "JWT": parseTokenFromCookie(cookies)
        }
    }).then(res => res.json());

    console.log("Profile Page: ", profilePage);



    resObj.pageExists = true;
    resObj.page = structuredClone(profilePage) as unknown as SinglePage;

    return resObj

    // Return page content from here
    // with success or failure message


};
