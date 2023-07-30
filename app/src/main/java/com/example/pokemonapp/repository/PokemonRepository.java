package com.example.pokemonapp.repository;

import com.example.pokemonapp.api.ResponseCode;
import com.example.pokemonapp.factory.ApiFactory;
import com.example.pokemonapp.api.ApiClient;
import com.example.pokemonapp.model.Pokemon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PokemonRepository {
    ApiClient apiClient;

    public PokemonRepository() {
        this.apiClient = ApiFactory.getApiClient();
    }

    public CompletableFuture<Pokemon> getPokemon(int id) {
        String endpoint = "pokemon/" + id;
        return apiClient.fetchData(endpoint).thenApply(httpResponse -> {
            String name = "";
            String imageUrl = "";

           try {
               if (httpResponse.getStatusCode() != ResponseCode.HTTP_OK.getCode()) throw new Exception();
               JSONObject jsonObject = new JSONObject(httpResponse.getResponseBody());

               if (jsonObject.has("forms")) {
                   JSONArray forms = jsonObject.getJSONArray("forms");
                   JSONObject firstObject = forms.getJSONObject(0);

                   if (firstObject.has("name")) {
                       name = firstObject.getString("name");
                   }
               }

               if (jsonObject.has("sprites")) {
                   JSONObject sprites = jsonObject.getJSONObject("sprites");

                   if (sprites.has("front_default")) {
                       imageUrl = sprites.getString("front_default");
                   }
               }
               return new Pokemon(id, name, imageUrl);
           } catch (Exception e) {
                throw new RuntimeException();
           }
        });
    }

    public CompletableFuture<List<Pokemon>> getPokemonList() {
        int FIRST_GENERATION_NUMBER = 151;
        List<CompletableFuture<Pokemon>> futures = new ArrayList<>();
        for (int i = 1; i <= FIRST_GENERATION_NUMBER; i++) {
            futures.add(getPokemon(i));
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
    }
}
