package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.*;
import main.java.com.airtribe.meditrack.exception.InvalidDataException;
import main.java.com.airtribe.meditrack.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class BillService {
    private List<Bill> allBills = new ArrayList<>();
    public Bill generateBill(Appointment appointment) {

        double consultationFee =
                appointment.getDoctor().getConsultationFee();


       Bill bill = new Bill(IdGenerator.getBillId(), appointment.getPatient(), appointment, consultationFee);
        allBills.add(bill);
        return bill;

    }

    public EmergencyBill generateEmergencyBill(Appointment appointment) {

        double consultationFee = appointment.getDoctor().getConsultationFee();

        EmergencyBill bill = new EmergencyBill(
                IdGenerator.getBillId(),
                appointment.getPatient(), appointment,
                consultationFee
        );

        allBills.add(bill);

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
        System.out.println("Total Amount: ₹" + billSummary.getTotalAmount());

        System.out.println("==================================");
    }

    public Bill searchBillById(int id){
        for(Bill bill: allBills){
            if(bill.getBillId()==id){
                return bill;
            }
        }

        throw new InvalidDataException("No Bills found with id: "+id);
    }
}
