# MediTrack – Clinic Appointment Management System

A console-based **Clinic Appointment Management System** built in Java to demonstrate **Object-Oriented Programming, Low-Level Design (LLD), SOLID principles, design patterns, exception handling, and service-based architecture**.

## 📌 Overview

MediTrack helps manage the core operations of a clinic, including:

* 👨‍⚕️ Doctor management
* 🧑‍🤝‍🧑 Patient management
* 📅 Appointment management
* 💳 Billing
* 🚨 Emergency appointments and billing
* 🩺 Doctor recommendations based on symptoms
* 🔔 Appointment notifications

The project is designed with a layered structure where entities represent domain objects and services handle business operations.

---

## ✨ Features

### 👨‍⚕️ Doctor Services

* Add a doctor
* Search doctor by ID
* Search doctors by specialization
* View all doctors
* Remove a doctor
* Validate doctor information such as:

  * Name
  * Date of birth
  * Phone number
  * Consultation fee
  * Working hours
  * License number
  * Experience

### 🧑‍🤝‍🧑 Patient Services

* Add a patient
* Search patient by ID
* View all patients
* Remove a patient
* Validate patient information
* Calculate patient age from date of birth

### 📅 Appointment Services

* Create appointments
* Search appointment by ID
* Search appointments by date
* Search appointments for a doctor
* Search appointments for a patient
* View all patients of a doctor
* Cancel appointments
* Update doctor in an appointment
* Update patient in an appointment
* Update appointment date and time
* Send appointment notifications

### 💳 Billing Services

* Generate regular bills
* Generate emergency bills
* Search and print bill summaries
* Support different bill types using polymorphism

### 🚨 Emergency Services

* Create emergency appointments
* Generate emergency bills
* Handle emergency cases separately from regular appointments

### 🩺 Doctor Recommendation

Patients can select their symptoms and MediTrack recommends relevant medical specializations.

Example:

```text
Symptoms:
HEADACHE
BLURRED_VISION

Recommended Specializations:
NEUROLOGY
OPHTHALMOLOGY
```

---

## 🏗️ Project Structure

```text
src/
└── main/
    └── java/
        └── com/
            └── airtribe/
                └── meditrack/
                    ├── entity/
                    │   ├── Appointment.java
                    │   ├── Bill.java
                    │   ├── BillSummary.java
                    │   ├── Doctor.java
                    │   ├── EmergencyBill.java
                    │   └── Patient.java
                    │
                    ├── enums/
                    │   ├── Gender.java
                    │   ├── Specialization.java
                    │   └── Symptom.java
                    │
                    ├── exception/
                    │   └── InvalidDataException.java
                    │
                    ├── interface/
                    │   └── Payable.java
                    │
                    ├── service/
                    │   ├── AppointmentService.java
                    │   ├── BillService.java
                    │   ├── DoctorService.java
                    │   ├── PatientService.java
                    │   └── SymptomRecommendationService.java
                    │
                    └── TempMain.java
```

---

## 🧠 Design & OOP Concepts

The project focuses heavily on Java OOP and LLD concepts.

### Encapsulation

Entity fields are encapsulated using private fields with appropriate constructors, getters, and setters.

### Inheritance

`EmergencyBill` extends the base `Bill` class.

```java
public class EmergencyBill extends Bill
```

### Polymorphism

The billing system works with the parent `Bill` type while supporting different bill implementations.

```java
List<Bill> allBills;
```

### Abstraction

Common payment behaviour is represented through the `Payable` interface.

### Enums

Enums are used to represent fixed domain values such as:

* `Gender`
* `Specialization`
* `Symptom`

### Exception Handling

Custom exceptions and Java exceptions are used to handle invalid operations and invalid user input.

```java
InvalidDataException
InputMismatchException
DateTimeParseException
```

---

## 🧩 Design Patterns

### Builder Pattern

The Builder Pattern is used where appropriate to construct complex objects while keeping object creation readable and maintainable.

### Service Layer

Business logic is separated from the console/UI layer through dedicated service classes:

```text
TempMain
   ↓
Service Layer
   ↓
Entity Layer
```

This keeps the main application flow separate from business operations.

---

## 🔄 Application Flow

```text
                    MediTrack
                       │
        ┌──────────────┼──────────────┐
        ↓              ↓              ↓
     Doctor         Patient       Appointment
     Services       Services        Services
        │              │              │
        └──────────────┼──────────────┘
                       ↓
                   Billing
                       │
              ┌────────┴────────┐
              ↓                 ↓
          Regular Bill     Emergency Bill

                       +
                       
              Symptom Recommendation
                       ↓
                Specialization
```

---

## 🛡️ Input Validation

MediTrack handles several common invalid-input scenarios.

Examples include:

* Invalid menu selections
* Invalid numeric input
* Invalid names
* Invalid phone numbers
* Future dates of birth
* Invalid date formats
* Past appointment dates
* Invalid consultation fees
* Invalid working hours
* Invalid doctor experience
* Empty license numbers

Date and time inputs use Java's `LocalDate` and `LocalDateTime`.

Expected formats:

```text
Date:
yyyy-MM-dd

Appointment Date & Time:
yyyy-MM-dd HH:mm
```

---

## 🛠️ Technologies Used

* **Java**
* **Java Collections Framework**
* **Java Time API**
* **Object-Oriented Programming**
* **SOLID Principles**
* **Low-Level Design**
* **Design Patterns**
* **Exception Handling**

---

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone https://github.com/akshaypatiyal23/MediTrack-Clinic-Appointment-Management-System.git
```

### 2. Open the project

Open the project in an IDE such as IntelliJ IDEA.

### 3. Run the main class

Run:

```text
TempMain.java
```

### 4. Use the console menu

The application provides the following main options:

```text
1. Doctor Services
2. Patient Services
3. Appointment Services
4. Billing Services
5. Doctor Recommendation
6. Emergency Services
7. Exit
```

---

## 🎯 Learning Objectives

This project was built to practice and demonstrate:

* Designing real-world domain models
* Applying OOP principles
* Writing maintainable Java code
* Separating business logic using services
* Using interfaces and inheritance
* Applying polymorphism
* Handling user input safely
* Designing custom exceptions
* Working with Java Collections
* Working with dates and times
* Applying LLD concepts to a practical system
* Understanding and implementing design patterns

---

## 🚀 Future Improvements

Potential future improvements include:

* Database persistence using PostgreSQL/MySQL
* REST APIs using Spring Boot
* Authentication and role-based authorization
* Persistent appointment history
* Doctor availability and scheduling
* Automated appointment reminders
* Unit and integration testing
* Logging
* GUI/Web interface
* Docker support

---

## 👨‍💻 Author

**Akshay Kumar**


## 📄 License

This project is intended for educational and learning purposes.
