package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.enums.Specialization;
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

    public ArrayList<Doctor> getDoctorsBySpecialization(Specialization specialization){
        ArrayList<Doctor> doctorsBySpecialization = new ArrayList<>();
        for(Doctor doctor: doctorsList){
            if(doctor.getSpecialization().equals(specialization)){
                doctorsBySpecialization.add(doctor);
            }
        }

        return doctorsBySpecialization;
    }

    public void deleteDoctor(int id){

      boolean removed =  doctorsList.removeIf(doctor -> doctor.getId() == id);
      if(removed) {
          System.out.println("Doctor deleted successfully");
      } else{
          throw new InvalidDataException("No Doctor found matching the id you have entered! Please try again");
      }



    }

    public void printDoctor(Doctor doctor) {
        System.out.println("Doctor Details");
        System.out.println("------------------------------");
        System.out.println("Name: " + doctor.getName());
        System.out.println("Date of Birth: " + doctor.getDateOfBirth());
        System.out.println("Age: " + doctor.getAge());
        System.out.println("Gender: " + doctor.getGender());
        System.out.println("Address: " + doctor.getAddress());
        System.out.println("Phone Number: " + doctor.getPhoneNumber());
        System.out.println("Specialization: " + doctor.getSpecialization());
        System.out.println("Consultation Fee: " + doctor.getConsultationFee());
        System.out.println("Working Hours: " + doctor.getWorkingHours());
        System.out.println("License Number: " + doctor.getLicenseNumber());
        System.out.println("Experience: " + doctor.getYearsOfExperience() + " years");
        System.out.println("------------------------------");
    }


}
