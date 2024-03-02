import { PUBLIC_BACKEND_HOST } from "$env/static/public";


export const AppLinks = {
    HOME: "/",
    ADMIN_ROUTER: "/_",
    ADMIN_LOGIN: "/_/login",
    ADMIN_DASHBOARD: "/_/dashboard",


    USER_LOGIN: "/login",
    USER_REGISTER: "/register",
    USER_DASHBOARD: "/dashboard",
    USER_PAGES: "/pages",
    USER_PROFILE_PAGE: "/profile",
}

const _BackendApiEndpoints = {
    REGISTER: "/auth/register",
    LOGIN: "/auth/tokens",
    REFRESH_TOKEN: "/auth/refresh",
};


type BackendApiEndpointsType = typeof _BackendApiEndpoints;
type BackendApiEndpointsKeys = keyof BackendApiEndpointsType;

export const BackendApiEndpoints = new Proxy(_BackendApiEndpoints, {
    get: (target: BackendApiEndpointsType, prop: BackendApiEndpointsKeys) => {
        if (!PUBLIC_BACKEND_HOST) throw new Error("Backend Host not set");
        if (!target[prop]) throw new Error("Endpoint not found");
        return PUBLIC_BACKEND_HOST + target[prop];
    },
    set: () => {
        throw new Error("BackendApiEndpoints is a read-only object");
    }
})

export const InternalApiEndpoints = {
    NEW_PAGE: "/api/pages/new",
    EDIT_PAGE: "/api/pages/edit",
    EDIT_PROFILE: "/api/pages/profile",
    SEARCH_PAGE: "/api/pages/search",
    SITE_STYLESHEET: "/api/site-style",
}
