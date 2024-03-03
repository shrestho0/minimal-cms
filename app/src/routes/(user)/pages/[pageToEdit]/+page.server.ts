import { redirect, type Actions } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { AppLinks } from "@/utils/app-links";
import type { EditPageLoadData } from "@/types/load-data";
import type { SinglePage } from "@/types/pages-and-stuff";

/**
 * Loads the page to be edited
 * Checks if user owns the page
 * If not, show error message
 * Checks if this is user's profile page
 * If yes, redirect to profile page
 */

export const load: PageServerLoad = async ({ locals, params }) => {
    if (!locals?.user) {
        return redirect(307, AppLinks.LOGIN);
    }

    const resObj = {
        success: false,
        message: "Web page does not exist!",
        page: {}
    };
    const { pageToEdit } = params;

    // TODO: Get Page
    const page = null


    if (!page) {
        return resObj;
    }

    // if (page.slug == "/") {
    //     return redirect(307, AppLinks.USER_PROFILE_PAGE);
    // }

    resObj.success = true;
    resObj.page = structuredClone(page) as unknown as SinglePage;

    return resObj


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


};
