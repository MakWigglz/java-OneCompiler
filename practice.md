*** EconomicIndicatorsApp.java
``` java code:
/* 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EconomicIndicatorsApp {
    public static void main(String[] args) {
        // Generate mock data
        List<EconomicData> gdpData = generateMockData ("GDP", 2013, 2023);
        List<EconomicData> unemploymentData = generateMockData("Unemployment", 2013, 2023);
        List<EconomicData> inflationData = generateMockData("Inflation", 2013, 2023);

        // Print the data
        System.out.println("GDP Data: " + gdpData);
        System.out.println("Unemployment Data: " + unemploymentData);
        System.out.println("Inflation Data: " + inflationData);

        // TODO: Add data manipulation and visualization code here 
    }

    private static List<EconomicData> generateMockData
    (String indicator, int startYear, int endYear) {
        List<EconomicData> data = new ArrayList<>();
        Random random = new Random();

        for (int year = startYear; year <= endYear; year++) {
            double value = switch (indicator) {
                case "GDP" -> 18000 + random.nextDouble () * 5000; // GDP in billions
                case "Unemployment" -> 3 + random.nextDouble() * 5; // Unemployment rate 3-8%
                case "Inflation" -> 1 + random.nextDouble() * 3; // Inflation rate 1-4%
                default -> random.nextDouble() * 100;
            };
            data.add(new EconomicData(year, value));
        }
        return data:
    }

    static class EconomicData {
        int year;
        double value;

        EconomicData(int year, double value) {
            this.year = year;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("Year: %d, Value: %.2f", year, value);
        }
    }
}

*/