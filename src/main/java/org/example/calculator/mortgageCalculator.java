package org.example.calculator;

import java.io.*;
import java.nio.charset.StandardCharsets;

//Mortgage loan calculator built by Anton Backman using Gradle 7.6.4

public class mortgageCalculator {
    static void main(String[] args) {

        // Try to read specified txt file, if not throw error
        try {
            String file = args[0];
            InputStream fileName = mortgageCalculator.class.getClassLoader().getResourceAsStream(file + ".txt");

            if (fileName != null) {
                calculateMortgage(fileName);
            } else {
                System.err.println("File not found: " + file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void calculateMortgage(InputStream fileName) {

        // Read specified txt, txt files should be located in resources to work correctly
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            int prospectNumber = 1;

            // Skip header line
            reader.readLine();

            // Continue reading lines until end of txt file
            while ((line = reader.readLine()) != null) {
                CustomerData customerData = parseCustomerData(line);

                // Only call upon calculation function if data is correct
                if (customerData != null) {
                    double monthlyPayment = calculateMonthlyPayment(
                            customerData.getTotalLoan(),
                            customerData.getYearlyInterest(),
                            customerData.getLoanPeriod());

                    // Printing wanted information
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

    static CustomerData parseCustomerData(String line) {

        // Return if line is empty or null
        if (line.trim().isEmpty()) {
            return null;
        }
        // Regex to ignore commas located between alphabetic characters and
        String[] customerData = line.split(",(?=(?:[^a-zA-Z]*\"[^\"]*\"[^a-zA-Z]*)*[^a-zA-Z]*$)");

        // Assure that customerData was split into exactly 4 substrings, otherwise data format is incorrect
        if (customerData.length == 4) {
            try {
                // Cleaning up name best as possible to ensure maximum compatibility, even with some format errors
                String customerName = cleanName(customerData[0]);

                // Parsing all the numerical values from string, throw error if number format is wrong
                double totalLoan = Double.parseDouble(customerData[1].trim());
                double yearlyInterest = Double.parseDouble(customerData[2].trim());
                int loanPeriod = Integer.parseInt(customerData[3].trim());

                // Returning the parsed data as a record
                return new CustomerData(customerName, totalLoan, yearlyInterest, loanPeriod);

            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + line);
            }
        } else {
            System.out.println("Invalid data format: " + line);
        }
        return null;
    }

    // Function to clean formatting errors in prospect names using regex
    static String cleanName(String name) {

        // Removing characters that are not letters, digits, whitespace or excluded special characters
        String cleanName = name.replaceAll("[^a-zA-Z0-9\\séÉåÅäÄöÖøØÁáÍíÓóÚúÝýÀàÈèÌìÒòÙùÂâÊêÎîÔôÛûËëÏïÜüÇçÑñÆæ]", " ");

        // Previous function replaces unwanted characters with whitespaces, which is why we need to remove any additional
        // whitespaces between letters
        cleanName = cleanName.replaceAll("\\s+", " ");

        // Finally trim any rogue whitespaces from String
        return cleanName.trim();
    }

    // Using a record to store customer data
    record CustomerData(String customerName, double totalLoan, double yearlyInterest, int loanPeriod) {
        // Getters for the customer data
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

    // Function to calculate monthly payment
    static double calculateMonthlyPayment(double totalLoan, double yearlyInterest, int loanPeriod) {

        // Calculate number of payments and monthly interest from parsed data
        int numberOfPayments = loanPeriod * 12;
        double monthlyInterest = yearlyInterest / 12 / 100;

        // Apply mortgage formula to data and return the prospects monthly payment
        double numerator = totalLoan * monthlyInterest * power(1 + monthlyInterest, numberOfPayments);
        double denominator = power(1 + monthlyInterest, numberOfPayments) -1;
        return numerator / denominator;
    }

    // Since we cant use any math dependencies, we use a replacement function to calculate exponents
    static double power(double base, int exponent) {
        // Start at 1
        double result = 1.0;
        // Calculating the exponent with a for loop where the result is multiplied by the base X times
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
}
