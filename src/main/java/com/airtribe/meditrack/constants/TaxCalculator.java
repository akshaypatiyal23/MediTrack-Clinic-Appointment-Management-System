package main.java.com.airtribe.meditrack.constants;

public class TaxCalculator {

    public static double calculateTax(double amount) {
        return amount * Constants.TAX_RATE;
    }
}