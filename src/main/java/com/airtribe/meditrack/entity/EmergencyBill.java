package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.interfaces.Payable;

public class EmergencyBill extends Bill implements Payable {
    public EmergencyBill(int billId, Patient patient, Appointment appointment, double consultationFee, double tax, double totalAmount) {
        super(billId, patient, appointment, consultationFee);
    }

    @Override
    public double calculatePayment() {
        return 2000.0 + getTotalAmount();
    }
}
