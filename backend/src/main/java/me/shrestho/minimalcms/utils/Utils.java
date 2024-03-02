package me.shrestho.minimalcms.utils;

public class Utils {
    public static String[] requiredRegisterFields = { "name", "username", "email", "password", "passwordConfirm"};

    public static String[] requiredLoginFields = { "email", "password" };

    public static String[] requiredPostFields = { "title", "content", "slug", "status", "user" };

    public static void print(Object[] args){
        if(args == null) {
            System.out.println("Utils.print called without any arguments.");
        }


        String str = "";
        for (int i = 0; i < args.length; i++) {
            str += " " + args[i].toString();
        }
        System.out.println(str);
    }

}
