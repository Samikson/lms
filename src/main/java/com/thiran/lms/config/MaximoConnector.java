package com.thiran.lms.config;

import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaximoConnector {

	private static final String BASE_URL = "https://main.visualinspection.sandbox-trial.suite.maximo.com/api";
    private static final String API_KEY = "QdIv-ZagN-J75z-vW5Q";

    private static final Logger logger = LogManager.getLogger(MaximoConnector.class);
    
    public static void main(String[] args) throws URISyntaxException {
    	 String endpoint = "/datasets";
         URI uri = new URI(BASE_URL + endpoint + "?oslc.pageSize=10");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(uri);
            request.addHeader("x-auth-token", API_KEY);
            request.addHeader("Content-Type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                parseAndDisplayAssetData(jsonResponse);
            }
        } catch (Exception e) {
            logger.info("Exception : ", e);
        }
    }
    
    private static void parseAndDisplayAssetData(String jsonResponse) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse))) {
            JsonArray array = jsonReader.readArray();
            for (JsonObject dataset : array.getValuesAs(JsonObject.class)) {
                String id = dataset.getString("_id", "N/A");
                String name = dataset.getString("name", "N/A");
                String usage = dataset.getString("usage", "N/A");
                String purpose = dataset.getString("purpose", "N/A");
                int totalFileCount = dataset.getInt("total_file_count", 0);
                long createdAt = dataset.getJsonNumber("created_at").longValue();
                long updatedAt = dataset.getJsonNumber("updated_at").longValue();

                System.out.println("Dataset ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Usage: " + usage);
                System.out.println("Purpose: " + purpose);
                System.out.println("Total File Count: " + totalFileCount);
                System.out.println("Created At: " + createdAt);
                System.out.println("Updated At: " + updatedAt);
                System.out.println("-----");
            }
        }
    }
}

