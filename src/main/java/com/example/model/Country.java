package com.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Author: Surekha Shinde
 * Description: Model class to represent a country and its borders, used for deserializing API responses
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    @JsonProperty("cca3")
    private String cca3;

    private List<String> borders;

    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }
}
