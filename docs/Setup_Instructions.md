# Setup Instructions

## Prerequisites

- **JDK 17+** (project is configured for `openjdk-26` in IntelliJ; any JDK 17 or newer will compile the codebase). Verify with:
  ```bash
  java -version
  javac -version
  ```
- **IntelliJ IDEA** (recommended) — the repo already includes a `.iml` module and `.idea/` project files, so it can be opened directly. Any editor works too, since the project uses plain `javac`/`java` with no build tool (no Maven/Gradle).
- **Git** for cloning the repository.

## Getting the Code

```bash
git clone <repository-url>
cd MediTrack-Clinic-Appointment-Management-System
```

## Project Structure

```
src/main/java/com/airtribe/meditrack/
├── Main.java
├── constants/
├── entity/
├── service/
├── util/
├── exception/
├── interface/
└── test/
    └── TestRunner.java   # manual tests (no JUnit)
docs/
├── JVM_Report.md
├── Setup_Instructions.md
└── Design_Decisions.md
```

## Option A: Run via IntelliJ IDEA

1. Open IntelliJ IDEA → **Open** → select the project root folder.
2. IntelliJ will detect the existing module (`MediTrack-Clinic-Appointment-Management-System.iml`) and load the source folder automatically.
3. Ensure a Project SDK is set: **File → Project Structure → Project → SDK** (JDK 17+).
4. Right-click `Main.java` (`src/main/java/com/airtribe/meditrack/Main.java`) → **Run 'Main.main()'**.

## Option B: Run via Command Line

From the project root:

```bash
# Compile all source files into an output directory
mkdir -p out
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt

# Run the application
java -cp out com.airtribe.meditrack.Main
```

Clean up the temporary file list when done:

```bash
rm sources.txt
```

## Running Manual Tests

The project uses a manual `TestRunner` instead of JUnit:

```bash
java -cp out com.airtribe.meditrack.test.TestRunner
```

## Notes

- No external dependencies are required — the project relies only on the core Java standard library (collections, exceptions, I/O, serialization).
- The `out/` directory is git-ignored; recompile after pulling changes.
