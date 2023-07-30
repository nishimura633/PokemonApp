package com.example.pokemonapp.factory;

import com.example.pokemonapp.api.ApiClient;
import com.example.pokemonapp.api.HttpService;

public class ApiFactory {
    private static ApiClient apiClient;

    public static ApiClient getApiClient() {
        if (apiClient == null) {
            HttpService httpService = new HttpService();
            apiClient = new ApiClient(httpService);
        }
        return apiClient;
    }
}
