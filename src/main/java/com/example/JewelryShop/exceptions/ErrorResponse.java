package com.example.JewelryShop.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Data
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private Integer status_code;
    private String timestamp;
    private List<String> details;
}
