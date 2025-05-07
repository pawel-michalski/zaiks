package org.sortapp.adapter.datasource;

import org.sortapp.domain.exception.ResourceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class HttpResourceStrategy implements ResourceStrategy {
    private static final String DEFAULT_URL = "https://zaiks.org.pl/dane/lista.json";
    private final String url;
    private final JsonParser jsonParser;

    public HttpResourceStrategy(JsonParser jsonParser) {
        this(DEFAULT_URL, jsonParser);
    }

    public HttpResourceStrategy(String url, JsonParser jsonParser) {
        this.url = url;
        this.jsonParser = jsonParser;
    }

    @Override
    public int[] fetchData() throws ResourceException {
        try {
            URL resourceUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) resourceUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                    if(JsonMockProvider.containsElementsArray(content.toString())) {
                        return jsonParser.parse(content.toString());
                    } else {
                        return jsonParser.parse(JsonMockProvider.getJsonElements());
                    }
                }
            } else {
                throw new ResourceException("HTTP error code: " + responseCode);
            }
        } catch (IOException e) {
            throw new ResourceException("Failed to fetch data from URL: " + url, e);
        }
    }

    @Override
    public boolean canFetch() {
        try {
            URL resourceUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) resourceUrl.openConnection();
            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            return false;
        }
    }
}
