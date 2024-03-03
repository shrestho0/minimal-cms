import type { NewOrEditPageData, ResponseNewOrUpdatePage } from "@/types/load-data";
import type { PageStatus, SinglePage } from "@/types/pages-and-stuff";
import { AppLinks, BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";
import { ErrorMessages } from "@/utils/messages";
import { validRegex } from "@/utils/validations";
import { json, redirect, type RequestHandler } from "@sveltejs/kit";

export const POST: RequestHandler = async ({ locals, request, url, cookies }) => {
    // if (!locals.user) {
    //     return redirect(307, AppLinks.LOGIN)
    // }

    // const pageId = url.searchParams.get("pageId");
    // // console.log("Page ID: ", pageId, url.searchParams)
    // if (!pageId) {
    //     return json({ pageExists: false, message: "Invalid page id. Accepts: ?pageId=[PAGE_ID]" })
    // }


    // const pageData = await request.json() as {
    //     oldSlug: string;
    //     status: PageStatus
    // } & SinglePage;

    // console.log("Page Data from /edit")

    // const responseObj: ResponseNewOrUpdatePage = {
    //     success: false,
    //     message: "Some error occured",
    //     errors: {
    //         title: "",
    //         slug: "",
    //         content: ""
    //     }
    // }

    // // check if page exists and request user is the owner

    // const resObj = await fetch(BackendApiEndpoints.USER_PAGES + `/${pageId}`, {
    //     method: "GET",
    //     headers: {
    //         "Content-Type": "application/json",
    //         "JWT": parseTokenFromCookie(cookies)
    //     }
    // })

    // const resJson = await resObj.json()

    // if (!resJson.success) {
    //     return json({
    //         pageExists: false,
    //         message: resJson.message
    //     })
    // }

    // // check title, slug and content
    // if (!validRegex.pageSlug.test(pageData.slug)) {
    //     responseObj.errors.slug = ErrorMessages.PAGE_SLUG_INVALID
    // }
    // if (!validRegex.pageTitle.test(pageData.title)) {
    //     responseObj.errors.title = ErrorMessages.PAGE_TITLE_INVALID
    // }
    // if (pageData.content?.length < 5) {
    //     responseObj.errors.content = ErrorMessages.PAGE_CONTENT_INVALID
    // }



    // if (pageData.oldSlug != pageData.slug) {
    //     // check if new slug is unique for user
    //     // const slugExists = await locals.pb.collection(dbTables.pages).getFirstListItem(`slug = "${pageData.slug}" && user = "${locals.user.id}"`).catch((err) => {
    //     //     console.log("Error checking if slug exists", err)
    //     //     return null;
    //     // });

    //     const resObj = await fetch(BackendApiEndpoints.USER_PAGES + `/${pageId}`, {
    //         method: "GET",
    //         headers: {
    //             "Content-Type": "application/json",
    //             "JWT": parseTokenFromCookie(cookies)
    //         }
    //     })

    //     const resJson = await resObj.json()

    //     if (!resJson.success) {
    //         return json({
    //             pageExists: false,
    //             message: resJson.message
    //         })
    //     }

    //     const slugExists = resJson.success;

    //     if (slugExists) {
    //         responseObj.errors.slug = "Slug already exists";
    //     }
    //     // else we ignore
    // }

    // console.log("Page Data from /edit", pageData)


    // if (responseObj.errors.slug || responseObj.errors.title || responseObj.errors.content) {
    //     return json(responseObj);
    // }

    // // Update page
    // // const updatedPage = await locals.pb.collection(dbTables.pages).update(pageData.id, {
    // //     title: pageData.title,
    // //     slug: pageData.slug,
    // //     content: pageData.content,
    // //     status: pageData.status
    // // }).catch((err) => {
    // //     console.log("Error updating page", err)
    // //     return null;
    // // });

    // const resObj2 = await fetch(BackendApiEndpoints.USER_PAGES + `/${pageId}`, {
    //     method: "PATCH",
    //     headers: {
    //         "Content-Type": "application/json",
    //         "JWT": parseTokenFromCookie(cookies)
    //     },
    //     body: JSON.stringify(pageData)
    // })

    // const updatedPage = await resObj2.json()

    // if (updatedPage.success) {
    //     responseObj.success = true;
    //     responseObj.message = (updatedPage.slug == "/" ? "Profile" : 'Page') + " updated successfully";
    //     return json(responseObj);
    // }

    // return json({ success: false, message: ErrorMessages.DEFAULT_ERROR }, {
    //     status: 403,
    // })

    return json({ success: false, message: "Not implemented" })
};