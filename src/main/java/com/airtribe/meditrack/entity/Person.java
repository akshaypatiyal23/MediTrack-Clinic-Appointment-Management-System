package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.enums.Gender;
import main.java.com.airtribe.meditrack.util.IdGenerator;

import java.time.LocalDate;
import java.util.Date;

public class Person {
    private int id;
    private String name;
    private LocalDate dateOfBirth;
    private int age;
    private Gender gender;
    private String address;
    private long phoneNumber;

    public Person(int id, String name, LocalDate dateOfBirth, int age, Gender gender,String address, long phoneNumber){
        this.id = id;
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
