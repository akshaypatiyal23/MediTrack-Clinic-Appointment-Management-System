package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.entity.Person;
import main.java.com.airtribe.meditrack.enums.Gender;
import main.java.com.airtribe.meditrack.enums.Specialization;
import main.java.com.airtribe.meditrack.util.IdGenerator;

import java.util.Date;

public class Doctor extends Person {
   private Specialization specialization;
   private int consultationFee;
   private int workingHours;
   private String licenseNumber;
   private int yearsOfExperience;

   public  Doctor(String name, Date dateOfBirth, Gender gender, String address, long phoneNumber,Specialization specialization, int consultationFee, int workingHours, String licenseNumber, int yearsOfExperience){
       super(IdGenerator.getDoctorId(), name, dateOfBirth, gender, address, phoneNumber);
       this.specialization = specialization;
       this.consultationFee = consultationFee;
       this.workingHours = workingHours;
       this.licenseNumber = licenseNumber;
       this.yearsOfExperience = yearsOfExperience;
   }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public int getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(int consultationFee) {
        this.consultationFee = consultationFee;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}


