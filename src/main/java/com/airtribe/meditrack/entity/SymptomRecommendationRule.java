package main.java.com.airtribe.meditrack.entity;

import main.java.com.airtribe.meditrack.enums.Specialization;
import main.java.com.airtribe.meditrack.enums.Symptom;

public class SymptomRecommendationRule {
    private Symptom symptom;
    private Specialization specialization;
    private int weight;
    public  SymptomRecommendationRule(Symptom symptom, Specialization specialization){
        this.symptom = symptom;
        this.specialization = specialization;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
