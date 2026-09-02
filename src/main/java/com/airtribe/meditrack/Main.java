package main.java.com.airtribe.meditrack;

import main.java.com.airtribe.meditrack.entity.Doctor;
import main.java.com.airtribe.meditrack.entity.Patient;
import main.java.com.airtribe.meditrack.enums.Gender;
import main.java.com.airtribe.meditrack.enums.Specialization;
import main.java.com.airtribe.meditrack.enums.Symptom;
import main.java.com.airtribe.meditrack.exception.InvalidDataException;
import main.java.com.airtribe.meditrack.service.DoctorService;
import main.java.com.airtribe.meditrack.service.PatientService;

import javax.print.Doc;
import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Scanner;

public class Main{
    static Scanner sc = new Scanner(System.in);
    static DoctorService doctorService = new DoctorService();
    static PatientService patientService = new PatientService();
    static void main(String[] args) {
     System.out.println("Welcome to MediTrack Clinic!");
     while (true){
         mainList();
         switch (sc.nextInt()) {
             case 1:
                 doctorServiceLoop:
                 while (true) {
                     doctorServiceList();
                     switch (sc.nextInt()) {
                         case 1:
                             addDoctor();
                             break;
                         case 2:
                             getDoctorById();
                             break;
                         case 3:
                             getDoctorBySpecialization();
                             break;
                         case 4:
                             getAllDoctors();
                             break;
                         case 5:
                             removeADoctor();
                             break;
                         case 6:
                             break doctorServiceLoop;
                         default:
                             System.out.println("Please enter a valid input!");
                     }
                 }

             case 2:
                 patientServiceLoop: while (true) {
                     patientServiceList();
                     switch (sc.nextInt()) {
                         case 1:
                             addPatient();
                             break;
                         case 2:
                             getPatientById();
                             break;
                         case 3:
                             getAllPatients();
                             break;
                         case 4:
                             removeAPatient();
                             break;
                         case 5:
                             break patientServiceLoop;
                         default: System.out.println("Enter a valid input");
                     }
                 }

             case 3: appointmentServiceList();
         }
     }
    }

    private static void appointmentServiceList(){
        System.out.println("Select an option\n" +
                "1. Add a Patient\n" +
                "2. Get Patient by Id\n" +
                "3. Get All Patients\n" +
                "4. Remove a Patient\n" +
                "5. Previous menu");
    }

    private static void removeAPatient(){
        System.out.println("Enter the Id of the Patient you want to remove");
        try{
            patientService.deletePatient(sc.nextInt());
            System.out.println("Patient deleted successfully!");
        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }

    private static void getAllPatients(){
        List<Patient> allPatients = patientService.getAllPatients();
        if (allPatients.isEmpty()) {
            System.out.println("No Patients added yet");
        } else {
            for (Patient patient : allPatients) {
                patientService.printPatient(patient);
            }
        }
    }

    private static void getPatientById(){
        System.out.println("Enter the Id of the Patient you want to search");
        try{

            Patient patient = patientService.getPatientById(sc.nextInt());
            patientService.printPatient(patient);

        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }
    private static void addPatient(){
        System.out.println("Enter the Full Name of Patient");
        String name = sc.nextLine();
        System.out.println("Enter the Date of Birth of Patient");
        LocalDate dateOfBirth = LocalDate.parse(sc.nextLine());
        int age =  Period.between(
                dateOfBirth,
                LocalDate.now()
        ).getYears();
        Gender gender = getGender("Patient");
        System.out.println("Enter the Address of Patient");
        String address = sc.nextLine();
        System.out.println("Enter the Phone Number of Patient");
        long phoneNumber = sc.nextLong();
        Patient patient = new Patient(name, dateOfBirth, age, gender, address, phoneNumber);
        patientService.addPatient(patient);
        System.out.println("Patient added successfully");
    }
    private static void patientServiceList(){
        System.out.println("Select an option\n" +
                "1. Add a Patient\n" +
                "2. Get Patient by Id\n" +
                "3. Get All Patients\n" +
                "4. Remove a Patient\n" +
                "5. Previous menu");
    }


    private static void removeADoctor(){
        System.out.println("Enter the Id of the Doctor you want to remove");
        try{
            doctorService.deleteDoctor(sc.nextInt());
            System.out.println("Doctor deleted successfully!");
        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }

    private static void getAllDoctors() {
        List<Doctor> allDoctors = doctorService.getAllDoctors();
        if (allDoctors.isEmpty()) {
            System.out.println("No Doctors added yet");
        } else {
            for (Doctor doctor : allDoctors) {
                doctorService.printDoctor(doctor);
            }
        }
    }

    private static void getDoctorBySpecialization() {

        System.out.println("Select the Specialization of Doctor:");

        Specialization specialization = getSpecialization();

        List<Doctor> doctors =
                doctorService.getDoctorsBySpecialization(specialization);

        if (doctors.isEmpty()) {
            System.out.println(
                    "No doctors found for specialization: "
                            + specialization
            );
            return;
        }

        doctors.forEach(doctorService::printDoctor);
    }

    private static void getDoctorById(){
        System.out.println("Enter the Id of the doctor you want to search");
        try{

            Doctor doctor = doctorService.getDoctorById(sc.nextInt());
            doctorService.printDoctor(doctor);

        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }

    private static void mainList(){
        System.out.println("Select an option to getting started with our services");
        System.out.println("1. Doctor Services - Only for Internal Staff\n" +
                "2. Patient Services - Enter your details to getting started and book an appointment\n" +
                "3. Appointment services - Make sure to add your details first in Patient services to book an appointment\n" +
                "4. Get Doctor Recommendation based on your symptoms\n" +
                "5. Emergency Services - Get immediate assistance");

    }

    private  static  void doctorServiceList(){
        System.out.println("Select an option\n" +
                "1. Add a Doctor\n" +
                "2. Get Doctor by Id\n" +
                "3. Get Doctor by Specialization\n" +
                "4. Get All Doctors\n" +
                "5. Remove a Doctor\n" +
                "6. Previous menu");
    }

    private static void addDoctor(){
        System.out.println("Enter the Full Name of Doctor");
        String name = sc.nextLine();
        System.out.println("Enter the Date of Birth of Doctor");
        LocalDate dateOfBirth = LocalDate.parse(sc.nextLine());
        int age =  Period.between(
                dateOfBirth,
                LocalDate.now()
        ).getYears();
        Gender gender = getGender("Doctor");
        System.out.println("Enter the Address of Doctor");
        String address = sc.nextLine();
        System.out.println("Enter the Phone Number of Doctor");
        long phoneNumber = sc.nextLong();
        Specialization specialization = getSpecialization();
        System.out.println("Enter the Consultation of Doctor");
        int consultationFee = sc.nextInt();
        System.out.println("Enter the Working hours of Doctor");
        int workingHours = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the License Number of Doctor");
        String licenseNumber = sc.nextLine();
        System.out.println("Enter the Experience (in years) of Doctor");
        int yearsOfExperience = sc.nextInt();
        Doctor doctor = new Doctor(name, dateOfBirth, age, gender, address, phoneNumber, specialization, consultationFee, workingHours, licenseNumber, yearsOfExperience);
        doctorService.addDoctor(doctor);
        System.out.println("Doctor added successfully!");







    }

    private static Specialization getSpecialization(){
        while (true){
            System.out.println(
                    "Enter the Specialization of Doctor:\n" +
                            "Enter \"1\" for Cardiology.\n" +
                            "Enter \"2\" for Dermatology.\n" +
                            "Enter \"3\" for Neurology.\n" +
                            "Enter \"4\" for Pediatrics\n" +
                            "Enter \"5\" for General_medicine\n" +
                            "Enter \"6\" for Ent\n" +
                            "Enter \"7\" for Gynecology\n" +
                            "Enter \"8\" for Psychiatry\n" +
                            "Enter \"9\" for Oncology\n" +
                            "Enter \"10\" for Dentistry\n" +
                            "Enter \"11\" for Ophthalmology\n"
            );
            int inputValue = sc.nextInt();
         if(inputValue ==  1){
             return Specialization.CARDIOLOGY;
         } else if(inputValue == 2){
             return  Specialization.DERMATOLOGY;
         } else if (inputValue ==3) {
            return Specialization.NEUROLOGY;
         } else if(inputValue == 4){
             return  Specialization.PEDIATRICS;
         } else if(inputValue == 5){
             return  Specialization.GENERAL_MEDICINE;
         } else if(inputValue == 6){
             return Specialization.ENT;
         } else if(inputValue == 7){
             return Specialization.GYNECOLOGY;
         } else if(inputValue == 8){
             return Specialization.PSYCHIATRY;
         } else if(inputValue == 9){
             return Specialization.ONCOLOGY;
         } else if(inputValue == 10){
             return Specialization.DENTISTRY;
         } else if(inputValue == 11){
             return Specialization.OPHTHALMOLOGY;
         } else {
             System.out.println("Invalid input. Please try again");
         }
        }
    }

    private static Gender getGender(String type) {
        while (true) {
            System.out.println(
                    "Enter the Gender of " + type+":\n" +
                            "Enter \"1\" for Male.\n" +
                            "Enter \"2\" for Female.\n" +
                            "Enter \"3\" for Transgender.\n" +
                            "Enter \"4\" for Others"
            );

            int inputValue = sc.nextInt();

            if (inputValue == 1) {
                return Gender.MALE;
            } else if (inputValue == 2) {
                return Gender.FEMALE;
            } else if (inputValue == 3) {
                return Gender.TRANSGENDER;
            } else if (inputValue == 4) {
                return Gender.OTHERS;
            } else {
                System.out.println("Please enter a valid gender.");
            }
        }
    }
}
