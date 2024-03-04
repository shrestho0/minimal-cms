import { BackendApiEndpoints } from "@/utils/app-links";
import type { PageServerLoad } from "./$types";
import { error } from "@sveltejs/kit";
import { ErrorMessages } from "@/utils/messages";
import macros from "@/utils/macros";

export const load: PageServerLoad = async ({ locals, fetch, params }) => {
    const { username, slug } = params;

    // User's page
    const page = await fetch(BackendApiEndpoints.PUBLIC_PAGE + `/${username}/${slug}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    }).then((res) => res.json()).catch((err) => { return null; });

    console.log("\n\n Page OK \n\n")

    if (!page || !page?.success) {
        return error(404, {
            message: ErrorMessages.PAGE_NOT_FOUND
        });
    }

    // Replace macros here


    // page.page.content = page.page.content.replace(/{{username}}/g, username);

    if (page.page.status == "draft" && locals?.user?.id != page.owner.id) {
        return error(403, {
            message: ErrorMessages.PAGE_NOT_PUBLIC
        });
    }

    if (page.page.status == "banned" && locals?.user?.id != page.owner.id) {
        return error(403, {
            message: ErrorMessages.PAGE_BANNED
        });
    }

    macros.forEach((macro) => {
        page.page.title = page.page.title.replaceAll(macro.macro, page.owner[macro.userKey])
        page.page.content = page.page.content.replaceAll(macro.macro, page.owner[macro.userKey])
    })



    return {
        pageOwner: page.owner,
        pageContent: page.page
    }

};