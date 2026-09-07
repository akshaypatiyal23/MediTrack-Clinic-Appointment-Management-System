package com.airtribe.meditrack.entity;

public abstract class MedicalEntity {
    private final int id;
    private String name;

    protected MedicalEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getDetails();
}
