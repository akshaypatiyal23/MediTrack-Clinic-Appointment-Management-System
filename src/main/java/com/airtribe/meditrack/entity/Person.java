package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.enums.Gender;
import main.java.com.airtribe.meditrack.util.IdGenerator;

import java.util.Date;

public class Person {
    private int id;
    private String name;
    private Date dateOfBirth;
    private Gender gender;
    private String address;
    private long phoneNumber;

    public Person(int id, String name, Date dateOfBirth, Gender gender,String address, long phoneNumber){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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
