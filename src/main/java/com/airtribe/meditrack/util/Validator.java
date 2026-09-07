package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

import java.time.LocalDate;

public class Validator {

    private Validator() {
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty() || !name.matches("[a-zA-Z ]+")) {
            throw new InvalidDataException("Please enter a valid name.");
        }
    }

    public static void validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            throw new InvalidDataException("Date of birth cannot be in the future.");
        }
    }

    public static void validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidDataException("Please enter a valid address.");
        }
    }

    public static void validatePhoneNumber(long phoneNumber) {
        if (String.valueOf(phoneNumber).length() != 10) {
            throw new InvalidDataException("Please enter a valid 10-digit phone number.");
        }
    }

    public static void validateConsultationFee(int consultationFee) {
        if (consultationFee <= 0) {
            throw new InvalidDataException("Consultation fee must be greater than 0.");
        }
    }

    public static void validateWorkingHours(int workingHours) {
        if (workingHours <= 0 || workingHours > 24) {
            throw new InvalidDataException("Working hours must be between 1 and 24.");
        }
    }

    public static void validateExperience(int yearsOfExperience, int age) {
        if (yearsOfExperience < 0 || yearsOfExperience > age - 18) {
            throw new InvalidDataException("Please enter valid experience.");
        }
    }

    public static void validateLicenseNumber(String licenseNumber) {
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            throw new InvalidDataException("Please enter a valid license number.");
        }
    }
}
