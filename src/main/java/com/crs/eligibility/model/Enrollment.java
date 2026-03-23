package com.crs.eligibility.model;

// For saving to file and date/time
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ============================================
 * ENROLLMENT CLASS
 * ============================================
 * 
 * This class records when a student is enrolled for the next year.
 * 
 * When we enroll a student, we save:
 * - Enrollment ID (unique identifier)
 * - Student info (ID and name)
 * - What year they're moving from and to
 * - Their CGPA and failed courses at time of enrollment
 * - Who enrolled them and when
 * 
 * WHAT IS Serializable?
 * It means this object can be saved to a file and loaded back.
 * We use this to save enrollments to a file (enrollments.dat)
 */
public class Enrollment implements Serializable {
    
    // This ID helps Java know the version of the class
    // It's used when saving/loading from files
    private static final long serialVersionUID = 1L;
    
    // ==========================================
    // FIELDS
    // ==========================================
    
    private String enrollmentId;    // Like "ENR-ABC123"
    private String studentId;       // Student who was enrolled
    private String studentName;     // Student's full name
    private String fromLevel;       // Year before (e.g., "Freshman")
    private String toLevel;         // Year after (e.g., "Sophomore")
    private double cgpa;            // CGPA at time of enrollment
    private int failedCourses;      // Failed courses at time of enrollment
    private String enrolledBy;      // Who performed the enrollment
    private LocalDateTime enrollmentDate;  // When it happened
    
    // ==========================================
    // CONSTRUCTORS
    // ==========================================
    
    /**
     * Default constructor
     */
    public Enrollment() {
        // Empty
    }
    
    /**
     * Constructor with all details
     */
    public Enrollment(String enrollmentId, String studentId, String studentName,
                      String fromLevel, String toLevel, double cgpa, 
                      int failedCourses, String enrolledBy) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.fromLevel = fromLevel;
        this.toLevel = toLevel;
        this.cgpa = cgpa;
        this.failedCourses = failedCourses;
        this.enrolledBy = enrolledBy;
        
        // Set the current date and time
        this.enrollmentDate = LocalDateTime.now();
    }
    
    // ==========================================
    // GETTERS
    // ==========================================
    
    public String getEnrollmentId() { 
        return enrollmentId; 
    }
    
    public String getStudentId() { 
        return studentId; 
    }
    
    public String getStudentName() { 
        return studentName; 
    }
    
    public String getFromLevel() { 
        return fromLevel; 
    }
    
    public String getToLevel() { 
        return toLevel; 
    }
    
    public double getCgpa() { 
        return cgpa; 
    }
    
    public int getFailedCourses() { 
        return failedCourses; 
    }
    
    public String getEnrolledBy() { 
        return enrolledBy; 
    }
    
    public LocalDateTime getEnrollmentDate() { 
        return enrollmentDate; 
    }
    
    /**
     * Get the date formatted nicely for display
     * Example: "2024-12-10 14:30"
     */
    public String getFormattedDate() {
        if (enrollmentDate == null) {
            return "";
        }
        
        // Create a formatter with our desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        // Format the date
        return enrollmentDate.format(formatter);
    }
}
