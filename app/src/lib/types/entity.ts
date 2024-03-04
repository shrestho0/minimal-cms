export enum UserRole {
    ADMIN = "ADMIN",
    USER = "USER",
}


export interface BaseUser {
    id: string;
    username: string;
    email: string;
    name: string;
    password?: string;
    passwordConfirm?: string;
    created: string;
    updated: string;
}

export interface User extends BaseUser {
    role: UserRole.USER;
}

export interface Admin extends BaseUser {
    role: UserRole.ADMIN;
}


export type PageStatus = 'published' | 'draft' | 'banned';
export type SinglePage = {
    id: string;
    title: string;
    slug: string;
    content: string;
    user: string;
    status: PageStatus;
    created: string;
    updated: string;
    expand?: {
        user?: User
    }
}

export interface Profile {
    id: string;
    title: string;
    content: string;

    user: User;

    created: string;
    updated: string;
}