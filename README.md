# Simple Eligibility Check System

A **beginner-friendly** Java application for checking student eligibility and enrollment.

## 📁 Project Structure (Only 4 Files!)

```
src/main/java/eligibility/
├── Main.java          (Entry point - 30 lines)
├── Student.java       (Student data + Grade inner class - 230 lines)
├── DataManager.java   (Load/Save data - 150 lines)
└── MainWindow.java    (GUI window - 310 lines)
```

## 🎯 What This Program Does

1. **Shows a list of students** with their CGPA and eligibility status
2. **Color-coded rows**: Green = Eligible, Red = Not Eligible
3. **View Details**: Shows all grades and eligibility breakdown
4. **Enroll Students**: Only eligible students can be enrolled

## 📋 Eligibility Rules

A student is **ELIGIBLE** if:
- ✅ CGPA >= 2.0 (minimum)
- ✅ Failed courses <= 3 (maximum)

A student is **NOT ELIGIBLE** if:
- ❌ CGPA < 2.0 (Low CGPA)
- ❌ Failed courses > 3 (Too Many Failures)
- ❌ Both problems

## 🚀 How to Run

### Option 1: Using javac (Command Line)
```bash
# Compile all files
javac -d target/classes src/main/java/eligibility/*.java

# Run the program
java -cp target/classes eligibility.Main
```

### Option 2: Using an IDE (IntelliJ, Eclipse, NetBeans)
1. Open the project folder
2. Navigate to `src/main/java/eligibility/Main.java`
3. Right-click → Run

## 📚 Java Concepts Used

### Basic Concepts
- **Classes and Objects** - Student, MainWindow
- **Encapsulation** - Private fields with getters/setters
- **Methods** - Functions inside classes
- **Constructors** - Special methods to create objects

### Data Structures
- **ArrayList** - Stores list of students and grades
- **Inner Classes** - GradeRecord inside Student class

### GUI (Swing)
- **JFrame** - Main window
- **JTable** - Displays student data
- **JButton** - Clickable buttons
- **ActionListener** - Handles button clicks
- **BorderLayout** - Arranges components

### File I/O
- **FileReader/FileWriter** - Reading/writing files
- **BufferedReader/Writer** - Efficient file operations

## 🎨 Sample Data

The program includes 6 sample students:
1. Alice Johnson - Eligible (Good CGPA)
2. Bob Smith - Eligible (2 failures, good CGPA)
3. Charlie Brown - Not Eligible (Low CGPA)
4. Diana Wilson - Not Eligible (4 failures)
5. Edward Davis - Not Eligible (Both problems)
6. Fiona Green - Eligible (Exactly at limits)

## 📝 Code Comments

Every file contains extensive comments explaining:
- What each class does
- What each method does
- How the logic works
- Java concepts being used

This makes the code easy to understand for beginners!
