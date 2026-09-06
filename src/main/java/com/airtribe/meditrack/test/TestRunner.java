package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.Gender;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.DateUtil;
import com.airtribe.meditrack.util.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Manual test runner (no JUnit) exercising the core services end to end.
 * Run with: java -cp out com.airtribe.meditrack.test.TestRunner
 */
public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testPatientCrudAndOverloadedSearch();
        testDoctorServiceAndStreams();
        testAppointmentLifecycleAndExceptions();
        testBillingAndTax();
        testPatientDeepClone();
        testAppointmentDeepClone();
        testValidator();

        System.out.println();
        System.out.println("Tests passed: " + passed + ", failed: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testPatientCrudAndOverloadedSearch() {
        PatientService patientService = new PatientService();
        Patient alice = new Patient("Alice Brown", LocalDate.of(1990, 1, 1), 36, Gender.FEMALE, "456 Oak Ave", 9123456780L);
        Patient bob = new Patient("Bob Green", LocalDate.of(2000, 1, 1), 26, Gender.MALE, "1 Elm St", 9123456781L);
        patientService.addPatient(alice);
        patientService.addPatient(bob);

        assertTrue("searchPatient(int) finds by id", patientService.searchPatient(alice.getId()) == alice);
        assertTrue("searchPatient(String) finds by name", patientService.searchPatient("Bob Green").contains(bob));
        assertTrue("searchPatient(int,int) finds by age range", patientService.searchPatient(30, 40).contains(alice));
        assertThrows("searchPatient(int) throws for unknown id", InvalidDataException.class,
                () -> patientService.searchPatient(9999));

        patientService.deletePatient(bob.getId());
        assertThrows("deletePatient throws for already-removed id", InvalidDataException.class,
                () -> patientService.deletePatient(bob.getId()));
    }

    private static void testDoctorServiceAndStreams() {
        DoctorService doctorService = new DoctorService();
        Doctor cardio = new Doctor("John Smith", LocalDate.of(1980, 5, 15), 46, Gender.MALE, "123 Main St", 9876543210L,
                Specialization.CARDIOLOGY, 500, 8, "LIC1", 20);
        Doctor derma = new Doctor("Jane Doe", LocalDate.of(1985, 3, 10), 41, Gender.FEMALE, "2 Pine St", 9876543211L,
                Specialization.DERMATOLOGY, 300, 6, "LIC2", 15);
        doctorService.addDoctor(cardio);
        doctorService.addDoctor(derma);

        assertTrue("getDoctorsBySpecialization filters correctly",
                doctorService.getDoctorsBySpecialization(Specialization.CARDIOLOGY).equals(List.of(cardio)));
        assertTrue("getAverageConsultationFee computes the mean",
                doctorService.getAverageConsultationFee() == 400.0);
        assertTrue("searchDoctorsByKeyword matches by name",
                doctorService.searchDoctorsByKeyword("Jane Doe").contains(derma));
        assertTrue("countDoctorsBySpecialization groups correctly",
                doctorService.countDoctorsBySpecialization().get(Specialization.CARDIOLOGY) == 1L);
    }

    private static void testAppointmentLifecycleAndExceptions() {
        AppointmentService appointmentService = new AppointmentService();
        Patient patient = new Patient("Carol White", LocalDate.of(1995, 6, 1), 31, Gender.FEMALE, "3 Birch St", 9123456782L);
        Doctor doctor = new Doctor("Mark Lee", LocalDate.of(1975, 2, 20), 51, Gender.MALE, "4 Cedar St", 9876543212L,
                Specialization.NEUROLOGY, 600, 8, "LIC3", 25);

        Appointment appointment = appointmentService.createAppointment(patient, doctor, LocalDateTime.now().plusDays(1));
        assertTrue("createAppointment is retrievable by id",
                appointmentService.searchAppointmentById(appointment.getAppointmentId()) == appointment);

        appointmentService.cancelAppointment(appointment.getAppointmentId());
        assertTrue("cancelAppointment updates status", appointment.getStatus().name().equals("CANCELLED"));

        assertThrows("searchAppointmentById throws AppointmentNotFoundException for unknown id",
                AppointmentNotFoundException.class,
                () -> appointmentService.searchAppointmentById(9999));
    }

    private static void testBillingAndTax() {
        AppointmentService appointmentService = new AppointmentService();
        BillService billService = new BillService();
        Patient patient = new Patient("Dan Black", LocalDate.of(1992, 4, 4), 34, Gender.MALE, "5 Maple St", 9123456783L);
        Doctor doctor = new Doctor("Sara Kim", LocalDate.of(1982, 8, 8), 44, Gender.FEMALE, "6 Walnut St", 9876543213L,
                Specialization.PEDIATRICS, 500, 8, "LIC4", 18);
        Appointment appointment = appointmentService.createAppointment(patient, doctor, LocalDateTime.now().plusDays(1));

        Bill bill = billService.generateBill(appointment);
        assertTrue("generateBill applies the 18% tax rate", bill.calculatePayment() == 590.0);
        assertTrue("searchBillById finds the generated bill", billService.searchBillById(bill.getBillId()) == bill);

        var emergencyBill = billService.generateEmergencyBill(appointment);
        assertTrue("emergency bill adds the flat surcharge on top", emergencyBill.calculatePayment() == 2590.0);
    }

    private static void testPatientDeepClone() {
        Patient original = new Patient("Eve Adams", LocalDate.of(1988, 7, 7), 38, Gender.FEMALE, "7 Spruce St", 9123456784L);
        original.addMedicalHistory("Penicillin allergy");

        Patient clone = original.clone();
        clone.addMedicalHistory("Noted only on the clone");

        assertTrue("cloning a Patient copies existing history",
                clone.getMedicalHistory().contains("Penicillin allergy"));
        assertTrue("mutating the clone's history does not affect the original",
                original.getMedicalHistory().size() == 1 && clone.getMedicalHistory().size() == 2);
    }

    private static void testAppointmentDeepClone() {
        Patient patient = new Patient("Frank Moore", LocalDate.of(1991, 9, 9), 35, Gender.MALE, "8 Poplar St", 9123456785L);
        Doctor doctor = new Doctor("Grace Hall", LocalDate.of(1979, 11, 11), 47, Gender.FEMALE, "9 Ash St", 9876543214L,
                Specialization.GENERAL_MEDICINE, 400, 8, "LIC5", 22);
        Appointment original = new Appointment(doctor, patient, LocalDateTime.now().plusDays(2));

        Appointment clone = original.clone();
        clone.getDoctor().setConsultationFee(999);

        assertTrue("cloning an Appointment deep-copies its Doctor",
                original.getDoctor().getConsultationFee() == 400 && clone.getDoctor().getConsultationFee() == 999);
        assertTrue("cloned Appointment is a distinct object", clone != original);
    }

    private static void testValidator() {
        assertThrows("Validator rejects a blank name", InvalidDataException.class,
                () -> Validator.validateName("   "));
        assertThrows("Validator rejects a future date of birth", InvalidDataException.class,
                () -> Validator.validateDateOfBirth(LocalDate.now().plusDays(1)));
        assertThrows("Validator rejects a non-10-digit phone number", InvalidDataException.class,
                () -> Validator.validatePhoneNumber(12345L));
        assertTrue("DateUtil parses a valid date", DateUtil.parseDate("2024-01-15").equals(LocalDate.of(2024, 1, 15)));
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + testName);
        } else {
            failed++;
            System.out.println("[FAIL] " + testName);
        }
    }

    private static void assertThrows(String testName, Class<? extends Throwable> expectedType, Runnable action) {
        try {
            action.run();
            failed++;
            System.out.println("[FAIL] " + testName + " (expected " + expectedType.getSimpleName() + " but nothing was thrown)");
        } catch (Throwable t) {
            assertTrue(testName, expectedType.isInstance(t));
        }
    }
}
