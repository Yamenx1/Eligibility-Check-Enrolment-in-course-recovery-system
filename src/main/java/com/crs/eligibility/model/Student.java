package com.crs.eligibility.model;

// Import ArrayList - a list that can grow and shrink
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================
 * STUDENT CLASS
 * ============================================
 * 
 * This class represents a student in our system.
 * It stores all the information about a student:
 * - Their personal details (name, ID, major, etc.)
 * - Their grades
 * - Whether they are eligible for enrollment
 * - Whether they have already enrolled
 * 
 * WHAT IS A CLASS?
 * A class is like a blueprint or template.
 * From this blueprint, we can create many student "objects".
 * Each object has its own data but uses the same methods.
 */
public class Student {
    
    // ==========================================
    // FIELDS (also called instance variables)
    // ==========================================
    // These store the data for each student
    // Each student object has its own copy of these
    
    private String id;           // Student ID like "S001"
    private String firstName;    // First name like "John"
    private String lastName;     // Last name like "Smith"
    private String major;        // Major like "Computer Science"
    private String year;         // Year like "Freshman", "Sophomore"
    private String email;        // Email address
    private List<GradeRecord> grades;  // List of all grades
    private boolean enrolled;    // true if already enrolled for next year
    
    // ==========================================
    // CONSTRUCTORS
    // ==========================================
    // A constructor is a special method that creates a new object
    
    /**
     * Default constructor - creates an empty student
     */
    public Student() {
        // Initialize the grades list as empty
        this.grades = new ArrayList<GradeRecord>();
        // Student is not enrolled by default
        this.enrolled = false;
    }
    
    /**
     * Constructor with all details - creates a student with given info
     * 
     * @param id        The student ID
     * @param firstName The first name
     * @param lastName  The last name
     * @param major     The major/program
     * @param year      The year (Freshman, Sophomore, etc.)
     * @param email     The email address
     */
    public Student(String id, String firstName, String lastName, 
                   String major, String year, String email) {
        // Store all the values
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.year = year;
        this.email = email;
        
        // Initialize empty grades list
        this.grades = new ArrayList<GradeRecord>();
        
        // Not enrolled by default
        this.enrolled = false;
    }
    
    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================
    // Getters let us read the private fields
    // Setters let us change the private fields
    // This is called "Encapsulation" - protecting data
    
    public String getId() { 
        return id; 
    }
    
    public void setId(String id) { 
        this.id = id; 
    }
    
    public String getFirstName() { 
        return firstName; 
    }
    
    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }
    
    public String getMajor() { 
        return major; 
    }
    
    public void setMajor(String major) { 
        this.major = major; 
    }
    
    public String getYear() { 
        return year; 
    }
    
    public void setYear(String year) { 
        this.year = year; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public List<GradeRecord> getGrades() { 
        return grades; 
    }
    
    public boolean isEnrolled() { 
        return enrolled; 
    }
    
    public void setEnrolled(boolean enrolled) { 
        this.enrolled = enrolled; 
    }
    
    // ==========================================
    // HELPER METHODS
    // ==========================================
    
    /**
     * Get the full name of the student
     * @return First name + space + Last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Add a grade record to this student
     * @param grade The grade to add
     */
    public void addGrade(GradeRecord grade) {
        grades.add(grade);
    }
    
    // ==========================================
    // CGPA CALCULATION
    // ==========================================
    
    /**
     * Calculate the student's CGPA (Cumulative Grade Point Average)
     * 
     * CGPA is calculated as:
     * Sum of (Grade Points × Credits) / Total Credits
     * 
     * Example:
     * - Course 1: A (4.0) × 3 credits = 12 points
     * - Course 2: B (3.0) × 4 credits = 12 points
     * - Total: 24 points / 7 credits = 3.43 CGPA
     * 
     * @return The calculated CGPA
     */
    public double calculateCGPA() {
        // If no grades, CGPA is 0
        if (grades.isEmpty()) {
            return 0.0;
        }
        
        // Variables to keep track of totals
        double totalPoints = 0.0;
        int totalCredits = 0;
        
        // Loop through all grades using a simple for loop
        for (int i = 0; i < grades.size(); i++) {
            // Get the grade at position i
            GradeRecord grade = grades.get(i);
            
            // Get the grade point (A=4.0, B=3.0, etc.)
            double gradePoint = grade.getGradePoint();
            
            // Get the credits for this course
            int credits = grade.getCredits();
            
            // Add to our totals
            // Grade points × credits gives us quality points
            totalPoints = totalPoints + (gradePoint * credits);
            totalCredits = totalCredits + credits;
        }
        
        // Avoid dividing by zero
        if (totalCredits == 0) {
            return 0.0;
        }
        
        // Calculate CGPA
        double cgpa = totalPoints / totalCredits;
        
        // Round to 2 decimal places
        // Math.round rounds to nearest integer, so we multiply by 100 first
        cgpa = Math.round(cgpa * 100.0) / 100.0;
        
        return cgpa;
    }
    
    // ==========================================
    // FAILED COURSES COUNT
    // ==========================================
    
    /**
     * Count how many courses the student has failed
     * A grade of "F" means the student failed
     * 
     * @return Number of failed courses
     */
    public int countFailedCourses() {
        int count = 0;
        
        // Loop through all grades
        for (int i = 0; i < grades.size(); i++) {
            GradeRecord grade = grades.get(i);
            
            // Check if the grade is "F" (failed)
            // equalsIgnoreCase means "F" and "f" are both considered equal
            if (grade.getGrade().equalsIgnoreCase("F")) {
                count = count + 1;
            }
        }
        
        return count;
    }
    
    // ==========================================
    // ELIGIBILITY CHECK
    // ==========================================
    
    /**
     * Check if the student is eligible for enrollment
     * 
     * ELIGIBILITY RULES:
     * 1. CGPA must be >= 2.0 (at least a C average)
     * 2. Failed courses must be <= 3 (no more than 3 failures)
     * 
     * @return true if eligible, false if not
     */
    public boolean isEligible() {
        double cgpa = calculateCGPA();
        int failedCourses = countFailedCourses();
        
        // Check both conditions
        boolean hasSufficientCGPA = (cgpa >= 2.0);
        boolean hasAcceptableFailures = (failedCourses <= 3);
        
        // Both conditions must be true
        return hasSufficientCGPA && hasAcceptableFailures;
    }
    
    /**
     * Get a text description of the student's eligibility status
     * This is shown in the table
     * 
     * @return Status text like "Eligible" or "Ineligible (Low CGPA)"
     */
    public String getEligibilityStatus() {
        // Calculate the values
        double cgpa = calculateCGPA();
        int failed = countFailedCourses();
        
        // Check different conditions
        if (enrolled) {
            return "Already Enrolled";
        }
        
        if (cgpa >= 2.0 && failed <= 3) {
            return "Eligible";
        }
        
        if (cgpa < 2.0 && failed > 3) {
            return "Ineligible (Low CGPA & Too Many Failures)";
        }
        
        if (cgpa < 2.0) {
            return "Ineligible (Low CGPA)";
        }
        
        return "Ineligible (Too Many Failures)";
    }
}
