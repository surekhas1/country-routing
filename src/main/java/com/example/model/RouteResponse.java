package com.example.model;

import java.util.List;

/*
 * Author: Surekha Shinde
 * Description: Model class to represent the response containing the route between two countries, used for serializing API responses
 */
public class RouteResponse {
    private List<String> route;

    public RouteResponse(List<String> route) {
        this.route = route;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }
}
