package com.inventory.inventorymanagement.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);
    }

}
