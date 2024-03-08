import type { SinglePage } from "@/types/entity";
import type { Actions, PageServerLoad } from "./$types";
import type { User } from "@/types/entity";
import { fail } from "@sveltejs/kit";
import { BackendApiEndpoints } from "@/utils/app-links";
import { parseTokenFromCookie } from "@/utils/index.server";
import { validRegex } from "@/utils/validations";
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
                message: deleteRes?.message || 'Failed to delete user'
            })
        }

        return deleteRes


    },
    updateUser: async ({ request, locals, fetch, cookies }) => {
        const { userId, usernameX, emailX, nameX, passwordX } = Object.fromEntries(await request.formData()) as Record<string, string>
        console.log(userId, usernameX, emailX, nameX, passwordX)

        const errors = {
            username: '',
            email: '',
            name: '',
            password: ''
        }

        const updateData: Partial<User> = {}
        if (usernameX) {
            if (validRegex.username.test(usernameX) === false) {
                errors.username = 'Invalid username'
            }
            updateData.username = usernameX?.trim()

        }
        if (emailX) {
            if (validRegex.email.test(emailX) === false) {
                errors.email = 'Invalid email'
            }
            updateData.email = emailX?.trim()

        }
        if (nameX) {
            if (validRegex.name.test(nameX) === false) {
                errors.name = 'Invalid name'
            }
            updateData.name = nameX?.trim()
        }
        if (passwordX) {
            if (validRegex.password.test(passwordX) === false) {
                errors.password = 'Invalid password'
            }
            updateData.password = passwordX
        }

        if (errors.username || errors.email || errors.name || errors.password) return fail(400, {
            errors
        })

        console.log(updateData)


        if (Object.keys(updateData).length === 0) return fail(400, {
            message: 'No data to update'
        })


        //TODO: Update user
        const updatedUser = await fetch(BackendApiEndpoints.ADMIN_USERS + `/${userId}`, {
            method: 'PATCH',
            headers: {
                "Content-Type": "application/json",
                "JWT": parseTokenFromCookie(cookies)
            },
            body: JSON.stringify(updateData)
        }).then(res => res.json()).catch(err => {
            console.error(err)
            return {
                success: false,
                message: 'Failed to update user'
            }
        })


        if (!updatedUser?.success) return fail(500, {
            message: updatedUser?.message,
            errors: updatedUser?.errors
        })

        return {
            message: 'User updated successfully',
            updatedUser: structuredClone(updatedUser)
        }
    }
};