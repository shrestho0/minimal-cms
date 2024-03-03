import { json, type RequestHandler } from "@sveltejs/kit";

export const POST: RequestHandler = async ({ locals, request }) => {
    const responseObj = {
        success: false,
        message: "Failed to create web page!",
        redirect_to: "",
        errors: {
            title: "",
            slug: "",
            content: "",

        }
    } as {
        success: boolean;
        redirect_to: string;
        message: string;
        errors?: {
            title: string;
            slug: string;
            content: string;
        };
    }
    const pageData = await request.json();
    console.log(pageData);
    return json(responseObj);
};