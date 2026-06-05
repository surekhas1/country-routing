package com.example.service;

import java.io.InputStream;
import java.util.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.model.Country;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

/*
 * Author: Surekha Shinde
 * Description: Service class to load country data from a JSON file and provide access to the borders map, with fixes to ensure immutability and thread safety.
 */
@Service
public class CountryService {

    private Map<String, List<String>> bordersMap = new HashMap<>();

    @PostConstruct
    public void loadCountries() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = new ClassPathResource("countries.json").getInputStream();

            Country[] countries = mapper.readValue(inputStream, Country[].class);

            Map<String, List<String>> map = new HashMap<>();

            for (Country c : countries) {

                String countryCode = c.getCca3();

                List<String> borders = Optional.ofNullable(c.getBorders()).orElse(Collections.emptyList());

                List<String> safeBorders = new ArrayList<>(borders);
                Collections.sort(safeBorders);

                map.put(countryCode, List.copyOf(safeBorders));
            }

            this.bordersMap = map;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load countries.json", e);
        }
    }

    public Map<String, List<String>> getBordersMap() {
        Map<String, List<String>> safeMap = new HashMap<>();
        bordersMap.forEach((k, v) ->
                safeMap.put(k, List.copyOf(v))
        );

        return safeMap;
    }
}