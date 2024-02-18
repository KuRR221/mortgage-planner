package org.example.calculator;
import org.junit.jupiter.api.Test;
import org.example.calculator.mortgageCalculator.CustomerData;
import static org.junit.jupiter.api.Assertions.*;


public class mortgageCalculatorTest {

    @Test
    public void testParseCustomerDataValidInput1() {
        String validInput = "Batman,9000,4,5";
        CustomerData result = mortgageCalculator.parseCustomerData(validInput);

        assertNotNull(result);
        assertEquals("Batman", result.getCustomerName());
        assertEquals(9000, result.getTotalLoan());
        assertEquals(4, result.getYearlyInterest());
        assertEquals(5, result.getLoanPeriod());
    }

    @Test
    public void testParseCustomerDataValidInput2() {
        String validInput = "Albin Albinsson,1337,10,10";
        CustomerData result = mortgageCalculator.parseCustomerData(validInput);

        assertNotNull(result);
        assertEquals("Albin Albinsson", result.getCustomerName());
        assertEquals(1337, result.getTotalLoan());
        assertEquals(10, result.getYearlyInterest());
        assertEquals(10, result.getLoanPeriod());
    }

    @Test
    public void testParseCustomerDataEmptyInput() {
        String emptyInput = "";
        CustomerData result = mortgageCalculator.parseCustomerData(emptyInput);

        assertNull(result);
    }

    @Test
    public void testParseCustomerDataInvalidInput() {
        String invalidInput = "This is definately not correct";
        CustomerData result = mortgageCalculator.parseCustomerData(invalidInput);

        assertNull(result);
    }

    @Test
    public void testCleanNameEasy() {
        String nameInput = "General Kenobi";
        String result = mortgageCalculator.cleanName(nameInput);

        assertEquals("General Kenobi", result);
    }

    @Test
    public void testCleanNameMedium() {
        String nameInput = "*Suspicious*,Customer*";
        String result = mortgageCalculator.cleanName(nameInput);

        assertEquals("Suspicious Customer", result);
    }

    @Test
    public void testCleanNameHard() {
        String nameInput = "'*'--!!-Hello-__,,,,,,World--¨¨*'";
        String result = mortgageCalculator.cleanName(nameInput);

        assertEquals("Hello World", result);
    }

    @Test
    public void testCalculateMonthlyPayment1() {
        double result = mortgageCalculator.calculateMonthlyPayment(1000,5,2);
        assertEquals(43.87, result, 0.01);
    }

    @Test
    public void testCalculateMonthlyPayment2() {
        double result = mortgageCalculator.calculateMonthlyPayment(9999,5.5,3);
        assertEquals(301.93, result, 0.01);
    }

    @Test
    public void testCalculateMonthlyPayment3() {
        double result = mortgageCalculator.calculateMonthlyPayment(5050,10,2);
        assertEquals(233.03, result, 0.01);
    }

    @Test
    public void testPower1() {
        double result = mortgageCalculator.power(5, 5);
        assertEquals(3125, result);
    }

    @Test
    public void testPower2() {
        double result = mortgageCalculator.power(1.22, 10);
        assertEquals(7.304, result, 0.001);
    }

    @Test
    public void testPower3() {
        double result = mortgageCalculator.power(3, 10);
        assertEquals(59049, result, 0.001);
    }
}