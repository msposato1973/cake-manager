package com.waracle.cakemgr.utility;

import com.fasterxml.jackson.core.*;
import com.waracle.cakemgr.model.CakeEntity;
import jakarta.servlet.ServletException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CakeImporterReader {

    private static final String CAKE_JSON_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
    public List<CakeEntity> importCakes() {
        System.out.println("⬇️  Downloading cake JSON...");
        List<CakeEntity> listCakeEntity = new ArrayList<>();

        try (InputStream inputStream = new URL(CAKE_JSON_URL).openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String json = reader.lines().collect(Collectors.joining());
            System.out.println("📦 Parsing cake JSON...");

            JsonFactory factory = new JsonFactory();
            try (JsonParser parser = factory.createParser(json)) {

                if (parser.nextToken() != JsonToken.START_ARRAY) {
                    throw new IllegalStateException("Expected START_ARRAY token");
                }

                while (parser.nextToken() == JsonToken.START_OBJECT) {
                    CakeEntity cake = new CakeEntity();

                    while (parser.nextToken() != JsonToken.END_OBJECT) {
                        String fieldName = parser.getCurrentName();
                        parser.nextToken(); // Move to value
                        switch (fieldName) {
                            case "title" -> cake.setTitle(parser.getText());
                            case "desc" -> cake.setDescription(parser.getText());
                            case "image" -> cake.setImage(parser.getText());
                            default -> parser.skipChildren(); // Ignore unknown fields
                        }
                    }

                    listCakeEntity.add(cake);
                }
            }

        } catch (Exception ex) {
            System.err.println("Errore durante l'importazione: " + ex.getMessage());
            ex.printStackTrace();
        }

        return listCakeEntity;
    }


}
