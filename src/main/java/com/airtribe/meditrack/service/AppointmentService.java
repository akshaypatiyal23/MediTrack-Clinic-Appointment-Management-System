package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Appointment;
import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.entity.Patient;
import main.java.com.airtribe.meditrack.enums.AppointmentStatus;
import main.java.com.airtribe.meditrack.exception.InvalidDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AppointmentService {

    private final Map<Integer, Appointment> appointments = new HashMap<>();
    public Appointment createAppointment(Patient patient, Doctor doctor, LocalDateTime appointmentDateTime){
      Appointment appointment = new Appointment(doctor, patient, appointmentDateTime);
        appointments.put(appointment.getAppointmentId(), appointment);
        return appointment;
//        System.out.println("Appointment added successfully!");
    }

    public Appointment searchAppointmentById(int appointmentId) {

        Appointment appointment = appointments.get(appointmentId);

        if (appointment == null) {
            throw new InvalidDataException("Appointment not found.");
        }

        return appointment;
    }

    public List<Appointment> searchAppointmentsByDate(LocalDate date) {

        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {

            if (appointment.getAppointmentDateTime()
                    .toLocalDate()
                    .equals(date)) {

                result.add(appointment);
            }
        }

        return result;
    }
    public void printAppointment(Appointment appointment) {
        System.out.println("Appointment ID: " + appointment.getAppointmentId());
        System.out.println("Patient: " + appointment.getPatient().getName());
        System.out.println("Doctor: " + appointment.getDoctor().getName());
        System.out.println("Date & Time: " + appointment.getAppointmentDateTime());
    }

    public void cancelAppointment(int appointmentId){
        Appointment appointment = appointments.get(appointmentId);

        if (appointment == null) {
            throw new InvalidDataException(
                    "No appointment found with appointment id"
            );
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);

        System.out.println("Appointment cancelled successfully");
    }
    public void updateDoctor(int appointmentId, Doctor doctor) {
        Appointment appointment = appointments.get(appointmentId);
        if(appointment!=null){
            appointment.setDoctor(doctor);
            System.out.println("Doctor updated successful for Appointment ID: "+appointmentId);
        } else {
            throw new InvalidDataException("Appointment not found!");
        }
    }

    public void updatePatient(int appointmentId, Patient patient) {
       Appointment appointment = appointments.get(appointmentId);
       if(appointment!=null){
           appointment.setPatient(patient);
           System.out.println("Patient updated successful for Appointment ID: "+appointmentId);
       } else {
           throw new InvalidDataException("Appointment not found!");
       }
    }

    public void updateAppointmentDateTime(
            int appointmentId,
            LocalDateTime appointmentDateTime) {
        Appointment appointment = appointments.get(appointmentId);
        if(appointment!=null){
            appointment.setAppointmentDateTime(appointmentDateTime);
            System.out.println("Date and Time updated successful for Appointment ID: "+appointmentId);
        } else {
            throw new InvalidDataException("Appointment not found!");
        }
    }



    public Appointment searchAppointment(int appointmentId) {
     Appointment appointment = appointments.get(appointmentId);
     if(appointment!=null){
         return appointment;
     } else {
         throw new InvalidDataException("No appointments found with Appointment ID: "+appointmentId);
     }
    }

    public List<Appointment> searchAppointment(LocalDate date) {
        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {

            if (appointment.getAppointmentDateTime()
                    .toLocalDate()
                    .equals(date)) {

                result.add(appointment);
            }

        }

        if(!result.isEmpty()){
            return result;
        }

        throw new InvalidDataException("No Appointments found for this date");



    }

    public List<Patient> listPatientsByDoctor(Doctor doctor) {

        Set<Patient> uniquePatients = new HashSet<>();

        for (Appointment appointment : appointments.values()) {

            if (appointment.getDoctor().equals(doctor)) {
                uniquePatients.add(appointment.getPatient());
            }
        }

        if (uniquePatients.isEmpty()) {
            throw new InvalidDataException(
                    "No patients are currently listed to Doctor: "
                            + doctor.getName()
            );
        }

        return new ArrayList<>(uniquePatients);
    }

    public List<Appointment> listAppointmentsByDoctor(Doctor doctor) {

        List<Appointment> doctorAppointments = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {

            if (appointment.getDoctor().equals(doctor)) {
                doctorAppointments.add(appointment);
            }
        }

        if (doctorAppointments.isEmpty()) {
            throw new InvalidDataException(
                    "No appointments found for Doctor: "
                            + doctor.getName()
            );
        }

        return doctorAppointments;
    }

    public List<Appointment> listAppointmentsByPatient(Patient patient) {

        List<Appointment> patientAppointments = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {

            if (appointment.getPatient().equals(patient)) {
                patientAppointments.add(appointment);
            }
        }

        if (patientAppointments.isEmpty()) {
            throw new InvalidDataException(
                    "No appointments found for Patient: "
                            + patient.getName()
            );
        }

        return patientAppointments;
    }



    public void notifyAppointment(int appointmentId, int choice) {

        Appointment appointment = appointments.get(appointmentId);

        if (appointment == null) {
            throw new InvalidDataException("Appointment not found!");
        }

        switch (choice) {

            case 1:
                System.out.println(
                        "Patient " +
                                appointment.getPatient().getName() +
                                " notified about appointment " +
                                appointment.getAppointmentId()
                );
                break;

            case 2:
                System.out.println(
                        "Doctor " +
                                appointment.getDoctor().getName() +
                                " notified about appointment " +
                                appointment.getAppointmentId()
                );
                break;

            case 3:
                System.out.println(
                        "Patient " +
                                appointment.getPatient().getName() +
                                " notified."
                );

                System.out.println(
                        "Doctor " +
                                appointment.getDoctor().getName() +
                                " notified."
                );
                break;

            default:
                throw new InvalidDataException("Invalid notification choice!");
        }
    }



}
