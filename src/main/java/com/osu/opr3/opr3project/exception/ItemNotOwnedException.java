package com.osu.opr3.opr3project.exception;

import lombok.Getter;

@Getter
public class ItemNotOwnedException extends RuntimeException{
    private static final String messageIdEmail = "User %s does not own category with ID %s";
    public ItemNotOwnedException(String userEmail, long categoryId){
        super(String.format(messageIdEmail, userEmail, categoryId));
    }

}
