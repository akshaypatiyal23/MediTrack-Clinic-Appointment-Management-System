package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.interfaces.Payable;

public class ConsultationBill extends Bill implements Payable {

    public ConsultationBill(int billId, Patient patient, Appointment appointment, double consultationFee, double tax, double totalAmount) {
        super(billId, patient, appointment, consultationFee);
    }

    @Override
    public double calculatePayment() {
        return getTotalAmount();
    }
}
