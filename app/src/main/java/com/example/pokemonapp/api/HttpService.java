package com.example.pokemonapp.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpService {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public HttpResponse get(String endpoint) {
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = null;
        int responseCode = 0;

        try {
            URL url = new URL(BASE_URL + endpoint);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return new HttpResponse(responseCode, response.toString());
    }
}
