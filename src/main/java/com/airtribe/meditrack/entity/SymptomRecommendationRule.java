package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.enums.Symptom;

public class SymptomRecommendationRule {
    private Symptom symptom;
    private Specialization specialization;
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


}
