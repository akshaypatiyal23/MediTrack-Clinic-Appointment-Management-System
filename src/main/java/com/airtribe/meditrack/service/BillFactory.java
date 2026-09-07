package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.ConsultationBill;
import com.airtribe.meditrack.entity.EmergencyBill;
import com.airtribe.meditrack.enums.BillType;
import com.airtribe.meditrack.util.IdGenerator;

/**
 * Factory Pattern — decides which {@link Bill} subtype to instantiate based on {@link BillType},
 * so callers depend only on the {@code Bill} abstraction.
 */
public class BillFactory {

    private BillFactory() {
    }

    public static Bill createBill(BillType billType, Appointment appointment) {
        double consultationFee = appointment.getDoctor().getConsultationFee();

        switch (billType) {
            case EMERGENCY:
                return new EmergencyBill(IdGenerator.getBillId(), appointment.getPatient(), appointment, consultationFee);
            case CONSULTATION:
            default:
                return new ConsultationBill(IdGenerator.getBillId(), appointment.getPatient(), appointment, consultationFee);
        }
    }
}
