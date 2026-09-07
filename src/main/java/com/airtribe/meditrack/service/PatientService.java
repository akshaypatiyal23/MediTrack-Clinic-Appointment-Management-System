package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;

import java.util.List;
import java.util.stream.Collectors;

public class PatientService {
    private final DataStore<Patient> patients = new DataStore<>(Patient::getId);

    public void addPatient(Patient patient) {
        patients.add(patient);
        System.out.println("Patient added successfully!");
    }

    /**
     * Overload: exact match by id.
     */
    public Patient searchPatient(int id) {
        Patient patient = patients.getById(id);
        if (patient == null) {
            throw new InvalidDataException("No Patient found matching the id you have entered! Please try again");
        }
        return patient;
    }

    /**
     * Overload: case-insensitive match by name.
     */
    public List<Patient> searchPatient(String name) {
        List<Patient> matches = patients.getAll().stream()
                .filter(patient -> patient.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
        if (matches.isEmpty()) {
            throw new InvalidDataException("No Patient found matching the name you have entered! Please try again");
        }
        return matches;
    }

    /**
     * Overload: match by inclusive age range.
     */
    public List<Patient> searchPatient(int minAge, int maxAge) {
        List<Patient> matches = patients.getAll().stream()
                .filter(patient -> patient.getAge() >= minAge && patient.getAge() <= maxAge)
                .collect(Collectors.toList());
        if (matches.isEmpty()) {
            throw new InvalidDataException("No Patient found in the given age range! Please try again");
        }
        return matches;
    }

    public List<Patient> getAllPatients() {
        return patients.getAll();
    }

    public void printPatient(Patient patient) {
        System.out.println();
        System.out.println("Patient Details");
        System.out.println("------------------------------");
        System.out.println(patient.getDetails());
        System.out.println("------------------------------");
    }

    public void deletePatient(int id) {
        boolean removed = patients.removeById(id);
        if (removed) {
            System.out.println("Patient deleted successfully");
        } else {
            throw new InvalidDataException("No Patient found matching the id you have entered! Please try again");
        }
    }
}
