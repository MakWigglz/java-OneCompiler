*** EconomicIndicatorsApp.java
``` java code:
/* 
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class EconomicIndicatorsApp {
    private static final String
    WORLD_BANK_API_URL = "http://api.worldbank.org/v2";

    public static void main(String[] args) {
        try {
            // Fetch GDP data
            String gdpData = fetchWorldBankData("NY.GDP.MKTP.CD", "US", 2013, 2023);
            System.out.println("GDP Data: " + gdpData);

            // Fetch other economic indicators
            String unemploymentData = fetchWorldBankData("SL.UEM.TOTL.ZS", "US", 2013, 2023);
            System.out.println("Unemployment Data: " + unemploymentData);

            String inflationData = fetchWorldBankData("FP.CPI.TOTL.ZG", "US", 2013, 2023);
            System.out.println("Inflation Data: " + inflationData);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackedTrace();
        }
    }
    public static String fetchWorldBankData(String indicator, String country, int startYear, int endYear) throws Exception {
        String urlString = String.format
        ("%s/country/%s/indicator/%s? date=%d:%d&format=json", country, indicator, startYear, endYear);
    try {
        return fetchData(urlString);
    } catch (Exception e) {
        System.out.println("Error fetching data from World Bank API. Using mock data.");
        return getMockData (indicator);
    }   
}
    
    public static String fetchData

 
}

*/