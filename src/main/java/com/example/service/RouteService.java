package com.example.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.exception.RouteNotFoundException;

/*
 * Author: Surekha Shinde
 * Description: Service class to handle the business logic for finding routes between countries, using a breadth-first search algorithm to find the shortest path in a graph representation of countries and their borders.
 */
@Service
public class RouteService {

    private final CountryService countryService;

    public RouteService(CountryService countryService) {
        this.countryService = countryService;
    }

    /*
     * Method to find the land route between two countries using breadth-first search (BFS)
     * @param origin The starting country code
     * @param destination The target country code
     * @return A list of country codes representing the route from origin to destination
     * @throws RouteNotFoundException if either the origin or destination country does not exist, or if no land route can be found between them
     */
    public List<String> findRoute( String origin, String destination) {

        Map<String, List<String>> graph = countryService.getBordersMap();

        if (!graph.containsKey(origin) || !graph.containsKey(destination)) { 
            throw new RouteNotFoundException("Country not found");
        }
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();
        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(destination)) {
                return buildPath(parent, origin, destination);
            }

            for (String neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                	visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        throw new RouteNotFoundException("Land route not found");
    }

    /*
     * Helper method to reconstruct the path from the origin to the destination using the parent map built
     * during the breadth-first search. It backtracks from the destination to the origin and builds the path in reverse order, ensuring that the final path is correctly ordered from origin to destination.
     * @param parent A map that stores the parent of each visited node during the BFS, used to reconstruct the path
     * @param origin and destination The starting and target country code
     * @return A list of country codes representing the path from origin to destination
     * @throws RouteNotFoundException if the reconstructed path does not start with the origin, indicating that no valid route was found
     */
    private List<String> buildPath( Map<String, String> parent, String origin, String destination) {

        LinkedList<String> path = new LinkedList<>();
        String current = destination;

        while (current != null) {
            path.addFirst(current);
            current = parent.get(current);
        }

        if (!path.getFirst().equals(origin)) {
            throw new RouteNotFoundException("Route not found");
        }

        return path;
    }
}
