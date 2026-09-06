package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.enums.Gender;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.enums.Symptom;
import com.airtribe.meditrack.constants.AppConfig;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.DateUtil;
import com.airtribe.meditrack.util.Validator;

import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static DoctorService doctorService = new DoctorService();
    static PatientService patientService = new PatientService();
    static AppointmentService appointmentService = new AppointmentService();
    static BillService billService = new BillService();
    static SymptomRecommendationService symptomRecommendationService = new SymptomRecommendationService();

    static {
        appointmentService.addObserver(new ConsoleAppointmentNotifier());
    }

    public static void main(String[] args) {
        System.out.println("Welcome to " + AppConfig.getInstance().getClinicName() + "!");
        while (true) {
            try{
                mainList();
                switch (sc.nextInt()) {
                    case 1:
                        doctorServiceLoop:
                        while (true) {
                            try {
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
                                        searchDoctorByKeyword();
                                        break;
                                    case 5:
                                        getAllDoctors();
                                        break;
                                    case 6:
                                        removeADoctor();
                                        break;
                                    case 7:
                                        break doctorServiceLoop;
                                    default:
                                        System.out.println("Please enter a valid input!");
                                }
                            }catch (InputMismatchException e){
                                System.out.println("Please enter a valid number.");
                                sc.nextLine();
                            }
                        }
                        break;

                    case 2:
                        patientServiceLoop:
                        while (true) {
                            try {
                                patientServiceList();
                                switch (sc.nextInt()) {
                                    case 1:
                                        addPatient();
                                        break;
                                    case 2:
                                        getPatientById();
                                        break;
                                    case 3:
                                        searchPatientByName();
                                        break;
                                    case 4:
                                        searchPatientsByAgeRange();
                                        break;
                                    case 5:
                                        getAllPatients();
                                        break;
                                    case 6:
                                        removeAPatient();
                                        break;
                                    case 7:
                                        clonePatientDemo();
                                        break;
                                    case 8:
                                        break patientServiceLoop;
                                    default:
                                        System.out.println("Enter a valid input");
                                }
                            }catch (InputMismatchException e){
                                System.out.println("Please enter a valid input type");
                                sc.nextLine();
                            }
                        }
                        break;

                    case 3:
                        appointmentServiceLoop:
                        while (true) {
                            try {
                                appointmentServiceList();
                                switch (sc.nextInt()) {
                                    case 1:
                                        createAppointment();
                                        break;
                                    case 2:
                                        searchAppointmentById();
                                        break;
                                    case 3:
                                        searchAppointmentsByDate();
                                        break;
                                    case 4:
                                        cancelAnAppointment();
                                        break;
                                    case 5:
                                        updateDoctorInAppointment();
                                        break;
                                    case 6:
                                        updatePatientInAppointment();
                                        break;
                                    case 7:
                                        updateAppointmentDateAndTime();
                                        break;
                                    case 8:
                                        appointmentsSearchForDoctor();
                                        break;
                                    case 9:
                                        appointmentsSearchForPatient();
                                        break;
                                    case 10:
                                        patientsListOfDoctor();
                                        break;
                                    case 11:
                                        notifyForAppointment();
                                        break;
                                    case 12:
                                        break appointmentServiceLoop;
                                    default:
                                        System.out.println("Enter a valid input");
                                }
                            } catch (InputMismatchException e){
                                System.out.println("Please enter a valid input type");
                                sc.nextLine();
                            }
                        }
                        break;

                    case 4:
                        billingServiceLoop:
                        while (true) {
                            try {
                                billingServicesList();
                                switch (sc.nextInt()) {
                                    case 1:
                                        generateABill();
                                        break;

                                    case 2:
                                        generateEmergencyBill();
                                        break;
                                    case 3:
                                        billSummary();
                                        break;
                                    case 4:
                                        break billingServiceLoop;
                                    default:
                                        System.out.println("Enter a valid input");
                                }
                            } catch (InputMismatchException e){
                                System.out.println("Please enter a valid input type");
                                sc.nextLine();
                            }
                        }
                        break;

                    case 5:
                        doctorRecommendationList();
                        break;

                    case 6:
                        emergencyServicesLoop:
                        while (true) {
                            try {
                                emergencyServices();
                                switch (sc.nextInt()) {
                                    case 1:
                                        createEmergencyAppointment();
                                        break;
                                    case 2:
                                        generateEmergencyBill();
                                        break;

                                    case 3:
                                        billSummary();
                                          break;
                                    case 4:
                                        break emergencyServicesLoop;
                                    default:
                                        System.out.println("Enter a valid input");
                                }
                            } catch (InputMismatchException e){
                                System.out.println("Please enter a valid input type");
                                sc.nextLine();
                            }
                        }
                        break;

                    case 7:
                        analyticsDashboard();
                        break;

                    case 8:
                        return;

                    default:
                        System.out.println("Enter a valid input");
                }
            } catch (InputMismatchException e){
                System.out.println("Please enter a valid input type.");
                sc.nextLine();
            }
        }
    }
    private static void createEmergencyAppointment(){
        try {
            System.out.println("Enter the ID of the Patient");
            Patient patient = patientService.searchPatient(sc.nextInt());
            System.out.println("Enter the ID of the Doctor");
            Doctor doctor = doctorService.getDoctorById(sc.nextInt());
            Appointment appointment = appointmentService.createAppointment(patient, doctor, LocalDateTime.now());
            System.out.println("Emergency Appointment created successfully with Appointment Id: "+ appointment.getAppointmentId());
        }catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }

    }
    private static void emergencyServices(){
        System.out.println(
                "========== EMERGENCY SERVICES ==========\n" +
                        "1. Create Emergency Appointment\n" +
                        "2. Generate Emergency Bill\n" +
                        "3. View/Print Emergency Bill\n"+
                        "4. Previous Menu\n"
        );
    }

    private static void doctorRecommendationList(){
        boolean run = true;
        List<Symptom>  symptoms = new ArrayList<>();
        while (run) {
            System.out.println(
                    "Select the symptoms you're facing:\n" +
                            "1. Headache\n" +
                            "2. Fever\n" +
                            "3. Cough\n" +
                            "4. Chest Pain\n" +
                            "5. Stomach Pain\n" +
                            "6. Joint Pain\n" +
                            "7. Skin Rash\n" +
                            "8. Blurred Vision\n" +
                            "9. Toothache\n" +
                            "10. Proceed with selected ones"
            );
            try {
                switch (sc.nextInt()) {
                    case 1:
                        symptoms.add(Symptom.HEADACHE);
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println(" are selected till now");
                        break;
                    case 2:
                        symptoms.add(Symptom.FEVER);
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println(" are selected till now");
                        break;

                    case 3:
                        symptoms.add(Symptom.COUGH);
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println(" are selected till now");
                        break;
                    case 4:
                        symptoms.add(Symptom.CHEST_PAIN);
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println(" are selected till now");
                        break;

                    case 5:
                        symptoms.add(Symptom.STOMACH_PAIN);
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println(" are selected till now");
                        break;
                    case 6:
                        symptoms.add(Symptom.JOINT_PAIN);
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println(" are selected till now");
                        break;

                    case 7:
                        symptoms.add(Symptom.SKIN_RASH);
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println(" are selected till now");
                        break;

                    case 8:
                        symptoms.add(Symptom.BLURRED_VISION);
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println(" are selected till now");
                        break;

                    case 9:
                        symptoms.add(Symptom.TOOTHACHE);
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println(" are selected till now");
                        break;

                    case 10:
                        System.out.println("Proceeding with symptoms:");
                        for (Symptom symptom : symptoms) {
                            System.out.print(symptom);
                        }
                        System.out.println();
                        List<Specialization> specializations = symptomRecommendationService.recommendSpecialties(symptoms);
                        System.out.print("Recommended Specializations are: ");
                        for (Specialization specialization : specializations) {
                            System.out.println(specialization + " ");
                        }
                        System.out.println();

                        run = false;
                        break;
                    default:
                        System.out.println("Enter a valid input");
                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid input type");
                sc.nextLine();
            }
        }

    }

    private static void generateEmergencyBill(){
        try {
            System.out.println("Enter the Appointment ID of the Emergency Case");
            Appointment appointment = appointmentService.searchAppointmentById(sc.nextInt());
            EmergencyBill emergencyBill = billService.generateEmergencyBill(appointment);
            System.out.println("Emergency Bill Generated Successfully and Bill Id is: "+emergencyBill.getBillId());
        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }


    }

    private static void billSummary(){
        try {
            System.out.println("Enter the Bill id to Get/Print the bill");
            billService.printBillSummary(billService.searchBillById(sc.nextInt()));
        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }

    }
    private static void generateABill(){
        try {
            System.out.println("Enter the ID of the appointment for which you want to generate the bill");
            Appointment appointment = appointmentService.searchAppointmentById(sc.nextInt());
            Bill bill = billService.generateBill(appointment);
            System.out.println("Bill Generated Successfully and Bill Id is: "+bill.getBillId());
        }catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }

    }

    private static void billingServicesList(){
        System.out.println("Select an option below\n" +
                "1. Generate Bill\n" +
                "2. Generate Emergency Bill (Make sure to create an appointment first to generate emergency bill)\n" +
                "3. Get/Print Bill Summary\n"+
                "4. Previous Menu");
    }
    private static void notifyForAppointment(){
        try {
            System.out.println("Enter the ID of the appointment for which you want to send a notification: ");
            int appointmentId = sc.nextInt();
            System.out.println("Enter the recipient for the notification\n" +
                    "Press \"1\" to send notification to Patient\n" +
                    "Press \"2\" to send notification to Doctor\n" +
                    "Press \"3\" to send notification to both Patient and Doctor");
            int choice = sc.nextInt();
            appointmentService.notifyAppointment(appointmentId, choice);
        }catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }
    private static void patientsListOfDoctor(){
        try{
            System.out.println("Enter the id of the Doctor to see patients list");
            Doctor doctor = doctorService.getDoctorById(sc.nextInt());
            List<Patient> patients = appointmentService.listPatientsByDoctor(doctor);
            for(Patient patient: patients){
                patientService.printPatient(patient);
            }
        }catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }
    private static void appointmentsSearchForPatient(){
        try {
            System.out.println("Enter the ID of the Patient to see the list of Appointments");
            Patient patient = patientService.searchPatient(sc.nextInt());
            List<Appointment> appointments = appointmentService.listAppointmentsByPatient(patient);
            for(Appointment appointment: appointments){
                appointmentService.printAppointment(appointment);
            }
        }catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }

    }

    private static void appointmentsSearchForDoctor(){
        try {
            System.out.println("Enter the ID of the doctor to see the list of Appointments");
            Doctor doctor = doctorService.getDoctorById(sc.nextInt());
            List<Appointment> appointments = appointmentService.listAppointmentsByDoctor(doctor);
            for(Appointment appointment: appointments){
                appointmentService.printAppointment(appointment);
            }
        }catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }

    }
    private static void updateAppointmentDateAndTime(){
        try {
            System.out.println("Enter the Appointment Id where you want to update Date and Time");
            int appointmentId = sc.nextInt();
            sc.nextLine();
            try {
                System.out.println("Enter the new Date and Time for the appointment (in format yyyy-MM-dd HH:mm");
                LocalDateTime localDateTime = DateUtil.parseDateTime(sc.nextLine());
                appointmentService.updateAppointmentDateTime(appointmentId, localDateTime);

            } catch (DateTimeParseException e){
                System.out.println("Please enter valid format - yyyy-MM-dd HH:mm");
            }
        }
            catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }
    private static void updatePatientInAppointment(){
        try {
            System.out.println("Enter the Appointment Id where you want to update Patient");
            int appointmentId = sc.nextInt();
            System.out.println("Enter the ID of the new Patient you want to add");
            Patient patient = patientService.searchPatient(sc.nextInt());
            appointmentService.updatePatient(appointmentId, patient);

        }catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }    }

    private static void updateDoctorInAppointment(){
        try {
            System.out.println("Enter the Appointment Id where you want to update Doctor");
            int appointmentId = sc.nextInt();
            System.out.println("Enter the ID of the new Doctor you want to add");
            Doctor doctor = doctorService.getDoctorById(sc.nextInt());
            appointmentService.updateDoctor(appointmentId, doctor);

        }catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }

    private static void cancelAnAppointment(){
        try {
            System.out.println("Enter the ID of Appointment which you want to cancel");
            appointmentService.cancelAppointment(sc.nextInt());
        }catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }

    }
    private static void searchAppointmentsByDate(){
        try {
            try{
            System.out.println("Enter the Date and Time of Appointment (in format yyyy-MM-dd");
            LocalDate localDate = DateUtil.parseDate(sc.nextLine());
            List<Appointment> appointments = appointmentService.searchAppointmentsByDate(localDate);
            for (Appointment appointment : appointments) {
                appointmentService.printAppointment(appointment);
            }
        } catch (DateTimeParseException e){
                System.out.println("Please enter a valid format - yyyy-MM-dd");
            }
        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }


    }

    private static void searchAppointmentById(){
        try {
            System.out.println("Enter the Appointment ID");
            Appointment appointment = appointmentService.searchAppointmentById(sc.nextInt());
            appointmentService.printAppointment(appointment);

        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }

    }

    private static void createAppointment(){

        try {
            System.out.println("Enter the id of the Patient");
            Patient patient = patientService.searchPatient(sc.nextInt());
            System.out.println("Enter the id of the Doctor");
            Doctor doctor = doctorService.getDoctorById(sc.nextInt());
            sc.nextLine();
            try {
                System.out.println("Enter the Date and Time (in format yyyy-MM-dd HH:mm) for the Appointment");
                String input = sc.nextLine();
                LocalDateTime appointmentDateTime = DateUtil.parseDateTime(input);
                if (appointmentDateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Appointment cannot be scheduled in the past.");
                    return;
                }
                appointmentService.createAppointment(patient, doctor, appointmentDateTime);
                System.out.println("Appointment added successfully!");

            }catch (DateTimeParseException e){
                System.out.println("Please enter the date and time in yyyy-MM-dd HH:mm format.");
            }
        }catch (InvalidDataException e){
            System.out.println(e.getMessage());

        }


    }

    private static void appointmentServiceList(){
        System.out.println("Select an option\n" +
                "1. Create an Appointment\n" +
                "2. Search for an appointment by Id\n" +
                "3. Search for an appointments by date\n" +
                "4. Cancel an Appointment\n" +
                "5. Update Doctor in Appointment\n" +
                "6. Update Patient in Appointment\n" +
                "7. Update Appointment Date and Time\n" +
                "8. Appointments search for Doctor\n" +
                "9. Appointments search for Patient\n" +
                "10. Patients list for the Doctor\n" +
                "11. Notify for Appointment\n" +
                "12. Previous Menu");
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

            Patient patient = patientService.searchPatient(sc.nextInt());
            patientService.printPatient(patient);

        } catch (InvalidDataException e){
            System.out.println(e.getMessage());
        }
    }
    private static void addPatient(){
        sc.nextLine();
        try {
            System.out.println("Enter the Full Name of Patient");
            String name = sc.nextLine();
            Validator.validateName(name);

            System.out.println("Enter the Date of Birth of Patient (in format yyyy-MM-dd)");
            LocalDate dateOfBirth = DateUtil.parseDate(sc.nextLine());
            Validator.validateDateOfBirth(dateOfBirth);
            int age = DateUtil.calculateAge(dateOfBirth);

            Gender gender = getGender("Patient");
            sc.nextLine();
            System.out.println("Enter the Address of Patient");
            String address = sc.nextLine();
            Validator.validateAddress(address);

            System.out.println("Enter the Phone Number of Patient");
            long phoneNumber = sc.nextLong();
            Validator.validatePhoneNumber(phoneNumber);

            Patient patient = new Patient(name, dateOfBirth, age, gender, address, phoneNumber);
            patientService.addPatient(patient);
        } catch (DateTimeParseException e) {
            System.out.println("Please enter the date in yyyy-MM-dd format.");
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchPatientByName(){
        sc.nextLine();
        System.out.println("Enter the Name of the Patient you want to search");
        String name = sc.nextLine();
        try {
            List<Patient> patients = patientService.searchPatient(name);
            patients.forEach(patientService::printPatient);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchPatientsByAgeRange(){
        try {
            System.out.println("Enter the minimum age");
            int minAge = sc.nextInt();
            System.out.println("Enter the maximum age");
            int maxAge = sc.nextInt();
            List<Patient> patients = patientService.searchPatient(minAge, maxAge);
            patients.forEach(patientService::printPatient);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void clonePatientDemo(){
        try {
            System.out.println("Enter the Id of the Patient to clone");
            Patient original = patientService.searchPatient(sc.nextInt());
            Patient clone = original.clone();
            clone.addMedicalHistory("Noted only on the cloned copy");
            System.out.println("Original patient medical history: " + original.getMedicalHistory());
            System.out.println("Cloned patient medical history:    " + clone.getMedicalHistory());
            System.out.println("Deep copy confirmed — mutating the clone's medical history left the original untouched.");
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void patientServiceList(){
        System.out.println("Select an option\n" +
                "1. Add a Patient\n" +
                "2. Get Patient by Id\n" +
                "3. Search Patient by Name\n" +
                "4. Search Patients by Age Range\n" +
                "5. Get All Patients\n" +
                "6. Remove a Patient\n" +
                "7. Clone a Patient (deep copy demo)\n" +
                "8. Previous menu");
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
                "4. Billing Services - View and manage your bills and bill summaries\n" +
                "5. Get Doctor Recommendation based on your symptoms\n" +
                "6. Emergency Services - Get immediate assistance\n" +
                "7. Analytics Dashboard\n" +
                "8. Exit");

    }

    private static void analyticsDashboard(){
        System.out.println("========== ANALYTICS DASHBOARD ==========");
        System.out.printf("Average Consultation Fee: %.2f%n", doctorService.getAverageConsultationFee());
        System.out.println("Doctor count by specialization: " + doctorService.countDoctorsBySpecialization());
        System.out.println("Appointments per doctor: " + appointmentService.countAppointmentsPerDoctor());
        System.out.println("==========================================");
    }

    private  static  void doctorServiceList(){
        System.out.println("Select an option\n" +
                "1. Add a Doctor\n" +
                "2. Get Doctor by Id\n" +
                "3. Get Doctor by Specialization\n" +
                "4. Search Doctor by Name/ID/Age\n" +
                "5. Get All Doctors\n" +
                "6. Remove a Doctor\n" +
                "7. Previous menu");
    }

        private static void addDoctor(){
            sc.nextLine();
            try {
                System.out.println("Enter the Full Name of Doctor");
                String name = sc.nextLine();
                Validator.validateName(name);

                System.out.println("Enter the Date of Birth of Doctor (in format yyyy-MM-dd)");
                LocalDate dateOfBirth = DateUtil.parseDate(sc.nextLine());
                Validator.validateDateOfBirth(dateOfBirth);
                int age = DateUtil.calculateAge(dateOfBirth);

                Gender gender = getGender("Doctor");
                sc.nextLine();
                System.out.println("Enter the Address of Doctor");
                String address = sc.nextLine();
                Validator.validateAddress(address);

                System.out.println("Enter the Phone Number of Doctor");
                long phoneNumber = sc.nextLong();
                Validator.validatePhoneNumber(phoneNumber);

                Specialization specialization = getSpecialization();
                System.out.println("Enter the Consultation Fee of Doctor");
                int consultationFee = sc.nextInt();
                Validator.validateConsultationFee(consultationFee);

                System.out.println("Enter the daily Working hours of Doctor");
                int workingHours = sc.nextInt();
                Validator.validateWorkingHours(workingHours);
                sc.nextLine();
                System.out.println("Enter the License Number of Doctor");
                String licenseNumber = sc.nextLine();
                Validator.validateLicenseNumber(licenseNumber);

                System.out.println("Enter the Experience (in years) of Doctor");
                int yearsOfExperience = sc.nextInt();
                Validator.validateExperience(yearsOfExperience, age);

                Doctor doctor = new Doctor(name, dateOfBirth, age, gender, address, phoneNumber, specialization, consultationFee, workingHours, licenseNumber, yearsOfExperience);
                doctorService.addDoctor(doctor);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter the date in yyyy-MM-dd format.");
            } catch (InvalidDataException e) {
                System.out.println(e.getMessage());
            }
    }

    private static void searchDoctorByKeyword(){
        sc.nextLine();
        System.out.println("Enter the Name, ID, or Age to search for a Doctor");
        String keyword = sc.nextLine();
        List<Doctor> doctors = doctorService.searchDoctorsByKeyword(keyword);
        if (doctors.isEmpty()) {
            System.out.println("No doctors found matching: " + keyword);
        } else {
            doctors.forEach(doctorService::printDoctor);
        }
    }

    private static Specialization getSpecialization(){
        while (true) {
            try {
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
                                "Enter \"11\" for Ophthalmology"
                );
                int inputValue = sc.nextInt();
                if (inputValue == 1) {
                    return Specialization.CARDIOLOGY;
                } else if (inputValue == 2) {
                    return Specialization.DERMATOLOGY;
                } else if (inputValue == 3) {
                    return Specialization.NEUROLOGY;
                } else if (inputValue == 4) {
                    return Specialization.PEDIATRICS;
                } else if (inputValue == 5) {
                    return Specialization.GENERAL_MEDICINE;
                } else if (inputValue == 6) {
                    return Specialization.ENT;
                } else if (inputValue == 7) {
                    return Specialization.GYNECOLOGY;
                } else if (inputValue == 8) {
                    return Specialization.PSYCHIATRY;
                } else if (inputValue == 9) {
                    return Specialization.ONCOLOGY;
                } else if (inputValue == 10) {
                    return Specialization.DENTISTRY;
                } else if (inputValue == 11) {
                    return Specialization.OPHTHALMOLOGY;
                } else {
                    System.out.println("Invalid input. Please try again");
                }
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid input type");
                sc.nextLine();
            }

        }
    }

    private static Gender getGender(String type) {
        while (true) {
            try {
                System.out.println(
                        "Enter the Gender of " + type + ":\n" +
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
            }catch (InputMismatchException e){
                System.out.println("Please enter a valid input type");
                sc.nextLine();
            }
        }
    }
}
