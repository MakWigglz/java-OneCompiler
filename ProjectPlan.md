## To restructure your code to work with the web editor setup we discussed earlier. We'll split the code into multiple files and update the `build.gradle` file to include the necessary dependencies.



## File: /EconomicIndicatorsMain.java**
```java
import com.github.MakWigglz.economicIndicators.EconomicIndicatorsApp;

public class EconomicIndicatorsMain {
    public static void main(String[] args) {
        EconomicIndicatorsApp.main(args);
    }
}
```

2. EconomicIndicatorsApp file:

**File: /com/github/MakWigglz/economicIndicators/EconomicIndicatorsApp.java**
```java
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
            

2. EconomicIndicatorsApp file:

**File: /com/github/MakWigglz/economicIndicators/EconomicIndicatorsApp.java**
```java
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
```

3. Config file:

**File: /com/github/MakWigglz/economicIndicators/Config.java**
```java
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
```

4. WorldBank file:

**File: /com/github/MakWigglz/economicIndicators/WorldBank.java**
```java
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
```

5. Gradle build file:

**File: /build.gradle**
**File: /build.gradle**
```groovy
apply plugin: 'application'
mainClassName = 'EconomicIndicatorsMain'

run { standardInput = System.in }
sourceSets { main { java { srcDir './' } } }

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.json:json:20210307'
}
```

This structure separates the code into multiple files for better organization:

1. The Main Java Class file  is the entry point of the application.
2. `EconomicIndicatorsApp.java` contains the main logic for fetching economic indicators.
3. `Config.java` manages the configuration and API keys.
4. `WorldBank.java` handles fetching data from the World Bank API.
5. `build.gradle` sets up the project and includes the JSON library as a dependency.

To use this setup:

1. Create these files in the web editor with the provided content.
2. Make sure to set the environment variables `ALPHA_VANTAGE_API_KEY` and `FRED_API_KEY` with your actual API keys.
3. Run the project using the "Run" button in the web editor.

This structure allows you to organize your code better, use external dependencies (JSON library in this case), and make API calls to various economic data sources.


## Certainly! We can change the class name from "HelloWorld" to something more descriptive for your project. Let's rename it to "EconomicIndicatorsMain". Here are the changes:

1. Rename the main Java file:



2. Update the Gradle build file to reflect the new main class name:



The rest of the files (EconomicIndicatorsApp.java, Config.java, and WorldBank.java) remain the same as in the previous response.

This change makes the main class name more descriptive and relevant to your project. The EconomicIndicatorsMain class serves as the entry point for your application, and it calls the main method of the EconomicIndicatorsApp class.

To use this updated setup:

1. Rename the "HelloWorld.java" file to "EconomicIndicatorsMain.java" in the web editor.
2. Update the content of "EconomicIndicatorsMain.java" with the provided code.
3. Update the "build.gradle" file with the new content, particularly the `mainClassName` line.
4. Make sure all other files (EconomicIndicatorsApp.java, Config.java, and WorldBank.java) are present and contain the correct code as provided earlier.
5. Run the project using the "Run" button in the web editor.

This structure maintains the organization of your code while using a more appropriate name for the main class.
