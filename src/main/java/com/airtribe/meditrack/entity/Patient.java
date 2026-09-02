package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.enums.Gender;
import main.java.com.airtribe.meditrack.interfaces.Searchable;
import main.java.com.airtribe.meditrack.util.IdGenerator;

import javax.crypto.spec.OAEPParameterSpec;
import java.time.LocalDate;
import java.util.Date;

public class Patient extends Person implements Searchable {

    public Patient(String name, LocalDate dateOfBirth, int age, Gender gender, String address, long phoneNumber) {
        super(IdGenerator.getPatientId(), name, dateOfBirth, age, gender, address, phoneNumber);



    }



    @Override
    public boolean matches(String keyword) {
        return getName().equalsIgnoreCase(keyword)
                || String.valueOf(getId()).equals(keyword)
                    || String.valueOf(getAge()).equals(keyword);
    }
}
