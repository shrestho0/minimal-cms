import type { PageStatus, SinglePage } from "./pages-and-stuff";

export type EditPageLoadData = {
    pageExists: boolean;
    message: string;
    page: SinglePage
}

export type NewOrEditPageData = {
    status: PageStatus;
    pageId: string;
    updating?: boolean;
    title: {
        value: string;
        error: string;
    };
    slug: {
        value: string;
        error: string;
    };
    content: {
        value: string;
        error: string;
    };
};

export type RequestNewPage = {
    title: string;
    slug: string;
    content: string;
    status: PageStatus;
}

export type ResponseNewOrUpdatePage = {
    success: boolean;
    redirect_to?: string;
    message: string;
    errors: {
        title: string;
        slug: string;
        content: string;
    };
}