package me.shrestho.minimalcms.utils;

public class Utils {
    public static String[] requiredRegisterFields = { "name", "username", "email", "password", "passwordConfirm",
            "role" };

    public static String[] requiredLoginFields = { "email", "password" };

    public static String[] requiredPostFields = { "title", "content", "slug", "status", "user" };

}
