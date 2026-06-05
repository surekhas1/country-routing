package com.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Author: Surekha Shinde
 * Description: Unit test for CountryService to ensure that the borders list is never null, which is crucial for route finding logic
 */
class CountryServiceTest {

    private CountryService countryService;

    @BeforeEach
    void setUp() {
        countryService = new CountryService();
        countryService.loadCountries(); // manually trigger
    }

    /*
     * Test case to verify that the borders list for each country is never null, ensuring that the route finding logic can safely iterate over borders without encountering NullPointerExceptions
     */
    @Test
    void shouldNeverHaveNullBordersList() {

        Map<String, List<String>> map = countryService.getBordersMap();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            assertNotNull(entry.getValue(), "Borders list is null for " + entry.getKey());
        }
    }
}