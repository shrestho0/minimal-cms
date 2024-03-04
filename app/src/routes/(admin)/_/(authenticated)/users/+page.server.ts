import type { SinglePage } from "@/types/entity";
import type { Actions, PageServerLoad } from "./$types";
import type { User } from "@/types/entity";
import { fail } from "@sveltejs/kit";
export const load: PageServerLoad = async ({ locals, url }) => {
    let page = Number.parseInt(url.searchParams.get('page') || '1')
    let limit = Number.parseInt(url.searchParams.get('limit') || '5')

    let q = url.searchParams.get('qu') || ''

    const users = null

    return {
        // 
    }


};

export const actions: Actions = {
    deleteUser: async ({ request, locals }) => {
        const formData = await request.formData()
        let error = false;


        // TODO: Delete user

        if (error) return fail(500, {
            message: 'Failed to delete user'
        })

        return {
            message: 'User deleted successfully'
        }
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