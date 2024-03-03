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