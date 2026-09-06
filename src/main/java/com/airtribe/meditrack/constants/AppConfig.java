package com.airtribe.meditrack.constants;

/**
 * Lazy Singleton — the instance is created on first call to {@link #getInstance()},
 * not at class-load time. Contrast with {@link com.airtribe.meditrack.util.IdGenerator},
 * which is an eager Singleton.
 */
public class AppConfig {

    private static AppConfig instance;

    private final String clinicName;
    private final double taxRate;

    private AppConfig() {
        this.clinicName = "MediTrack Clinic";
        this.taxRate = Constants.TAX_RATE;
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getClinicName() {
        return clinicName;
    }

    public double getTaxRate() {
        return taxRate;
    }
}
