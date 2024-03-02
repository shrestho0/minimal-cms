export type User = {
    id: string;
    username: string;
    email: string;
    name: string;
    password?: string;
    passwordConfirm?: string;
    created: string;
    updated: string;
}

export type Admin = {
    id: string;
    email: string;
}