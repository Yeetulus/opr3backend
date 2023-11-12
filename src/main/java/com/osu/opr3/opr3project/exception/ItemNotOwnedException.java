package com.osu.opr3.opr3project.exception;

public class ItemNotOwnedException extends RuntimeException{
    public ItemNotOwnedException(String message){
        super(message);
    }
}
