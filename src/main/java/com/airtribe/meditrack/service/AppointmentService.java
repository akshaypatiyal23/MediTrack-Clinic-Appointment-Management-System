package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.AppointmentObserver;
import com.airtribe.meditrack.util.DataStore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AppointmentService {

    private final DataStore<Appointment> appointments = new DataStore<>(Appointment::getAppointmentId);
    private final List<AppointmentObserver> observers = new ArrayList<>();

    public void addObserver(AppointmentObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(Appointment appointment, String eventMessage) {
        for (AppointmentObserver observer : observers) {
            observer.onAppointmentEvent(appointment, eventMessage);
        }
    }

    public Appointment createAppointment(Patient patient, Doctor doctor, LocalDateTime appointmentDateTime) {
        Appointment appointment = new Appointment(doctor, patient, appointmentDateTime);
        appointments.add(appointment);
        notifyObservers(appointment, "New appointment scheduled with Dr. " + doctor.getName()
                + " for " + patient.getName() + " on " + appointmentDateTime);
        return appointment;
    }

    public Appointment searchAppointmentById(int appointmentId) {
        Appointment appointment = appointments.getById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        return appointment;
    }

    public List<Appointment> searchAppointmentsByDate(LocalDate date) {
        return appointments.getAll().stream()
                .filter(appointment -> appointment.getAppointmentDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public void printAppointment(Appointment appointment) {
        System.out.println("Appointment ID: " + appointment.getAppointmentId());
        System.out.println("Patient: " + appointment.getPatient().getName());
        System.out.println("Doctor: " + appointment.getDoctor().getName());
        System.out.println("Date & Time: " + appointment.getAppointmentDateTime());
        System.out.println("Status: " + appointment.getStatus());
    }

    public void cancelAppointment(int appointmentId) {
        Appointment appointment = appointments.getById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        notifyObservers(appointment, "Appointment cancelled.");
        System.out.println("Appointment cancelled successfully");
    }

    public void updateDoctor(int appointmentId, Doctor doctor) {
        Appointment appointment = appointments.getById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        appointment.setDoctor(doctor);
        System.out.println("Doctor updated successful for Appointment ID: " + appointmentId);
    }

    public void updatePatient(int appointmentId, Patient patient) {
        Appointment appointment = appointments.getById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        appointment.setPatient(patient);
        System.out.println("Patient updated successful for Appointment ID: " + appointmentId);
    }

    public void updateAppointmentDateTime(int appointmentId, LocalDateTime appointmentDateTime) {
        Appointment appointment = appointments.getById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        appointment.setAppointmentDateTime(appointmentDateTime);
        notifyObservers(appointment, "Appointment rescheduled to " + appointmentDateTime + ".");
        System.out.println("Date and Time updated successful for Appointment ID: " + appointmentId);
    }

    public List<Patient> listPatientsByDoctor(Doctor doctor) {
        Set<Patient> uniquePatients = appointments.getAll().stream()
                .filter(appointment -> appointment.getDoctor().equals(doctor))
                .map(Appointment::getPatient)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (uniquePatients.isEmpty()) {
            throw new InvalidDataException("No patients are currently listed to Doctor: " + doctor.getName());
        }

        return new ArrayList<>(uniquePatients);
    }

    public List<Appointment> listAppointmentsByDoctor(Doctor doctor) {
        List<Appointment> doctorAppointments = appointments.getAll().stream()
                .filter(appointment -> appointment.getDoctor().equals(doctor))
                .collect(Collectors.toList());

        if (doctorAppointments.isEmpty()) {
            throw new InvalidDataException("No appointments found for Doctor: " + doctor.getName());
        }

        return doctorAppointments;
    }

    public List<Appointment> listAppointmentsByPatient(Patient patient) {
        List<Appointment> patientAppointments = appointments.getAll().stream()
                .filter(appointment -> appointment.getPatient().equals(patient))
                .collect(Collectors.toList());

        if (patientAppointments.isEmpty()) {
            throw new InvalidDataException("No appointments found for Patient: " + patient.getName());
        }

        return patientAppointments;
    }

    /**
     * Analytics: number of appointments booked per doctor, keyed by doctor name.
     */
    public Map<String, Long> countAppointmentsPerDoctor() {
        return appointments.getAll().stream()
                .collect(Collectors.groupingBy(
                        appointment -> appointment.getDoctor().getName(),
                        Collectors.counting()));
    }

    public void notifyAppointment(int appointmentId, int choice) {
        Appointment appointment = appointments.getById(appointmentId);

        if (appointment == null) {
            throw new AppointmentNotFoundException(appointmentId);
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
