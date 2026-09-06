package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.Gender;
import com.airtribe.meditrack.util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patient extends Person implements Cloneable {

    private List<String> medicalHistory;

    public Patient(String name, LocalDate dateOfBirth, int age, Gender gender, String address, long phoneNumber) {
        super(IdGenerator.getPatientId(), name, dateOfBirth, age, gender, address, phoneNumber);
        this.medicalHistory = new ArrayList<>();
    }

    public void addMedicalHistory(String note) {
        medicalHistory.add(note);
    }

    public List<String> getMedicalHistory() {
        return Collections.unmodifiableList(medicalHistory);
    }

    @Override
    public String getDetails() {
        return super.getDetails() + ", Medical History: " + medicalHistory;
    }

    /**
     * Deep copy: the nested {@code medicalHistory} list is copied into a new
     * list so mutating the clone's history never affects the original.
     */
    @Override
    public Patient clone() {
        try {
            Patient cloned = (Patient) super.clone();
            cloned.medicalHistory = new ArrayList<>(this.medicalHistory);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
