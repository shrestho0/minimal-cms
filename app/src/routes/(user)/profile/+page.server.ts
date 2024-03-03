import { redirect, type Actions } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { AppLinks } from "@/utils/app-links";
import type { EditPageLoadData } from "@/types/load-data";
import type { SinglePage } from "@/types/pages-and-stuff";
import { dummyPages } from "@/dev/dummyPages";

export const load: PageServerLoad = async ({ locals, params }) => {
    if (!locals?.user) {
        redirect(307, AppLinks.LOGIN);
    }

    const resObj = {
        pageExists: false,
        message: "Web page does not exist!",
        page: {}
    } as EditPageLoadData;

    // Find user's profile page
    // let profilePage = await locals.pb.collection(dbTables.pages).getFirstListItem(`slug = "/" && user = "${locals.user.id}"`).catch((err) => {
    //     console.log("Profile page could not be fetched");
    //     return null;
    // });

    let profilePage = dummyPages[0]

    if (!profilePage) {
        console.log("User's profile page doesn't exist ")

    }

    resObj.pageExists = true;
    resObj.page = structuredClone(profilePage) as unknown as SinglePage;

    return resObj

    // Return page content from here
    // with success or failure message


};
