export const SuccessMessages = {
    LOGIN_SUCCESS: "Logged in successfully",
    PASSWORD_CHANGE_SUCCESS: "Password changed successfully",
}

export const ErrorMessages = {
    SERVER_ERROR: "Failed to create user. Please ask adminstrator",
    EMAIL_NOT_FOUND: "User with email not found",
    PASSWORD_INCORRECT: "Password is incorrect",
    ALL_FIELDS_REQUIRED: "All fields are required",

    USERNAME_INVALID: "Username is invalid. Must be alphanumeric or valid characters (_ - .)",
    NAME_INVALID: "Name is invalid. Must be alphanumeric",
    EMAIL_INVALID: "Email is invalid",
    PASSWORD_INVALID: "Password is invalid. Must be at least 8 characters long",
    PASSWORD_CONFIRM_INVALID: "Confirm Password must be same as password",
    PASSWORD_MISMATCH: "Passwords do not match",

    EMAIL_EXISTS: "Email already exists",
    USERNAME_EXISTS: "Username already exists",


    DEFAULT_ERROR: "Some error occured!",

    PAGE_NOT_FOUND: "we couldn't find this page.",
    PAGE_NOT_PUBLIC: "This page is not public",
    PAGE_BANNED: "This page has been banned",

    PAGE_TITLE_INVALID: "Invalid Title. Title should be 5-30 characters long",
    PAGE_SLUG_INVALID: "Invalid Slug. Slug should be 3-20 characters long and can contain a-z A-Z 0-9 - .",
    PAGE_CONTENT_INVALID: "Content should be 10-3000 characters long",
}