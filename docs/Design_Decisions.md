# Design Decisions

This document records the key architectural and OOP design choices for MediTrack and the reasoning behind them, per the project brief in [src/main/java/com/airtribe/meditrack/ReadMe.md](../src/main/java/com/airtribe/meditrack/ReadMe.md).

## Package Structure

Single base package `com.airtribe.meditrack`, split by responsibility rather than by feature:

| Package | Responsibility |
|---|---|
| `entity` | Domain objects (data + behavior directly tied to that data) |
| `service` | Business logic and orchestration over entities |
| `util` | Cross-cutting helpers with no domain state of their own |
| `exception` | Custom checked/unchecked exceptions |
| `interface` | Cross-cutting contracts implemented by multiple entities |
| `constants` | Fixed, application-wide values (tax rate, file paths) |
| `test` | Manual `TestRunner`, no JUnit dependency |

Rationale: a layered-by-responsibility structure keeps entities free of business logic (services own that), which makes the class hierarchy easier to reason about and keeps `Validator`/`DateUtil`/`CSVUtil` reusable across every entity instead of duplicated per-entity.

## Class Hierarchy

```
        MedicalEntity (abstract)
               │
             Person (abstract)
             ┌───┴───┐
          Doctor   Patient
```

- **`MedicalEntity`** (abstract class) — holds behavior/state common to *any* entity in the clinic domain (e.g. ID, common validation hooks), independent of whether it represents a person.
- **`Person`** (abstract, extends `MedicalEntity`) — adds person-specific fields shared by staff and patients (name, contact info, date of birth).
- **`Doctor` / `Patient`** (extend `Person`) — concrete, person-specific fields and behavior (specialization/fee for `Doctor`; medical history for `Patient`).

Rationale: `Doctor` and `Patient` share far more with each other (name, contact info, ID) than either shares with `Appointment` or `Bill`, so the `is-a Person` relationship is genuine inheritance rather than a forced hierarchy. Splitting `MedicalEntity` from `Person` keeps the door open for non-person medical entities later without disturbing the `Person` subtree.

Constructor chaining (`super(...)`) is used throughout so common field initialization (ID generation, base validation) lives once in `Person`/`MedicalEntity`, not duplicated in `Doctor` and `Patient`.

## Encapsulation & Validation

All entity fields are `private`, exposed only via getters/setters. Rather than validating inline in each setter, validation logic is centralized in `Validator` (in `util`) so:
- Validation rules (e.g. valid phone format, non-negative fee, valid date ranges) are defined once and reused across `Doctor`, `Patient`, `Appointment`, and `Bill`.
- Entities stay focused on holding state, not on validation policy — a change to a validation rule doesn't require touching every entity class.

## Interfaces

- **`Payable`** — implemented by anything that produces a billable amount (`Bill`, and potentially `Appointment` if appointments carry a direct charge). Decouples billing logic (`AppointmentService`/billing code) from concrete entity types — it only needs to know an object `isPayable()`.
- **`Searchable`** — implemented by entities that support lookup (`Doctor`, `Patient`). Lets `DoctorService`/`PatientService` expose search by ID, name, or age through a common contract rather than bespoke methods per service.

Default methods are used where a sensible common implementation exists (e.g. a default `toString()`-style summary), so implementers only override behavior that's genuinely entity-specific.

## Polymorphism

- **Overloading**: `searchPatient()` is overloaded by ID, name, and age rather than using a single method with a "search type" flag — this keeps call sites type-safe and self-documenting (`searchPatient(id)` vs `searchPatient(name, age)`), avoiding a fragile parameter-flag pattern.
- **Overriding**: `generateBill()` behavior differs by context (e.g. a `Doctor`-specific consultation fee vs. a `Patient`'s billing history) and is overridden where the base implementation in `MedicalEntity`/`Person` doesn't apply — this is what enables dynamic dispatch: code operating on a `Person` reference still invokes the correct subclass behavior at runtime.

## Immutability — `BillSummary`

`BillSummary` is implemented as an immutable value object: all fields `final`, set only via constructor, no setters. Rationale:
- A generated bill summary should never silently change after creation — immutability makes that a compiler-enforced guarantee, not a convention.
- Immutable objects are inherently thread-safe, which matters once concurrency (e.g. concurrent billing/reporting) is introduced — no synchronization needed to read a `BillSummary` safely from multiple threads.

## Deep vs. Shallow Copy

`Patient` and `Appointment` implement `Cloneable` with **deep copy** semantics: nested mutable objects (e.g. a `Patient`'s medical history list, an `Appointment`'s referenced `Doctor`/`Patient` where applicable) are cloned recursively rather than sharing references with the original.

Rationale: a shallow `clone()` on `Patient` would leave the clone's mutable fields (e.g. `List<String> medicalHistory`) pointing at the *same* underlying list as the original — mutating the clone would corrupt the original's data. Deep copy avoids this class of bug at the cost of a small amount of extra copying work, which is an acceptable trade-off for entities that get cloned for edit-and-cancel-safe operations (e.g. cloning an `Appointment` before applying a tentative reschedule).

## Enums over Strings

`Specialization` and `AppointmentStatus` are enums rather than raw `String` fields:
- Prevents invalid values (`"cardiologist"` typo'd, `"Cancled"`) that a `String` field would silently accept.
- Enables exhaustive `switch` handling in services (e.g. billing/notification logic branching on `AppointmentStatus`), so the compiler can catch a missed case when a new status is added.
- Self-documents the valid domain values in one place instead of scattering string literals across the codebase.

## Generics — `DataStore<T>`

A single generic `DataStore<T>` (backed by `HashMap`/`ArrayList` internally) is used for in-memory storage of `Doctor`, `Patient`, and `Appointment` collections, rather than three separate hand-written store classes.

Rationale: the storage behavior (add, remove, find by ID, list all) is identical regardless of entity type — generics let that logic be written and tested once. `HashMap` is used where ID-based lookup dominates (O(1) retrieval); `ArrayList`/streams are used where ordered iteration or filtering (e.g. "all doctors of a given specialization") is the primary access pattern.

## Exceptions

Two custom exceptions — `AppointmentNotFoundException` and `InvalidDataException` — are defined instead of relying on generic `RuntimeException`:
- Gives calling code (`Main`'s console loop) the ability to catch and handle domain-specific failure modes distinctly (e.g. show "appointment not found" vs. "invalid input, please retry").
- Supports exception chaining (wrapping a lower-level cause) so root-cause information isn't lost when a low-level I/O/parsing failure is surfaced as a domain-level error.

## Concurrency (planned)

Where concurrency is introduced (e.g. `TimerTask`-based appointment reminders), shared mutable state is minimized in favor of immutable snapshots (`BillSummary`-style) and `AtomicInteger` for simple counters (e.g. ID sequence generation in `IdGenerator`), avoiding broad `synchronized` blocks where a lock-free primitive suffices.

## Persistence (bonus)

CSV and/or Java Serialization are used for persistence rather than a database, matching the project's scope (a Core Java learning project, not a full backend). `CSVUtil` centralizes `String.split(",")`-based parsing so file-format concerns don't leak into entity or service classes; `try-with-resources` is used for all file I/O to guarantee streams are closed even on exceptions.

## Design Patterns (bonus, as applicable)

- **Singleton** — `IdGenerator`/app configuration, ensuring a single consistent ID sequence across the app rather than per-instance counters that could collide.
- **Factory** — bill creation, so the logic deciding *which* concrete billing behavior applies lives in one place instead of being duplicated at every call site that creates a `Bill`.
- **Observer** — appointment notifications, decoupling "an appointment changed status" from "who needs to know" (console reminder logic can be added/removed without changing `AppointmentService`).

These are applied only where they reduce duplication or coupling that's already present in the requirements — not added speculatively beyond what the brief's bonus section calls for.
