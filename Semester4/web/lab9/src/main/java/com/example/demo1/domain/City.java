package com.example.demo1.domain;

public class City {
    private final int cityId;
    private final String name;

    public City(int cityId, String name) {
        this.cityId = cityId;
        this.name = name;
    }

    public int getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }
}
