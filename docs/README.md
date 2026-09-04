# MediTrack — Clinic Appointment Management System

MediTrack is a **console-based Clinic Appointment Management System** developed in Java. The project focuses on applying **Object-Oriented Programming (OOP), Low-Level Design (LLD), SOLID principles, interfaces, inheritance, polymorphism, exception handling, collections, file I/O, and design patterns** to a real-world clinic management problem.

---

## 📌 Overview

MediTrack provides functionality for managing:

* 👨‍⚕️ Doctors
* 🧑‍🤝‍🧑 Patients
* 📅 Appointments
* 💳 Billing
* 🚨 Emergency appointments and billing
* 🩺 Doctor recommendations based on symptoms
* 🔔 Appointment notifications

The application follows a service-oriented structure where the `Main` class handles console interaction and dedicated service classes handle business operations.

---

# ✨ Features

## 👨‍⚕️ Doctor Services

* Add a doctor
* Search doctor by ID
* Search doctors by specialization
* View all doctors
* Remove a doctor
* Store doctor information including:

    * Name
    * Date of birth
    * Age
    * Gender
    * Address
    * Phone number
    * Specialization
    * Consultation fee
    * Working hours
    * License number
    * Experience
* Validate doctor information

---

## 🧑‍🤝‍🧑 Patient Services

* Add a patient
* Search patient by ID
* Search patients using supported search criteria
* View all patients
* Remove a patient
* Store patient information including medical history
* Automatically calculate age from date of birth
* Validate patient information

---

## 📅 Appointment Services

* Create an appointment
* Search appointment by ID
* Search appointments by date
* Search appointments for a doctor
* Search appointments for a patient
* List patients associated with a doctor
* Cancel an appointment
* Update doctor in an appointment
* Update patient in an appointment
* Update appointment date and time
* Send appointment notifications
* Track appointment status

---

## 💳 Billing Services

MediTrack supports different types of bills through the billing hierarchy.

### Regular Consultation Billing

* Generate consultation bills
* Calculate applicable taxes
* Calculate total payable amount
* Search bills by ID
* Print bill summaries

### Emergency Billing

* Generate emergency bills
* Handle emergency-specific billing
* Calculate the payable amount
* Print emergency bill summaries

Billing hierarchy:

```text
Bill
├── ConsultationBill
└── EmergencyBill
```

---

## 🚨 Emergency Services

* Create emergency appointments
* Generate emergency bills
* Handle emergency cases through the appointment and billing services

Emergency appointments are created with the current date and time.

---

## 🩺 Doctor Recommendation

MediTrack provides doctor specialization recommendations based on selected patient symptoms.

Supported symptoms include:

* Headache
* Fever
* Cough
* Chest Pain
* Stomach Pain
* Joint Pain
* Skin Rash
* Blurred Vision
* Toothache

The recommendation system uses `SymptomRecommendationRule` and `SymptomRecommendationService` to map symptoms to relevant specializations.

Example:

```text
Selected Symptoms:
HEADACHE
BLURRED_VISION

Recommended Specializations:
NEUROLOGY
OPHTHALMOLOGY
```

---

# 🏗️ Project Structure

```text
src/
└── main/
    └── java/
        └── com/
            └── airtribe/
                └── meditrack/
                    │
                    ├── Main.java
                    │
                    ├── constants/
                    │   ├── Constants.java
                    │   └── TaxCalculator.java
                    │
                    ├── entity/
                    │   ├── Person.java
                    │   ├── Doctor.java
                    │   ├── Patient.java
                    │   ├── Appointment.java
                    │   ├── Bill.java
                    │   ├── ConsultationBill.java
                    │   ├── EmergencyBill.java
                    │   ├── BillSummary.java
                    │   └── SymptomRecommendationRule.java
                    │
                    ├── enums/
                    │   ├── Gender.java
                    │   ├── Specialization.java
                    │   ├── Symptom.java
                    │   └── AppointmentStatus.java
                    │
                    ├── exception/
                    │   └── InvalidDataException.java
                    │
                    ├── interfaces/
                    │   ├── Searchable.java
                    │   └── Payable.java
                    │
                    ├── service/
                    │   ├── DoctorService.java
                    │   ├── PatientService.java
                    │   ├── AppointmentService.java
                    │   ├── BillService.java
                    │   └── SymptomRecommendationService.java
                    │
                    └── util/
                        └── IdGenerator.java
```

---

# 🧠 OOP & LLD Concepts

## Encapsulation

Classes encapsulate their state through fields and expose controlled access through methods.

The `Person` class contains common information shared by doctors and patients.

```text
Person
├── Doctor
└── Patient
```

---

## Inheritance

Inheritance is used to model relationships between related domain objects.

### Person hierarchy

```text
Person
├── Doctor
└── Patient
```

### Billing hierarchy

```text
Bill
├── ConsultationBill
└── EmergencyBill
```

---

## Polymorphism

The billing system uses the parent `Bill` type to work with different bill implementations.

This allows regular and emergency bills to be handled through a common abstraction.

---

## Abstraction

Interfaces are used to define common contracts:

* `Searchable`
* `Payable`

`Doctor` and `Patient` implement `Searchable`, while bill-related classes implement `Payable`.

---

## Composition

Entities are connected to represent real-world relationships.

For example:

```text
Appointment
├── Patient
└── Doctor
```

An appointment connects a patient with a doctor at a particular date and time.

---

## Enums

Enums are used for fixed domain values:

* `Gender`
* `Specialization`
* `Symptom`
* `AppointmentStatus`

This provides type-safe representation of predefined values.

---

# 🧩 Design Patterns & Principles

## Builder Pattern

The project uses the Builder Pattern for constructing objects where appropriate, making object creation more readable and manageable when multiple attributes are involved.

## Service Layer

Business operations are separated into dedicated services:

```text
Main
 │
 ├── DoctorService
 ├── PatientService
 ├── AppointmentService
 ├── BillService
 └── SymptomRecommendationService
```

This keeps the console/UI logic separate from business logic.

## SOLID Principles

The project applies SOLID concepts through:

* Separation of responsibilities between entities and services
* Interface-based abstractions
* Dependency on abstractions where appropriate
* Focused service responsibilities

---

# 📦 Package Responsibilities

| Package      | Responsibility                                 |
| ------------ | ---------------------------------------------- |
| `entity`     | Core domain objects                            |
| `service`    | Business logic and application operations      |
| `interfaces` | Common contracts                               |
| `enums`      | Fixed domain values                            |
| `exception`  | Custom exception handling                      |
| `constants`  | Application-wide constants and tax calculation |
| `util`       | Utility functionality such as ID generation    |

---

# 🛡️ Validation & Exception Handling

The application validates user input and business data at different stages.

Examples include:

* Invalid menu selection
* Invalid numeric input
* Invalid names
* Invalid phone numbers
* Invalid date formats
* Future date of birth
* Past appointment date/time
* Invalid consultation fee
* Invalid working hours
* Invalid doctor experience
* Invalid license number
* Invalid entity IDs
* Invalid business operations

A custom runtime exception is provided:

```text
InvalidDataException
```

Java exceptions such as `InputMismatchException` and `DateTimeParseException` are also handled for console input.

---

# 💰 Billing Flow

### Consultation Bill

```text
Appointment
     │
     ▼
BillService
     │
     ▼
ConsultationBill
     │
     ▼
Tax Calculation
     │
     ▼
BillSummary
```

### Emergency Bill

```text
Emergency Appointment
        │
        ▼
    BillService
        │
        ▼
   EmergencyBill
        │
        ▼
    BillSummary
```

---

# 🔄 Application Flow

When the application starts, the main menu provides:

```text
1. Doctor Services
2. Patient Services
3. Appointment Services
4. Billing Services
5. Doctor Recommendation
6. Emergency Services
7. Exit
```

The user can enter the corresponding service and perform the available operations through the console.

---

# 🛠️ Technologies Used

* **Java**
* **Core Java**
* **Java Collections Framework**
* **Java Time API**
* **File I/O**
* **Exception Handling**
* **OOP**
* **SOLID Principles**
* **Low-Level Design**
* **Interfaces**
* **Inheritance**
* **Polymorphism**
* **Design Patterns**

No external framework is required to run the application.

---

# ▶️ Setup & Running

## Prerequisites

* **JDK 17 or newer**
* IntelliJ IDEA (recommended)
* Git

Check your Java installation:

```bash
java -version
javac -version
```

---

## Clone the Repository

```bash
git clone https://github.com/akshaypatiyal23/MediTrack-Clinic-Appointment-Management-System.git
cd MediTrack-Clinic-Appointment-Management-System
```

---

## Run Using IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Ensure the Project SDK is set to JDK 17 or newer.
3. Open:

```text
src/main/java/com/airtribe/meditrack/Main.java
```

4. Run `Main.main()`.

---

## Run Using Command Line

From the project root:

```bash
mkdir -p out
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out com.airtribe.meditrack.Main
```

After compilation, the generated `sources.txt` file can be removed:

```bash
rm sources.txt
```

---

# 📚 Documentation

Additional project documentation is available in the `docs/` directory:

```text
docs/
├── Setup_Instructions.md
├── Design_Decisions.md
└── JVM_Report.md
```

### Setup Instructions

Contains detailed instructions for configuring and running the project.

### Design Decisions

Documents the architectural and OOP decisions made during development.

### JVM Report

Covers JVM concepts relevant to the MediTrack project, including:

* JDK vs JRE vs JVM
* Class loading
* Runtime data areas
* Execution engine
* Garbage collection
* JVM execution concepts

---

# 🎯 Learning Objectives

This project was developed to gain practical experience in:

* Designing real-world Java applications
* Object-Oriented Programming
* Low-Level Design
* SOLID principles
* Encapsulation
* Abstraction
* Inheritance
* Polymorphism
* Interfaces
* Service-layer architecture
* Exception handling
* Java Collections
* Date and time handling
* File handling
* Business-rule implementation
* Design patterns
* JVM fundamentals

---

# 🚀 Future Improvements

Possible future enhancements include:

* Database integration
* REST APIs using Spring Boot
* Authentication and role-based access
* Persistent doctor and patient records
* Doctor availability and scheduling
* Automated appointment reminders
* Unit and integration testing
* Logging
* Web-based user interface
* Docker support

---


GitHub:
https://github.com/akshaypatiyal23/MediTrack-Clinic-Appointment-Management-System

---

## 📄 License

This project is developed for educational and learning purposes.
