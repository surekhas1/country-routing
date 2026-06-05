package com.example.service;

import com.example.exception.RouteNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/*
 * Author: Surekha Shinde
 * Description: Unit tests for RouteService to verify the correctness of the route finding logic, including edge cases such as non-existent countries and no available routes
 */
@ExtendWith(MockitoExtension.class)
public class RouteServiceTest  {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private RouteService routeService;

    /*
     * Test case to verify that a valid route is found between two countries when a path exists in the graph
     */
    @Test
    void shouldFindRoute() {

        Map<String, List<String>> graph = Map.of(
                "CZE", List.of("AUT"),
                "AUT", List.of("CZE", "ITA"),
                "ITA", List.of("AUT")
        );

        when(countryService.getBordersMap()).thenReturn(graph);

        List<String> route = routeService.findRoute("CZE", "ITA");

        assertEquals(
                List.of("CZE", "AUT", "ITA"),
                route
        );
    }

    /*
	 * Test case to verify that a RouteNotFoundException is thrown when either the origin or destination country does not exist in the graph, ensuring that the service correctly handles invalid input
	 */
    @Test
    void shouldThrowWhenCountryDoesNotExist() {

        Map<String, List<String>> graph = Map.of(
                "CZE", List.of("AUT"),
                "AUT", List.of("CZE")
        );

        when(countryService.getBordersMap()).thenReturn(graph);

        RouteNotFoundException ex = assertThrows(
                RouteNotFoundException.class,
                () -> routeService.findRoute("XYZ", "ITA")
        );

        assertEquals("Country not found", ex.getMessage());
    }
    
    @Test
    void shouldThrowWhenNoLandRouteExists() {

        Map<String, List<String>> graph = Map.of(
                "CZE", List.of("AUT"),
                "AUT", List.of("CZE"),
                "ITA", List.of()
        );

        when(countryService.getBordersMap()).thenReturn(graph);

        RouteNotFoundException ex = assertThrows(
                RouteNotFoundException.class,
                () -> routeService.findRoute("CZE", "ITA")
        );

        assertEquals("Land route not found", ex.getMessage());
    }

    /*
	 * Test case to verify that when the origin and destination are the same, the service returns
	 * a route consisting of just that single country, ensuring that the service correctly handles this edge case without unnecessary processing
	 */
    @Test
    void shouldReturnSingleCountryWhenOriginEqualsDestination() {

        Map<String, List<String>> graph = Map.of("CZE", List.of("AUT")
        );

        when(countryService.getBordersMap()).thenReturn(graph);

        List<String> route = routeService.findRoute("CZE", "CZE");

        assertEquals(List.of("CZE"), route);
    }
}
