package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.exception.InvalidDataException;

import java.util.ArrayList;

public class DoctorService {
    public   ArrayList<Doctor> doctorsList = new ArrayList<>();

    public  void addDoctor(Doctor doctor){
    doctorsList.add(doctor);
    System.out.println("Doctor added successfully!");
    }

    public  Doctor getDoctorById(int id){
        for(Doctor doctor : doctorsList){
            if(doctor.getId() == id){
                return  doctor;
            }
        }

        throw new InvalidDataException("No Doctor found matching the id you have entered! Please try again");
    }

    public ArrayList<Doctor> getAllDoctors(){
        return doctorsList;
    }

    public void deleteDoctor(int id){

      boolean removed =  doctorsList.removeIf(doctor -> doctor.getId() == id);
      if(removed) {
          System.out.println("Doctor deleted successfully");
      } else{
          throw new InvalidDataException("No Doctor found matching the id you have entered! Please try again");
      }



    }


}
