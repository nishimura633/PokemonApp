package com.example.pokemonapp.api;

import java.util.concurrent.CompletableFuture;

public class ApiClient {
    private final HttpService httpService;

    public ApiClient(HttpService httpService) {
        this.httpService = httpService;
    }

    public CompletableFuture<HttpResponse> fetchData(String endpoint) {
        return CompletableFuture.supplyAsync(() -> httpService.get(endpoint));
    }
}
