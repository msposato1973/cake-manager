package com.waracle.cakemgr.api;

public final class ApiPaths {

    private ApiPaths() {
        // private constructor to prevent instantiation
    }

    public static final String BASE = "/api/";
    public static final String CAKES = BASE + "/cakes";
    public static final String CAKE_BY_ID = CAKES + "/{id}";
}
