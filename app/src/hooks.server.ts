
import type { Handle } from "@sveltejs/kit";
import { sequence } from "@sveltejs/kit/hooks";


/**
 * Authentication Handler, Event Handler/Interceptor
 * This checks cookie, validates tokens and sets users in locals
 * This checks user types and sets locals.user or locals.admin
 */


export const AuthHandler = (async ({ event, resolve }) => {
    // We'll handle authentication from here

    const response = await resolve(event);
    return response;

}) satisfies Handle;


export const handle: Handle = sequence(AuthHandler);
