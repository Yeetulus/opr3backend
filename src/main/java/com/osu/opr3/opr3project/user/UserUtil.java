package com.osu.opr3.opr3project.user;

import com.osu.opr3.opr3project.user.User;

import java.io.IOException;

public class UserUtil {

    public static User castToUser(Object userObject) throws IOException {
        if (userObject instanceof User) {
            return (User) userObject;
        }
        throw new IOException("Cannot parse User from request");
    }
}
