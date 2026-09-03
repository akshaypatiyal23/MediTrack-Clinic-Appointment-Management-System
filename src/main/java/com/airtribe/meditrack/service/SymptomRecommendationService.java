package main.java.com.airtribe.meditrack.service;

import main.java.com.airtribe.meditrack.entity.SymptomRecommendationRule;
import main.java.com.airtribe.meditrack.enums.Specialization;
import main.java.com.airtribe.meditrack.enums.Symptom;

import java.util.ArrayList;
import java.util.List;

public class SymptomRecommendationService {

    private List<SymptomRecommendationRule> rules = List.of(
            new SymptomRecommendationRule(
                    Symptom.HEADACHE,
                    Specialization.NEUROLOGY
            ),

            new SymptomRecommendationRule(
                    Symptom.CHEST_PAIN,
                    Specialization.CARDIOLOGY
            ),

            new SymptomRecommendationRule(
                    Symptom.JOINT_PAIN,
                    Specialization.ONCOLOGY
            ),

            new SymptomRecommendationRule(
                    Symptom.BLURRED_VISION,
                    Specialization.OPHTHALMOLOGY
            ),

            new SymptomRecommendationRule(
                    Symptom.SKIN_RASH,
                    Specialization.DERMATOLOGY
            ),

            new SymptomRecommendationRule(
            Symptom.TOOTHACHE,
            Specialization.DENTISTRY
            )
    );

    public List<Specialization> recommendSpecialties(
            List<Symptom> symptoms) {

        return rules.stream()
                .filter(rule -> symptoms.contains(rule.getSymptom()))
                .map(SymptomRecommendationRule::getSpecialization)
                .distinct()
                .toList();
    }
}