package com.example.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.exception.RouteNotFoundException;

@Service
public class RouteService {

	private final CountryService countryService;

    public RouteService(CountryService countryService) {
        this.countryService = countryService;
    }

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

        throw new RouteNotFoundException("No land route found");
    }

    private List<String> buildPath( Map<String, String> parent, String origin, String destination) {

        LinkedList<String> path = new LinkedList<>();
        String current = destination;
        
        while (current != null) {
            path.addFirst(current);
            current = parent.get(current);
        }

        if (!path.getFirst().equals(origin)) {
            throw new RouteNotFoundException("No route found");
        }

        return path;
    }
}
