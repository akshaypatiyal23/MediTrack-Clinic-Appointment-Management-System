package main.java.com.airtribe.meditrack.util;

public class IdGenerator {
     static int doctorId = 0;
   public static int getDoctorId(){
       return doctorId++;
   }
}
