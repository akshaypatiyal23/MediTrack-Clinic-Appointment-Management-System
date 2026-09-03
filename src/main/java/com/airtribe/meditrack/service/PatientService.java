package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.entity.Patient;
import main.java.com.airtribe.meditrack.exception.InvalidDataException;

import java.util.ArrayList;

public class PatientService {
    public ArrayList<Patient> patientList = new ArrayList<>();

    public void addPatient(Patient patient) {
        patientList.add(patient);
        System.out.println("Patient added successfully!");
    }

    public Patient getPatientById(int id) {
        for (Patient patient : patientList) {
            if (patient.getId() == id) {
                return patient;
            }
        }

        throw new InvalidDataException("No Patient found matching the id you have entered! Please try again");
    }

    public ArrayList<Patient> getAllPatients() {
        return patientList;
    }
    public void printPatient(Patient patient) {
        System.out.println();
        System.out.println("Patient Details");
        System.out.println("------------------------------");
        System.out.println("ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Age: " + patient.getAge());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Address: " + patient.getAddress());
        System.out.println("Phone Number: " + patient.getPhoneNumber());
        System.out.println("------------------------------");
    }

    public void deletePatient(int id) {

       boolean removed = patientList.removeIf(patient -> patient.getId() == id);
       if(removed) {
           System.out.println("Patient deleted successfully");
       } else{
           throw new InvalidDataException("No Patient found matching the id you have entered! Please try again");

       }


    }
}

