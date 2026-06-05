package com.example.controller;

import com.example.exception.RouteNotFoundException;
import com.example.model.RouteResponse;
import com.example.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/* 
 * Author: Surekha Shinde
 * Description: Controller class to handle routing-related HTTP requests
*/
@RestController
@RestControllerAdvice
@RequestMapping("/routing")
public class RoutingController {

   private final RouteService routeService;

    public RoutingController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/{origin}/{destination}")
    public RouteResponse getRoute(@PathVariable String origin, @PathVariable String destination) {

        return new RouteResponse(routeService.findRoute(origin.toUpperCase(), destination.toUpperCase()));
    }

    @ExceptionHandler(RouteNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(RouteNotFoundException e) {
        return e.getMessage();
    }
}
