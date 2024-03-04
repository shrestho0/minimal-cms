import type { NewOrEditPageData, ResponseNewOrUpdatePage } from "@/types/load-data";
import type { PageStatus, SinglePage } from "@/types/entity";
import { AppLinks, BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";
import { ErrorMessages } from "@/utils/messages";
import { validRegex } from "@/utils/validations";
import { json, redirect, type RequestHandler } from "@sveltejs/kit";

export const POST: RequestHandler = async ({ locals, request, url, cookies }) => {
    if (!locals.user) {
        return redirect(307, AppLinks.LOGIN)
    }

    const editPageData: SinglePage = await request.json();

    // Edit page validation errors

    let editResData = {
        success: false,
        errors: {
            title: "",
            slug: "",
            content: "",
            status: ""
        }
    } as unknown as ResponseNewOrUpdatePage;

    // validate data
    if (!validRegex.pageTitle.test(editPageData.title)) {
        editResData.errors.title = ErrorMessages.PAGE_TITLE_INVALID;
    }

    if (!validRegex.pageContent.test(editPageData.content)) {
        editResData.errors.content = ErrorMessages.PAGE_CONTENT_INVALID + `. Provided: ${editPageData.content.length} characters`;
    }


    // check if errors
    if (editResData.errors.title || editResData.errors.slug || editResData.errors.content) {
        return json(editResData);
    }

    const token = parseTokenFromCookie(cookies);


    const editRes = await fetch(BackendApiEndpoints.USER_PROFILE, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            "JWT": token
        },
        body: JSON.stringify(editPageData)
    })

    editResData = await editRes.json() as ResponseNewOrUpdatePage;
    console.log("Edit Response: ", editResData)

    return json(editResData);
};