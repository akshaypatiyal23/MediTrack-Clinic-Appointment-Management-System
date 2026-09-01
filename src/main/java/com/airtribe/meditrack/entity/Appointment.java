package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.enums.AppointmentStatus;
import main.java.com.airtribe.meditrack.util.IdGenerator;

import java.time.LocalDateTime;

public class Appointment{

private int appointmentId;
private Doctor doctor;
private Patient patient;
private LocalDateTime appointmentDateTime;
private AppointmentStatus status;

    public Appointment(Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) {
        this.appointmentId = IdGenerator.getAppointmentId();
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
        this.status = AppointmentStatus.PENDING;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
