import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class EconomicIndicatorsApp {
    private static final String WORLD_BANK_API_URL = "http://api.worldbank.org/v2";

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
            e.printStackTrace();
        }
    }

    public static String fetchWorldBankData(String indicator, String country, int startYear, int endYear) throws Exception {
        String urlString = String.format("%s/country/%s/indicator/%s?date=%d:%d&format=json",
                WORLD_BANK_API_URL, country, indicator, startYear, endYear);
        try {
            return fetchData(urlString);
        } catch (Exception e) {
            System.out.println("Error fetching data from World Bank API. Using mock data.");
            return getMockData(indicator);
        }
    }

    public static String fetchData(String urlString) throws Exception {
        URI uri = new URI(urlString);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");
        
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        } finally {
            connection.disconnect();
        }
    }

    private static String getMockData(String indicator) {
        switch (indicator) {
            case "NY.GDP.MKTP.CD":
                return "[{\"indicator\":{\"id\":\"NY.GDP.MKTP.CD\",\"value\":\"GDP (current US$)\"},\"country\":{\"id\":\"US\",\"value\":\"United States\"},\"value\":21433226000000,\"decimal\":0,\"date\":\"2023\"}]";
            case "SL.UEM.TOTL.ZS":
                return "[{\"indicator\":{\"id\":\"SL.UEM.TOTL.ZS\",\"value\":\"Unemployment, total (% of total labor force) (modeled ILO estimate)\"},\"country\":{\"id\":\"US\",\"value\":\"United States\"},\"value\":3.6,\"decimal\":1,\"date\":\"2023\"}]";
            case "FP.CPI.TOTL.ZG":
                return "[{\"indicator\":{\"id\":\"FP.CPI.TOTL.ZG\",\"value\":\"Inflation, consumer prices (annual %)\"},\"country\":{\"id\":\"US\",\"value\":\"United States\"},\"value\":4.1,\"decimal\":1,\"date\":\"2023\"}]";
            default:
                return "[]";
        }
    }
}