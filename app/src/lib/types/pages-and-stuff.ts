import type { User } from "./users";

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