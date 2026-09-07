package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.TaxCalculator;
import com.airtribe.meditrack.interfaces.Payable;

public abstract class Bill implements Payable {
    private final int billId;
    private final Patient patient;
    private final Appointment appointment;
    private final double consultationFee;
    private final double tax;

    protected Bill(int billId, Patient patient, Appointment appointment, double consultationFee) {
        this.billId = billId;
        this.patient = patient;
        this.appointment = appointment;
        this.consultationFee = consultationFee;
        this.tax = TaxCalculator.calculateTax(consultationFee);
    }

    public int getBillId() {
        return billId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getTax() {
        return tax;
    }

    public double getTotalAmount() {
        return getConsultationFee() + getTax();
    }
}
