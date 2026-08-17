# JVM Report

This report covers Java Virtual Machine (JVM) fundamentals as they apply to MediTrack, satisfying the "Environment Setup & JVM Understanding" requirement.

**Environment used for this project:**
- JDK: OpenJDK 22 (`java -version` → `openjdk version "22.0.2"`)
- IDE-configured SDK: `openjdk-26` (IntelliJ project SDK)
- Any JDK 17+ is sufficient to compile and run the project (see [Setup_Instructions.md](Setup_Instructions.md)).

## JDK vs JRE vs JVM

| Component | Role |
|---|---|
| **JDK** (Java Development Kit) | Full development kit: compiler (`javac`), debugger, tools, plus a JRE. Needed to *build* MediTrack. |
| **JRE** (Java Runtime Environment) | JVM + core class libraries. Needed to *run* compiled `.class` files, but not to compile them. |
| **JVM** (Java Virtual Machine) | The abstract machine that actually executes bytecode. Platform-specific implementation, platform-independent bytecode contract. |

## Class Loader

The Class Loader subsystem loads `.class` files into the JVM at runtime, in three phases:

1. **Loading** — reads the bytecode for a class (e.g. `com.airtribe.meditrack.entity.Patient`) from the classpath and creates a `Class` object representing it in the Method Area.
2. **Linking**
   - *Verification* — checks the bytecode is structurally valid and doesn't violate JVM safety constraints.
   - *Preparation* — allocates memory for static fields and sets them to default values (e.g. `0`, `null`).
   - *Resolution* — resolves symbolic references (class/method/field names) to direct references.
3. **Initialization** — runs static initializers and static blocks, in the order they appear in the source, top to bottom. This is where `Constants` in MediTrack's `constants` package, and any `static { ... }` counters (e.g. an `IdGenerator` seed), get their real values.

Class loading is **lazy**: a class is only loaded the first time it's actively used (e.g. the first time `Doctor` is instantiated), not when `Main` starts.

The JVM uses a delegation hierarchy of loaders:
- **Bootstrap Class Loader** — loads core `java.*` classes (`java.lang`, `java.util`, etc.) from the JDK itself.
- **Platform/Extension Class Loader** — loads platform-specific extension APIs.
- **Application Class Loader** — loads MediTrack's own classes from the project's classpath/`out` directory.

## Runtime Data Areas

When the JVM runs, it partitions memory into distinct regions:

- **Heap** — shared across all threads; stores all objects and arrays created with `new`. Every `Patient`, `Doctor`, `Appointment`, and `Bill` instance MediTrack creates lives here. Garbage collected automatically when no longer reachable.
- **Stack** — one per thread; stores stack frames for method calls. Each frame holds local variables, method parameters, and partial results (e.g. local `int` counters or object references inside `AppointmentService.createAppointment(...)`). Frames are pushed on call and popped on return — this is why deeply recursive calls can cause a `StackOverflowError`.
- **Method Area** (part of the JVM's runtime memory, shared across threads) — stores per-class data: field/method metadata, the runtime constant pool, and static variables (e.g. `Constants.TAX_RATE`).
- **PC (Program Counter) Register** — one per thread; holds the address of the JVM instruction currently being executed by that thread. Lets each thread track its own execution point independently, which matters once MediTrack introduces concurrency (e.g. `TimerTask` reminders running alongside the main console loop).
- **Native Method Stacks** — support native (non-Java) method calls, used internally by the JVM/JDK.

```
             ┌───────────────────────────┐
             │        Method Area        │  (class metadata, static fields, constant pool)
             └───────────────────────────┘
             ┌───────────────────────────┐
             │            Heap           │  (all objects: Patient, Doctor, Appointment...)
             └───────────────────────────┘
   Thread A:  ┌───────┐ ┌───────┐          Thread B: ┌───────┐ ┌───────┐
              │ Stack │ │  PC   │                     │ Stack │ │  PC   │
              └───────┘ └───────┘                     └───────┘ └───────┘
```

## Execution Engine

Once code is loaded and verified, the Execution Engine actually runs the bytecode. It has three main components:

1. **Interpreter** — reads and executes bytecode instructions one at a time. Fast to start, but re-interprets the same instructions every time a method is called — slow for "hot" code paths that run repeatedly.
2. **JIT (Just-In-Time) Compiler** — monitors which methods/loops run frequently ("hot spots" — e.g. `AppointmentService.searchPatient()` if called in a loop over thousands of records) and compiles those directly to native machine code, caching the result so future calls skip re-interpretation. This is why long-running JVM programs speed up over time ("warm-up").
3. **Garbage Collector (GC)** — part of the execution engine's supporting machinery; reclaims Heap memory occupied by objects with no remaining reachable references (e.g. an `Appointment` object after it's cancelled and no longer stored in any `DataStore<T>` or list).

### Interpreter vs JIT Compiler

| | Interpreter | JIT Compiler |
|---|---|---|
| Translates | One bytecode instruction at a time | Whole hot methods/loops to native code |
| Startup cost | Low — starts executing immediately | Higher — compilation takes time upfront |
| Steady-state speed | Slower — repeats translation work | Faster — runs compiled native code directly |
| When used | Always, for code that runs rarely (cold code) | For code detected as "hot" (called/looped often) |

Modern JVMs (HotSpot, used by OpenJDK) combine both: they interpret by default and JIT-compile hot paths, giving fast startup and strong steady-state throughput.

## "Write Once, Run Anywhere" (WORA)

Java source (`.java`) is compiled by `javac` into **bytecode** (`.class`), a platform-independent intermediate format — not native machine code. Any machine with a compatible JVM can execute that same bytecode, regardless of the underlying OS/CPU, because the JVM implementation handles the platform-specific translation.

This is why MediTrack's compiled `.class` files (produced with `javac` on macOS in this project's dev environment) would run unmodified on a JVM on Windows or Linux — the only requirement is a compatible JRE/JVM on the target machine. The JVM is the abstraction layer that makes the bytecode "portable"; only the JVM itself is platform-specific, not the compiled application.
