
import java.io.*;
import java.nio.charset.StandardCharsets;
public class mortgageCalculator {
    public static void main(String[] args) {
        String file = args[0];
        InputStream fileName = mortgageCalculator.class.getClassLoader().getResourceAsStream(file + ".txt");
        assert fileName !=null;
        calculateMortgage(fileName);
    }

    private static void calculateMortgage(InputStream fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            int prospectNumber = 1;

            // Skip header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                CustomerData customerData = parseCustomerData(line);
                if (customerData != null) {
                    double monthlyPayment = calculateMonthlyPayment(
                            customerData.getTotalLoan(),
                            customerData.getYearlyInterest(),
                            customerData.getLoanPeriod());
                    System.out.println();
                    System.out.println("******************************************************************************************");
                    System.out.printf("Prospect %d: %s wants to borrow %.2f € for a period of %d years and pay %.2f € each month%n",
                            prospectNumber++, customerData.getCustomerName(),
                            customerData.getTotalLoan(), customerData.getLoanPeriod(), monthlyPayment);
                    System.out.println("******************************************************************************************");
                    System.out.println();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static CustomerData parseCustomerData(String line) {

        if (line.trim().isEmpty()) {
            return null;
        }

        String[] customerData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (customerData.length == 4) {
            try {
                String customerName = cleanName(customerData[0]);
                double totalLoan = Double.parseDouble(customerData[1].trim());
                double yearlyInterest = Double.parseDouble(customerData[2].trim());
                int loanPeriod = Integer.parseInt(customerData[3].trim());

                return new CustomerData(customerName, totalLoan, yearlyInterest, loanPeriod);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + line);
            }
        } else {
            System.out.println("Invalid data format: " + line);
        }

        return null;
    }

    private static String cleanName(String name) {

        String cleanName = name.replaceAll("^\\s*\"|\"\\s*$", "").trim();

        cleanName = cleanName.replaceAll("[^a-zA-Z0-9\\séÉåÅäÄöÖÆøØ]", " ");

        cleanName = cleanName.replaceAll("\\s+", " ");

        return cleanName.trim();
    }

    private record CustomerData(String customerName, double totalLoan, double yearlyInterest, int loanPeriod) {
        public String getCustomerName() {
            return customerName;
        }
        public double getTotalLoan() {
            return  totalLoan;
        }
        public double getYearlyInterest() {
            return yearlyInterest;
        }
        public int getLoanPeriod() {
            return loanPeriod;
        }
    }

    private static double calculateMonthlyPayment(double totalLoan, double yearlyInterest, int loanPeriod) {

        int numberOfPayments = loanPeriod * 12;
        double monthlyInterest = yearlyInterest / 12 / 100;

        double numerator = totalLoan * monthlyInterest * power(1 + monthlyInterest, numberOfPayments);
        double denominator = power(1 + monthlyInterest, numberOfPayments) -1;
        return numerator / denominator;
    }
    private static double power(double base, int exponent) {
        double result = 1.0;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
}
