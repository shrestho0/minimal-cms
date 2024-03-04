import { JWT_COOKIE_NAME } from "$env/static/private";
import type { SiteStyle } from "@/types/customizations";
import { UserRole, type BaseUser, type Admin, type User } from "@/types/entity";


import { fail, type Cookies } from "@sveltejs/kit";
import type { JWTVerifyResult } from "jose";



export function jsonToCSS(json: SiteStyle) {
    let css = "";
    if (json.fontLoadUrl) {
        css += `@import url('${json.fontLoadUrl}');`;
    }
    if (json.fontFamily) {
        css += `body{font-family: ${json.fontFamily};}`;
    }
    for (const tag in json.styleJson) {
        css += `${tag}{`;
        for (const prop in json.styleJson[tag]) {
            css += `${prop}:${json.styleJson[tag][prop]};`;
        }
        css += "}";
    }

    return css;
}

export function parseUserFromJWTVerifyResult(verifiedAcsessToken: JWTVerifyResult) {
    const buser: BaseUser = {
        id: verifiedAcsessToken.payload['id'] as string,
        username: verifiedAcsessToken.payload['username'] as string,
        email: verifiedAcsessToken.payload['email'] as string,
        name: verifiedAcsessToken.payload['name'] as string,
        created: verifiedAcsessToken.payload['created'] as string,
        updated: verifiedAcsessToken.payload['updated'] as string,
    }

    const returnObj: {
        admin?: Admin,
        user?: User,
    } = {}

    // const role = verifiedAcsessToken.payload['role'] == UserRole.USER ? UserRole.USER : verifiedAcsessToken.payload['role'] == UserRole.ADMIN ? UserRole.ADMIN : null;

    if (verifiedAcsessToken.payload['role'] == UserRole.USER) {
        returnObj.user = {
            ...buser,
            role: UserRole.USER
        }
    } else if (verifiedAcsessToken.payload['role'] == UserRole.ADMIN) {
        returnObj.admin = {
            ...buser,
            role: UserRole.ADMIN
        }
    }

    return returnObj;

}

// parseTokenFromCookie
export function parseTokenFromCookie(cookies: Cookies, tokenType: "access" | "refresh" = "access") {
    const tokens = JSON.parse(cookies.get(JWT_COOKIE_NAME) ?? "{}");
    if (!tokens.access || !tokens.refresh) new Error("No tokens in cookie");
    if (tokenType == "access") {
        return tokens.access;
    } else {
        return tokens.refresh;
    }

}