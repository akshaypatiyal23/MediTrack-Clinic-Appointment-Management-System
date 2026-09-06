package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.interfaces.AppointmentObserver;

/**
 * Observer that prints a console reminder whenever a subscribed appointment event fires.
 */
public class ConsoleAppointmentNotifier implements AppointmentObserver {

    @Override
    public void onAppointmentEvent(Appointment appointment, String eventMessage) {
        System.out.println("[Reminder] Appointment " + appointment.getAppointmentId() + ": " + eventMessage);
    }
}
