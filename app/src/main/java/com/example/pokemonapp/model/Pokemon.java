package com.example.pokemonapp.model;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private final int order;
    private final String name;
    private final String imageUrl;

    public Pokemon(int order, String name, String imageUrl) {
        this.order = order;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() { return imageUrl; }
}