package com.airtribe.meditrack.exception;

public class AppointmentNotFoundException extends InvalidDataException {
    public AppointmentNotFoundException(int appointmentId) {
        super("No appointment found with Appointment ID: " + appointmentId);
    }
}
