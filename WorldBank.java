package com.github.MakWigglz.economicIndicators;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WorldBank {
    private static final String WORLD_BANK_API_URL = "http://api.worldbank.org/v2/country/%s/indicator/%s?date=%d:%d&format=json";

    public static String fetchData(String indicator, String countryCode, int startYear, int endYear) throws Exception {
        String urlString = String.format(WORLD_BANK_API_URL, countryCode, indicator, startYear, endYear);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        return content.toString();
    }
}