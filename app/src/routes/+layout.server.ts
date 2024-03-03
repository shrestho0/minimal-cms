import type { LayoutServerLoad } from "./$types";

/**
 * Checks user authentication status and sanitzes common data for frontend, to access from all pages and layouts.
 */
export const load: LayoutServerLoad = async ({ locals }) => {

    return {
        user: locals.user,
        admin: locals.admin
    }


};