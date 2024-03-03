import type { NewOrEditPageData, ResponseNewOrUpdatePage } from "@/types/load-data";
import type { PageStatus, SinglePage } from "@/types/pages-and-stuff";
import { AppLinks } from "@/utils/app-links";
import dbTables from "@/utils/db-tables";
import { ErrorMessages } from "@/utils/messages";
import { validRegex } from "@/utils/validations";
import { json, redirect, type RequestHandler } from "@sveltejs/kit";

/**
 * Edit Profile Internal Endpoint
 */

export const POST: RequestHandler = async ({ locals, request, url }) => {
    if (!locals.user) {
        return redirect(307, AppLinks.USER_LOGIN)
    }

    const pageData = await request.json() as {
        id: string;
        title: string;
        content: string;
    };

    console.log("Page Data from /edit")

    const responseObj: ResponseNewOrUpdatePage = {
        success: false,
        message: "Some error occured",
        errors: {
            title: "",
            slug: "",
            content: ""
        }
    }

    console.log("Page Data from /edit", pageData, validRegex.pageContent.test(pageData.content))

    if (!validRegex.pageTitle.test(pageData.title)) {
        responseObj.success = false;
        responseObj.errors.title = ErrorMessages.PAGE_TITLE_INVALID
    }
    if (!validRegex.pageContent.test(pageData.content)) {
        responseObj.success = false;
        responseObj.errors.content = ErrorMessages.PAGE_CONTENT_INVALID
    }




    if (responseObj.errors.title || responseObj.errors.content) {
        console.log("Invalid data", responseObj.errors)
        return json(responseObj);
    }

    // const actualPage = await locals.pb.collection(dbTables.pages).getOne(pageData.id).catch((err) => {
    //     return null;
    // });




    // Update page
    const updatedPage = await locals.pb.collection(dbTables.profile).update(pageData.id, {
        title: pageData.title,
        content: pageData.content,
    }).catch((err) => {
        console.log("Error updating profile", err)
        return null;
    });

    if (updatedPage) {
        responseObj.success = true;
        responseObj.message = "Profile updated successfully";
        return json(responseObj);
    }

    return json({ success: false, message: ErrorMessages.DEFAULT_ERROR }, {
        status: 403,
    })
};