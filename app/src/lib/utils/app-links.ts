import { PUBLIC_BACKEND_HOST } from "$env/static/public";


export const AppLinks = {
    HOME: "/",
    ADMIN_ROUTER: "/_",
    ADMIN_DASHBOARD: "/_/dashboard",


    LOGIN: "/login",
    USER_REGISTER: "/register",
    USER_DASHBOARD: "/dashboard",
    USER_PAGES: "/pages",
    USER_PROFILE_PAGE: "/profile",
}

const _BackendApiEndpoints = {
    REGISTER: "/auth/register",
    LOGIN: "/auth/tokens",
    TOKEN_REFRESH: "/auth/refresh", // requires refresh token as JWT
    LOGOUT: "/auth/logout", // requires refresh token as JWT

    USER_PAGES: "/user/pages", // CRUD, requires ACCESS_TOKEN as JWT
    USER_PROFILE: "/user/profile", // GET, PATCH, requires ACCESS_TOKEN as JWT
    USER_SITE_FOOTER: "/user/site-footer", // GET, PATCH, requires ACCESS_TOKEN as JWT
    USER_SITE_HEADER: "/user/site-header", // GET, PATCH, requires ACCESS_TOKEN as JWT
    USER_SITE_STYLE: "/user/site-style", // GET, PATCH, requires ACCESS_TOKEN as JWT
    USER_CHANGE_PASSWORD: "/user/change-password", // PATCH, requires ACCESS_TOKEN as JWT
    USER_DASHBOARD: "/user/dashboard", // GET, requires ACCESS_TOKEN as JWT

    PUBLIC_DESIGN: "/design", // GET with /design/:username
    PUBLIC_STUFF: "/stuff", // GET with /stuff/:username
    PUBLIC_PAGE: "/page", // GET with /page/:username/:slug
    PUBLIC_PROFILE: "/profile", // GET with /profile/:username

    ADMIN_DASHBOARD: "/admin/dashboard", // GET, requires ADMIN_ACCESS_TOKEN as JWT
    ADMIN_CHANGE_PASSWORD: "/admin/change-password", // PATCH, requires ACCESS_TOKEN as JWT
    ADMIN_USERS: "/admin/users", // POST, requires ADMIN_ACCESS_TOKEN as JWT    
    ADMIN_ADMINS: "/admin/admins", // POST, requires ADMIN_ACCESS_TOKEN as JWT  

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
