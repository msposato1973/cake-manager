package com.waracle.cakemgr.utility;

import com.fasterxml.jackson.core.*;
import com.waracle.cakemgr.model.CakeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CakeImporterReader {

     private static final Logger LOGGER = LoggerFactory.getLogger(CakeImporterReader.class);
    @Value("${cake.json.url:}")
    private String cakeJsonUrl;

     public List<CakeEntity> importCakes() {
        System.out.println("⬇️  Downloading cake JSON...");
        List<CakeEntity> listCakeEntity = new ArrayList<>();
        if (cakeJsonUrl == null || cakeJsonUrl.isEmpty()) {
            throw new IllegalStateException("Cake JSON URL property is not set");
        }
        try (InputStream inputStream = new URL(cakeJsonUrl).openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String json = reader.lines().collect(Collectors.joining());
            System.out.println("Parsing cake JSON...");

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
                            case "employeeId" -> {
                                if (parser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) {
                                    cake.setId(parser.getIntValue());
                                } else {
                                    throw new IllegalStateException("Expected employeeId to be an integer");
                                }
                            }
                            // Ignore unknown fields
                        }
                    }
                    listCakeEntity.add(cake);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error during import: " + ex.getMessage());
            ex.printStackTrace();
        }

        return listCakeEntity;
    }


}
