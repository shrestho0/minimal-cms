import type { LayoutServerLoad } from "./$types";

export const load: LayoutServerLoad = async ({ locals }) => {
    // we don't want logged in users here
    if (locals.user) {
        return { status: 302, redirect: "/" };
    }
};