package com.airtribe.meditrack.interfaces;

public interface Payable {
    double calculatePayment();

    default String formattedAmount() {
        return "₹" + String.format("%.2f", calculatePayment());
    }
}
