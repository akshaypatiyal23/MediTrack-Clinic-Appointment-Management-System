package main.java.com.airtribe.meditrack.util;

public class IdGenerator {
     static int doctorId = 1;
     static int patientId = 1;
     static int appointmentId =1;
     static  int billId = 1;
   public static int getDoctorId(){
       return doctorId++;
   }

    public static int getPatientId(){
        return patientId++;
    }

    public static int getAppointmentId() {
       return appointmentId++;
    }

    public static int getBillId(){
       return billId++;
    }
}
