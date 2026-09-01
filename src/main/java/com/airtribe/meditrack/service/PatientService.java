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

    public void deletePatient(int id) {
        patientList.removeIf(patient -> patient.getId() == id);
        System.out.println("Patient deleted successfully");


    }
}
