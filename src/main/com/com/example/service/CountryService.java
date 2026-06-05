package com.example.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import com.example.model.Country;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CountryService {

    private static final String URL = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json";

    private final RestTemplate restTemplate;

    private final Map<String, List<String>> bordersMap = new HashMap<>();

    public CountryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void loadCountries() throws Exception {

        String json = restTemplate.getForObject(URL, String.class);

        ObjectMapper mapper = new ObjectMapper();

        Country[] countries = mapper.readValue(json, Country[].class);

        for (Country country : countries) {

            bordersMap.put(
                    country.getCca3(),
                    country.getBorders() == null
                            ? Collections.emptyList()
                            : country.getBorders()
            );
        }
    }

    public Map<String, List<String>> getBordersMap() {
        return bordersMap;
    }
}
