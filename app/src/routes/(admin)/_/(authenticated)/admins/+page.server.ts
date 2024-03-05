import type { SinglePage } from "@/types/entity";
import type { Actions, PageServerLoad } from "./$types";
import type { User } from "@/types/entity";
import { fail } from "@sveltejs/kit";
import { BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";
export const load: PageServerLoad = async ({ locals, url, fetch, cookies }) => {
    let page = Number.parseInt(url.searchParams.get('page') || '1')
    let limit = Number.parseInt(url.searchParams.get('limit') || '5')

    let qu = url.searchParams.get('qu') || ''

    const users = await fetch(BackendApiEndpoints.ADMIN_ADMINS + `?qu=${qu}&limit=${limit}&page=${page}`, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            "JWT": parseTokenFromCookie(cookies)
        }
    }).then(res => res.json()).catch(err => {
        console.error(err)
        return {
            page: page,
            totalPages: 0,
            totalItems: 0,
            perPage: limit,
            items: []

        }
    })

    // console.log(users)
    return users;


};

export const actions: Actions = {
    deleteUser: async ({ request, locals, fetch, cookies }) => {
        const formData = Object.fromEntries(await request.formData())
        let error = false;

        console.log('Delete Req', formData);

        const deleteRes = await fetch(BackendApiEndpoints.ADMIN_USERS + `/${formData.userId}`, {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json",
                "JWT": parseTokenFromCookie(cookies)
            }
        }).then(res => res.json()).catch(err => {
            error = true
        })

        if (!deleteRes?.success) {
            return fail(400, {
                message: deleteRes?.message || 'Failed to delete admin'
            })
        }

        return deleteRes
    },
    updateUser: async ({ request, locals }) => {
        const { userId, usernameX, emailX, nameX, passwordX } = Object.fromEntries(await request.formData()) as Record<string, string>
        console.log(userId, usernameX, emailX, nameX, passwordX)

        const updateData: Partial<User> = {}
        if (usernameX) updateData.username = usernameX?.trim()
        if (emailX) updateData.email = emailX?.trim()
        if (nameX) updateData.name = nameX?.trim()
        if (passwordX) {
            updateData.password = passwordX
            updateData.passwordConfirm = passwordX
        }

        console.log(updateData)


        if (Object.keys(updateData).length === 0) return fail(400, {
            message: 'No data to update'
        })


        //TODO: Update user
        const updatedUser = null


        if (!updatedUser) return fail(500, {
            message: 'Failed to update user'
        })

        return {
            message: 'User updated successfully',
            updatedUser: structuredClone(updatedUser)
        }
    }
};