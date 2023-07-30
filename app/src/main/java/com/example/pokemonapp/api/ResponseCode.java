package com.example.pokemonapp.api;

public enum ResponseCode {
    HTTP_OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    public int getCode() {
        return code;
    }

    ResponseCode(int code) {
        this.code = code;
    }
}
