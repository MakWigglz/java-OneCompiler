import java.util.*;
import java.util.stream.*;

public class EconomicIndicatorsApp {
    public static void main(String[] args) {
        Map<String, List<EconomicData>> allData = new HashMap<>();

        // Generate mock data for various indicators
        allData.put("GDP", generateMockData("GDP", 2013, 2023, 18000, 23000));
        allData.put("Unemployment", generateMockData("Unemployment", 2013, 2023, 3, 8));
        allData.put("Inflation", generateMockData("Inflation", 2013, 2023, 1, 4));

        // Stock market indices
        allData.put("Dow Jones", generateMockData("Dow Jones", 2013, 2023, 15000, 35000));
        allData.put("S&P 500", generateMockData("S&P 500", 2013, 2023, 1500, 4500));
        allData.put("NASDAQ", generateMockData("NASDAQ", 2013, 2023, 3500, 15000));
        allData.put("Russell 2000", generateMockData("Russell 2000", 2013, 2023, 900, 2300));
        allData.put("NYSE Composite", generateMockData("NYSE Composite", 2013, 2023, 9000, 16000));

        // Company data (using Apple as an example)
        allData.put("Apple Stock Price", generateMockData("Apple Stock Price", 2013, 2023, 50, 180));
        allData.put("Apple Revenue", generateMockData("Apple Revenue", 2013, 2023, 150, 380));
        allData.put("Apple Net Income", generateMockData("Apple Net Income", 2013, 2023, 30, 100));
        allData.put("Apple R&D Expenses", generateMockData("Apple R&D Expenses", 2013, 2023, 3, 22));
        allData.put("Apple Sales Growth", generateMockData("Apple Sales Growth", 2013, 2023, -5, 45));

        // Additional GDP indicators
        allData.put("GDP per Capita", generateMockData("GDP per Capita", 2013, 2023, 50000, 70000));
        allData.put("GDP Growth Rate", generateMockData("GDP Growth Rate", 2013, 2023, -2, 6));
        allData.put("Government Spending", generateMockData("Government Spending", 2013, 2023, 3000, 5000));
        allData.put("Consumer Spending", generateMockData("Consumer Spending", 2013, 2023, 12000, 16000));
        allData.put("Business Investment", generateMockData("Business Investment", 2013, 2023, 2000, 4000));

        // Process and visualize all data
        for (Map.Entry<String, List<EconomicData>> entry : allData.entrySet()) {
            String indicator = entry.getKey();
            List<EconomicData> data = entry.getValue();

            System.out.println("\n" + indicator + " Data: " + data);
            printStatistics(indicator, data);
            visualizeTrend(indicator, data);
            plotGraph(indicator, data);
        }
    }

    private static List<EconomicData> generateMockData(String indicator, int startYear, int endYear, double min, double max) {
        List<EconomicData> data = new ArrayList<>();
        Random random = new Random();

        for (int year = startYear; year <= endYear; year++) {
            double value = min + (max - min) * random.nextDouble();
            data.add(new EconomicData(year, value));
        }

        return data;
    }

    private static void printStatistics(String indicator, List<EconomicData> data) {
        DoubleSummaryStatistics stats = data.stream()
                .mapToDouble(d -> d.value)
                .summaryStatistics();

        System.out.printf("\n%s Statistics:\n", indicator);
        System.out.printf("Average: %.2f\n", stats.getAverage());
        System.out.printf("Minimum: %.2f\n", stats.getMin());
        System.out.printf("Maximum: %.2f\n", stats.getMax());
    }

    private static void visualizeTrend(String indicator, List<EconomicData> data) {
        System.out.println("\n" + indicator + " Trend:");
        int maxStars = 50;
        double maxValue = data.stream().mapToDouble(d -> d.value).max().orElse(0);

        for (EconomicData d : data) {
            int stars = (int) (d.value / maxValue * maxStars);
            System.out.printf("%d %s\n", d.year, "*".repeat(stars));
        }
    }

    private static void plotGraph(String indicator, List<EconomicData> data) {
        System.out.println("\n" + indicator + " Graph:");
        int height = 20;
        int width = data.size();
        char[][] graph = new char[height][width];
        for (char[] row : graph) {
            Arrays.fill(row, ' ');
        }

        double minValue = data.stream().mapToDouble(d -> d.value).min().orElse(0);
        double maxValue = data.stream().mapToDouble(d -> d.value).max().orElse(0);
        double range = maxValue - minValue;

        for (int i = 0; i < data.size(); i++) {
            int y = (int) ((data.get(i).value - minValue) / range * (height - 1));
            graph[height - 1 - y][i] = '*';
        }

        for (char[] row : graph) {
            System.out.println(new String(row));
        }
        System.out.println("-".repeat(width));
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
 /**//+
     * Generates mock economic data for a given indicator over a specified time range.//+
     * //+
     * @param indicator The name of the economic indicator.//+
     * @param startYear The starting year for the data generation.//+
     * @param endYear The ending year for the data generation.//+
     * @param min The minimum possible value for the indicator.//+
     * @param max The maximum possible value for the indicator.//+
     * @return A List of EconomicData objects containing the generated mock data.//+
     *///


/*  
1.
More indicators: We've added stock market indices, company-specific data (using Apple as an example), and additional GDP-related indicators.
2.
Flexible data generation: The generateMockData method now takes min and max parameters to generate more realistic data for each indicator.
3.
Data storage: All indicators are stored in a Map<String, List<EconomicData>> for easy access and processing.
4.
Graph plotting: A new plotGraph method creates a simple ASCII graph for each indicator, showing the trend over time.
5.
Streamlined processing: The main method now iterates through all indicators, applying the same analysis and visualization techniques to each.
This version provides a more comprehensive economic analysis tool with various indicators and visual representations, all within a single file for easy compilation on OneCompiler. The ASCII graphs offer a visual representation of trends over time for each indicator.
Note that due to the limitations of console output, the graphs are simple ASCII representations. For more sophisticated graphing, you'd typically use a graphing library like JFreeChart, but that would require additional dependencies which might not be available in the OneCompiler environment.
*/
