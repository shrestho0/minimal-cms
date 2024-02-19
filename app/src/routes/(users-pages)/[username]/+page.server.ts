import type { PageServerLoad } from "./$types";
import { error } from '@sveltejs/kit'

export const load: PageServerLoad = async ({ locals, params }) => {

    console.log("From users-pages/[username]/+page.server.ts | Params:", params)


    const { username } = params;

    // TODO: Remove after design
    if (username === "a") {
        return;
    }

    // Check for user in database
    const someUser = null;

    if (!someUser) {
        return error(404, "Page Not Found")
    }


};