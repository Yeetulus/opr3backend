package com.osu.opr3.opr3project.exception;

import lombok.Getter;

@Getter
public class ItemNotFoundException extends RuntimeException {

    private static final String messageId = "Item with ID %s was not found";
    private static final String messageCategoryId = "Item with ID %s was not found in category with ID %s";

    public ItemNotFoundException(long id){
        super(String.format(messageId, id));
    }
    public ItemNotFoundException(long id, long categoryId){
        super(String.format(messageCategoryId, id, categoryId));
    }
}
