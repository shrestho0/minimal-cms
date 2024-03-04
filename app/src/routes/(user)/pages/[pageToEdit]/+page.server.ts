import { redirect, type Actions } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { AppLinks, BackendApiEndpoints } from "@/utils/app-links";
import type { EditPageLoadData } from "@/types/load-data";
import type { SinglePage } from "@/types/entity";
import { parseTokenFromCookie } from "@/utils/index.server";

/**
 * Loads the page to be edited
 * Checks if user owns the page
 * If not, show error message
 * Checks if this is user's profile page
 * If yes, redirect to profile page
 */

export const load: PageServerLoad = async ({ locals, params, fetch, cookies }) => {
    if (!locals?.user) {
        return redirect(307, AppLinks.LOGIN);
    }

    const resObj = await fetch(BackendApiEndpoints.USER_PAGES + `/${params.pageToEdit}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "JWT": parseTokenFromCookie(cookies)
        }
    })

    const resJson = await resObj.json()

    if (!resJson.success) {
        return {
            success: false,
            message: resJson.message
        }
    }

    // resObj.success = true;
    // resObj.page = structuredClone(page) as unknown as SinglePage;

    return {
        success: true,
        page: resJson.page as SinglePage
    }
};



//Dummy Data

// if (pageToEdit == "published-page") {
//     // check and return if valid page exists
//     // Fetch page data from database
//     resObj.success = true;
//     resObj.page = {
//         id: "xyz",
//         title: "Page Title",
//         slug: "page-title",
//         content: "Page content",
//         user: locals.user.id,
//         status: "published"
//     }
//     return resObj;
// } else if (pageToEdit == "banned-page") {
//     // check and return if valid page exists
//     // Fetch page data from database
//     resObj.success = true;
//     resObj.page = {
//         id: "abc",
//         title: "Another Page Title",
//         slug: "another-page-title",
//         content: "Another page content",
//         user: locals.user.id,
//         status: "banned"
//     }
//     return resObj;
// } else if (pageToEdit == "draft-page") {
//     // check and return if valid page exists
//     // Fetch page data from database
//     resObj.success = true;
//     resObj.page = {
//         id: "def",
//         title: "Draft Page Title",
//         slug: "draft-page-title",
//         content: "Draft page content",
//         user: locals.user.id,
//         status: "draft"
//     }
//     return resObj;
// }

// return resObj

// Return page content from here
// with success or failure message


