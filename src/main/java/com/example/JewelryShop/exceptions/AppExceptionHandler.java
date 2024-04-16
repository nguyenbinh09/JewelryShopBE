package com.example.JewelryShop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setStatus_code(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimestamp(String.valueOf(LocalDateTime.now().format(format)));
        return errorResponse;
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerErrorException(InternalServerErrorException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setStatus_code(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setTimestamp(String.valueOf(LocalDateTime.now().format(format)));
        return errorResponse;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setStatus_code(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(String.valueOf(LocalDateTime.now().format(format)));
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException  e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setStatus_code(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(String.valueOf(LocalDateTime.now().format(format)));
        List<String> details = new ArrayList<>();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        errorResponse.setDetails(details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
