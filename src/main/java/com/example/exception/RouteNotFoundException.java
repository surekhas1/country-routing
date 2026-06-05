package com.example.exception;

/*
 * Author: Surekha Shinde
 * Description: Custom exception class to handle cases where a route is not found
 */
@SuppressWarnings("serial")
public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException(String message) {
        super(message);
    }
}
