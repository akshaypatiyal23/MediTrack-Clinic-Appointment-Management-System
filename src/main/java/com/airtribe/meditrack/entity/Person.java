package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.Gender;
import com.airtribe.meditrack.interfaces.Searchable;

import java.time.LocalDate;

public class Person extends MedicalEntity implements Searchable {
    private LocalDate dateOfBirth;
    private int age;
    private Gender gender;
    private String address;
    private long phoneNumber;

    public Person(int id, String name, LocalDate dateOfBirth, int age, Gender gender, String address, long phoneNumber) {
        super(id, name);
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getDetails() {
        return "ID: " + getId() +
                ", Name: " + getName() +
                ", DOB: " + dateOfBirth +
                ", Age: " + age +
                ", Gender: " + gender +
                ", Address: " + address +
                ", Phone: " + phoneNumber;
    }

    @Override
    public boolean matches(String keyword) {
        return getName().equalsIgnoreCase(keyword)
                || String.valueOf(getId()).equals(keyword)
                || String.valueOf(getAge()).equals(keyword);
    }
}
