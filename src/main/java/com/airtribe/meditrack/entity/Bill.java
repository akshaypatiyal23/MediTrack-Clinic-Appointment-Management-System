package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.constants.TaxCalculator;
import main.java.com.airtribe.meditrack.interfaces.Payable;

public class Bill implements Payable{
    private int billId;
    private Patient patient;
    private Appointment appointment;
    private double consultationFee;
    private double tax;

    public Bill(int billId, Patient patient, Appointment appointment, double consultationFee) {
        this.billId = billId;
        this.patient = patient;
        this.appointment = appointment;
        this.consultationFee = consultationFee;
        this.tax = TaxCalculator.calculateTax(consultationFee);
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalAmount() {
        return getConsultationFee()+getTax();
    }



    @Override
    public double calculatePayment() {
        return getConsultationFee()+getTax();
    }
}
