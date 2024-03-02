
import type { Handle } from "@sveltejs/kit";
import { sequence } from "@sveltejs/kit/hooks";

import { PUBLIC_BACKEND_HOST } from "$env/static/public";
import { JWT_COOKIE_NAME } from "$env/static/private"


/**
 * Authentication Handler, Event Handler/Interceptor
 * This checks cookie, validates tokens and sets users in locals
 * This checks user types and sets locals.user or locals.admin
 */




export const AuthHandler = (async ({ event, resolve }) => {
    // We'll handle authentication from here

    // get cookies from event
    const cookies = event.cookies.get(JWT_COOKIE_NAME);
    // console.log("COOKIES:", cookies, event.cookies.getAll());

    // check cookie and validate token
    // if access token is invalid, refresh token
    // if refresh token is invalid, delete cookie

    const response = await resolve(event);
    return response;

}) satisfies Handle;


export const handle: Handle = sequence(AuthHandler);
