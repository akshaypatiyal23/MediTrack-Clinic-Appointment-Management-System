package main.java.com.airtribe.meditrack.util;

public class IdGenerator {
     static int doctorId = 0;
     static int patientId = 0;
     static int appointmentId =0;
   public static int getDoctorId(){
       return doctorId++;
   }

    public static int getPatientId(){
        return patientId++;
    }

    public static int getAppointmentId() {
       return appointmentId++;
    }
}
