package main.java.com.airtribe.meditrack.entity;

public final class BillSummary {

    private final int billId;
    private final String patientName;
    private final String doctorName;
    private final double consultationFee;
    private final double tax;
    private final double totalAmount;

    public BillSummary(Bill bill) {
        this.billId = bill.getBillId();
        this.patientName = bill.getPatient().getName();
        this.doctorName = bill.getAppointment().getDoctor().getName();
        this.consultationFee = bill.getConsultationFee();
        this.tax = bill.getTax();
        this.totalAmount = bill.getTotalAmount();
    }

    public int getBillId() {
        return billId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getTax() {
        return tax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
