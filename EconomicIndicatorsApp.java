package com.github.MakWigglz.economicIndicators;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EconomicIndicatorsApp {
    private static final String ALPHA_VANTAGE_API_URL = "https://www.alphavantage.co/query";
    private static final String FRED_API_URL = "https://api.stlouisfed.org/fred/series/observations";

    public static void main(String[] args) {
        try {
            Config config = Config.getInstance();
            
            // Fetch GDP data
            String gdpData = WorldBank.fetchData("NY.GDP.MKTP.CD", "US", 2013, 2023);
            System.out.println("GDP Data: " + gdpData);

            // Fetch other economic indicators
            String unemploymentData = WorldBank.fetchData("SL.UEM.TOTL.ZS", "US", 2013, 2023);
            System.out.println("Unemployment Data: " + unemploymentData);

            String inflationData = WorldBank.fetchData("FP.CPI.TOTL.ZG", "US", 2013, 2023);
            System.out.println("Inflation Data: " + inflationData);

            // Fetch stock market data for major indices
            String spyData = fetchStockData("SPY", config.getAlphaVantageApiKey());
            System.out.println("S&P 500 ETF Data: " + spyData);

            String diaData = fetchStockData("DIA", config.getAlphaVantageApiKey());
            System.out.println("Dow Jones Industrial Average ETF Data: " + diaData);

            // Fetch FRED data
            String gdpGrowthData = fetchFredData("GDP", "2013-01-01", "2023-12-31", config.getFredApiKey());
            System.out.println("GDP Growth Rate Data: " + gdpGrowthData);

            String unemploymentRateData = fetchFredData("UNRATE", "2013-01-01", "2023-12-31", config.getFredApiKey());
            System.out.println("Unemployment Rate Data: " + unemploymentRateData);

            String cpiData = fetchFredData("CPIAUCSL", "2013-01-01", "2023-12-31", config.getFredApiKey());
            System.out.println("Consumer Price Index Data: " + cpiData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fetchStockData(String symbol, String apiKey) throws Exception {
        String urlString = String.format("%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                ALPHA_VANTAGE_API_URL, symbol, apiKey);
        return fetchData(urlString);
    }

    private static String fetchFredData(String seriesId, String startDate, String endDate, String apiKey) throws Exception {
        String urlString = String.format("%s?series_id=%s&observation_start=%s&observation_end=%s&api_key=%s&file_type=json",
                FRED_API_URL, seriesId, startDate, endDate, apiKey);
        return fetchData(urlString);
    }

    private static String fetchData(String urlString) throws Exception {
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