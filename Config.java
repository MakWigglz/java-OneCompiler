package com.github.MakWigglz.economicIndicators;

public class Config {
    private static Config instance;
    private String alphaVantageApiKey;
    private String fredApiKey;

    private Config() {
        // Load API keys from environment variables or a configuration file
        this.alphaVantageApiKey = System.getenv("ALPHA_VANTAGE_API_KEY");
        this.fredApiKey = System.getenv("FRED_API_KEY");
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public String getAlphaVantageApiKey() {
        return alphaVantageApiKey;
    }

    public String getFredApiKey() {
        return fredApiKey;
    }
}