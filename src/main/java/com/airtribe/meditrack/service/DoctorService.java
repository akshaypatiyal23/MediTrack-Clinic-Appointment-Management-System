package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DoctorService {
    private final DataStore<Doctor> doctors = new DataStore<>(Doctor::getId);

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        System.out.println("Doctor added successfully!");
    }

    public Doctor getDoctorById(int id) {
        Doctor doctor = doctors.getById(id);
        if (doctor == null) {
            throw new InvalidDataException("No Doctor found matching the id you have entered! Please try again");
        }
        return doctor;
    }

    public List<Doctor> getAllDoctors() {
        return doctors.getAll();
    }

    public List<Doctor> getDoctorsBySpecialization(Specialization specialization) {
        return doctors.getAll().stream()
                .filter(doctor -> doctor.getSpecialization().equals(specialization))
                .collect(Collectors.toList());
    }

    public List<Doctor> searchDoctorsByKeyword(String keyword) {
        return doctors.getAll().stream()
                .filter(doctor -> doctor.matches(keyword))
                .collect(Collectors.toList());
    }

    public double getAverageConsultationFee() {
        return doctors.getAll().stream()
                .mapToInt(Doctor::getConsultationFee)
                .average()
                .orElse(0.0);
    }

    public Map<Specialization, Long> countDoctorsBySpecialization() {
        return doctors.getAll().stream()
                .collect(Collectors.groupingBy(Doctor::getSpecialization, Collectors.counting()));
    }

    public void deleteDoctor(int id) {
        boolean removed = doctors.removeById(id);
        if (removed) {
            System.out.println("Doctor deleted successfully");
        } else {
            throw new InvalidDataException("No Doctor found matching the id you have entered! Please try again");
        }
    }

    public void printDoctor(Doctor doctor) {
        System.out.println("Doctor Details");
        System.out.println("------------------------------");
        System.out.println(doctor.getDetails());
        System.out.println("Working Hours: " + doctor.getWorkingHours());
        System.out.println("License Number: " + doctor.getLicenseNumber());
        System.out.println("Experience: " + doctor.getYearsOfExperience() + " years");
        System.out.println("------------------------------");
    }
}
