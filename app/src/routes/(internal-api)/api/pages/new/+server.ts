import type { RequestNewPage } from "@/types/load-data";
import { AppLinks, BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";
import { validRegex } from "@/utils/validations";
import { json, redirect, type RequestHandler } from "@sveltejs/kit";

export const POST: RequestHandler = async ({ locals, request, url, fetch, cookies }) => {
    if (!locals.user) {
        return redirect(307, AppLinks.LOGIN)
    }

    const pageData: RequestNewPage = await request.json();
    const { title, slug, content, status } = pageData;


    // Validations
    const errors = { title: "", slug: "", content: "", }
    if (!validRegex.pageSlug.test(slug)) {
        errors.slug = "Invalid Slug. Slug should be 3-20 characters long and can contain a-z A-Z 0-9 - ."
    }
    if (!validRegex.pageTitle.test(title)) {
        errors.title = "Invalid Title. Title should be 5-20 characters long"
    }
    if (content?.length < 5) {
        errors.content = "Content should be 5-1000 characters long"
    }
    if (errors.title || errors.slug || errors.content) {
        return json({ success: false, message: "", errors }, {
            status: 400,
        })
    }

    // Check if page with same slug exists


    /////////////////////////////////
    ////// Safe to create new page
    //         title,
    // slug: `${slug}`,
    // content,
    // status,
    // user: locals.user.id,
    /////////////////////////////////

    const access = parseTokenFromCookie(cookies, "access");

    const newPage = await fetch(BackendApiEndpoints.USER_PAGES, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "JWT": access
        },

        body: JSON.stringify({
            title,
            slug: `${slug}`,
            content,
            status,
            // user: locals.user.id,
        })

    })




    const newPageRes = await newPage.json();
    if (!newPageRes.success) {
        return json(newPageRes);
    }

    console.log("/api/pages/new Page Data Received: ", pageData)

    return json({ success: true, message: "New page created successfully", redirect_to: `/pages/${newPageRes.page.id}` })

};