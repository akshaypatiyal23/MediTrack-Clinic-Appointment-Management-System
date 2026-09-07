package com.airtribe.meditrack.util;

/**
 * Eager Singleton — the single instance is created at class-load time and owns
 * the counters itself. Contrast with {@link com.airtribe.meditrack.constants.AppConfig},
 * which is a lazy Singleton. Static convenience methods delegate to the instance so
 * existing call sites (IdGenerator.getDoctorId(), etc.) are unaffected.
 */
public class IdGenerator {

    private static final IdGenerator INSTANCE = new IdGenerator();

    private int doctorId;
    private int patientId;
    private int appointmentId;
    private int billId;

    static {
        INSTANCE.doctorId = 1;
        INSTANCE.patientId = 1;
        INSTANCE.appointmentId = 1;
        INSTANCE.billId = 1;
    }

    private IdGenerator() {
    }

    public static IdGenerator getInstance() {
        return INSTANCE;
    }

    public static synchronized int getDoctorId() {
        return INSTANCE.doctorId++;
    }

    public static synchronized int getPatientId() {
        return INSTANCE.patientId++;
    }

    public static synchronized int getAppointmentId() {
        return INSTANCE.appointmentId++;
    }

    public static synchronized int getBillId() {
        return INSTANCE.billId++;
    }
}
