package com.example.JewelryShop.exceptions;

public class InternalServerErrorException extends  RuntimeException{
    public InternalServerErrorException() {
        super("Something went wrong. Try again later.");
    }
    public InternalServerErrorException(String message) {
        super(message);
    }
}
