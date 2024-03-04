
import type { Handle } from "@sveltejs/kit";
import { sequence } from "@sveltejs/kit/hooks";

import { PUBLIC_BACKEND_HOST } from "$env/static/public";
import { JWT_COOKIE_NAME, JWT_SECRET_KEY, JWT_ISSUER, JWT_COOKIE_EXPIRES } from "$env/static/private"

import * as jose from 'jose'
import { UserRole, type User, type Admin, type BaseUser } from "@/types/entity";
import { BackendApiEndpoints } from "@/utils/app-links";
import { parseUserFromJWTVerifyResult } from "@/utils/index.server";


/**
 * Authentication Handler, Event Handler/Interceptor
 * This checks cookie, validates tokens and sets users in locals
 * This checks user types and sets locals.user or locals.admin
 */




export const AuthHandler = (async ({ event, resolve }) => {
    // We'll handle authentication from here

    // get cookies from event
    const cookies = event.cookies.get(JWT_COOKIE_NAME);

    try {
        // check cookie and validate token
        // console.log("COOKIES:", JSON.parse(cookies?.toString() || "null"));
        const cookieObj = JSON.parse(cookies?.toString() || "null")
        if (cookieObj) {
            const accessToken = cookieObj.access;
            const refreshToken = cookieObj.refresh;

            // if access token is invalid, refresh token with refreshToken 
            // console.log("ACCESS TOKEN:", accessToken);
            const secret = new TextEncoder().encode(
                JWT_SECRET_KEY
            )

            try {

                const accessTokenPayload = jose.decodeJwt(accessToken);
                // check if accessToken Expired or not

                if (accessTokenPayload.exp && accessTokenPayload.exp < Date.now() / 1000) {
                    // console.log("ACCESS TOKEN EXPIRED");
                    // Access Token Expired
                    // Proceed to refresh token
                    throw new Error("Access Token Expired");
                }

                const verifiedAcsessToken = await jose.jwtVerify(accessToken, secret, {
                    issuer: JWT_ISSUER
                });

                // We are safe here
                // Set user in locals
                // console.log("VERIFIED TOKEN:", verifiedAcsessToken);

                const { user, admin } = parseUserFromJWTVerifyResult(verifiedAcsessToken);
                if (user) {
                    event.locals.user = user;
                } else if (admin) {
                    event.locals.admin = admin;
                }
            } catch (e) {
                console.log("ACCESS TOKEN INVALID");
                // Proceed to refresh token
                const refreshToken = cookieObj.refresh;
                console.log("REFRESH TOKEN TO REFRESH:", refreshToken);
                const refreshTokenPayload = jose.decodeJwt(refreshToken);
                console.log("REFRESH TOKEN PAYLOAD:", refreshTokenPayload);
                if (refreshTokenPayload.exp && refreshTokenPayload.exp < Date.now() / 1000) {
                    console.log("REFRESH TOKEN EXPIRED");
                    event.cookies.delete(JWT_COOKIE_NAME, {
                        path: "/",
                    });
                }
                // Refresh Token is valid
                // Verify 
                const verifiedRefreshToken = await jose.jwtVerify(refreshToken, secret, {
                    issuer: JWT_ISSUER
                });
                console.log("VERIFIED REFRESH TOKEN:", verifiedRefreshToken);

                // Shob Thik Thatk,
                // Proceed to refresh token from backend
                const refreshX = await event.fetch(BackendApiEndpoints.TOKEN_REFRESH, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "JWT": refreshToken
                    },
                    body: JSON.stringify({ refreshToken })
                });

                const refreshXRes = await refreshX.json();

                if (!refreshXRes.success) {
                    throw new Error("Refresh Token Invalid");
                }

                console.log("REFRESH X RES:", refreshXRes?.success);

                // Set cookies
                event.cookies.set(JWT_COOKIE_NAME, JSON.stringify(refreshXRes.tokens), {
                    maxAge: Number.parseInt(JWT_COOKIE_EXPIRES) / 1000,
                    path: "/",
                    httpOnly: true,
                    sameSite: "lax",
                });

                // console.log("REFRESHED TOKENS:", refreshXRes.tokens);



                const { user, admin } = parseUserFromJWTVerifyResult(verifiedRefreshToken);
                console.log("REFRESHED USER:", user, admin);
                if (user) {
                    event.locals.user = user;
                } else if (admin) {
                    event.locals.admin = admin;
                }



                await resolve(event);

            }


        }

        // if refresh token is invalid, delete cookie        
    } catch (e) {
        // delete cookie
        event.cookies.delete(JWT_COOKIE_NAME, {
            path: "/",
        });
    }

    const response = await resolve(event);
    return response;

}) satisfies Handle;


export const handle: Handle = sequence(AuthHandler);
