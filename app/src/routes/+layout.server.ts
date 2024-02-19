import type { LayoutServerLoad } from "./$types";

/**
 * Checks user authentication status and sanitzes common data for frontend, to access from all pages and layouts.
 */
export const load: LayoutServerLoad = async ({ locals }) => {

    // Debugging
    if (locals.admin) {
        console.log('Admin:', locals.admin);
    } else if (locals.user) {
        console.log('User:', locals.user);
    }

    return {
        user: locals.user,
        admin: locals.admin
    }


};