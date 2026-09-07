package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.enums.BillType;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;

public class BillService {
    private final DataStore<Bill> bills = new DataStore<>(Bill::getBillId);

    public Bill generateBill(Appointment appointment) {
        Bill bill = BillFactory.createBill(BillType.CONSULTATION, appointment);
        bills.add(bill);
        return bill;
    }

    public EmergencyBill generateEmergencyBill(Appointment appointment) {
        EmergencyBill bill = (EmergencyBill) BillFactory.createBill(BillType.EMERGENCY, appointment);
        bills.add(bill);
        return bill;
    }

    public void printBillSummary(Bill bill) {

        BillSummary billSummary = new BillSummary(bill);

        System.out.println("========== BILL SUMMARY ==========");

        if (bill instanceof EmergencyBill) {
            System.out.println("Bill Type: Emergency Bill");
        } else {
            System.out.println("Bill Type: Regular Bill");
        }

        System.out.println("Bill Id: " + billSummary.getBillId());
        System.out.println("Patient: " + billSummary.getPatientName());
        System.out.println("Doctor: " + billSummary.getDoctorName());
        System.out.println("Consultation Fee: ₹" + billSummary.getConsultationFee());
        System.out.println("Tax: ₹" + billSummary.getTax());
        System.out.println("Total Amount: " + bill.formattedAmount());

        System.out.println("==================================");
    }

    public Bill searchBillById(int id) {
        Bill bill = bills.getById(id);
        if (bill == null) {
            throw new InvalidDataException("No Bills found with id: " + id);
        }
        return bill;
    }
}
