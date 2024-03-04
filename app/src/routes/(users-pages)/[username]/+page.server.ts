import { BackendApiEndpoints } from "@/utils/app-links";
import type { PageServerLoad } from "./$types";
import { error } from "@sveltejs/kit";
import { ErrorMessages } from "@/utils/messages";
import macros from "@/utils/macros";

export const load: PageServerLoad = async ({ locals, fetch, params }) => {
    const { username } = params;

    // User's Profile
    const profile = await fetch(BackendApiEndpoints.PUBLIC_PROFILE + `/${username}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    }).then((res) => res.json()).catch((err) => { return null; });

    console.log("\n\n Profile OK \n\n")

    if (!profile || !profile?.success) {
        return error(404, {
            message: ErrorMessages.PAGE_NOT_FOUND
        });
    }

    // Replace macros here


    // profile.profile.content = profile.profile.content.replace(/{{username}}/g, username);


    macros.forEach((macro) => {
        profile.profile.title = profile.profile.title.replaceAll(macro.macro, profile.owner[macro.userKey])
        profile.profile.content = profile.profile.content.replaceAll(macro.macro, profile.owner[macro.userKey])
    })



    return {
        pageOwner: profile.owner,
        pageContent: profile.profile
    }

};