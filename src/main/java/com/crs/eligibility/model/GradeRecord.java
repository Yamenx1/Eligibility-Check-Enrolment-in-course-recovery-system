package com.crs.eligibility.model;

/**
 * ============================================
 * GRADE RECORD CLASS
 * ============================================
 * 
 * This class stores information about ONE grade
 * that a student received in ONE course.
 * 
 * For example:
 * - Student S001 got an "A" in "Database Systems"
 * - This information is stored in a GradeRecord object
 * 
 * Each GradeRecord contains:
 * - Record ID (unique identifier)
 * - Student ID (who got this grade)
 * - Course ID and name
 * - The grade (A, B+, C, F, etc.)
 * - Credits for the course
 * - Semester and year
 * - Attempt number (1st try, 2nd try, etc.)
 */
public class GradeRecord {
    
    // ==========================================
    // FIELDS
    // ==========================================
    
    private String recordId;      // Unique ID like "GR001"
    private String studentId;     // Student who got this grade
    private String courseId;      // Course ID like "C201"
    private String courseName;    // Course name like "Database Systems"
    private String grade;         // Grade like "A", "B+", "F"
    private int credits;          // Credits for this course (2, 3, or 4)
    private String semester;      // "Fall", "Spring", or "Summer"
    private String academicYear;  // Like "2024-2025"
    private int attemptNumber;    // 1 = first try, 2 = retake, etc.
    
    // ==========================================
    // CONSTRUCTORS
    // ==========================================
    
    /**
     * Default constructor - creates empty record
     */
    public GradeRecord() {
        // Empty - all fields will be null or 0
    }
    
    /**
     * Constructor with all details
     */
    public GradeRecord(String recordId, String studentId, String courseId, 
                       String courseName, String grade, int credits,
                       String semester, String academicYear, int attemptNumber) {
        this.recordId = recordId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.grade = grade;
        this.credits = credits;
        this.semester = semester;
        this.academicYear = academicYear;
        this.attemptNumber = attemptNumber;
    }
    
    // ==========================================
    // GETTERS
    // ==========================================
    // We only need getters for this class
    // because we don't change grades after creating them
    
    public String getRecordId() { 
        return recordId; 
    }
    
    public String getStudentId() { 
        return studentId; 
    }
    
    public String getCourseId() { 
        return courseId; 
    }
    
    public String getCourseName() { 
        return courseName; 
    }
    
    public String getGrade() { 
        return grade; 
    }
    
    public int getCredits() { 
        return credits; 
    }
    
    public String getSemester() { 
        return semester; 
    }
    
    public String getAcademicYear() { 
        return academicYear; 
    }
    
    public int getAttemptNumber() { 
        return attemptNumber; 
    }
    
    // ==========================================
    // GRADE POINT CONVERSION
    // ==========================================
    
    /**
     * Convert letter grade to grade point value
     * 
     * GRADING SCALE:
     * A, A+ = 4.0 (Excellent)
     * A-    = 3.7
     * B+    = 3.3
     * B     = 3.0 (Good)
     * B-    = 2.7
     * C+    = 2.3
     * C     = 2.0 (Average - minimum for eligibility)
     * C-    = 1.7
     * D+    = 1.3
     * D     = 1.0 (Pass)
     * D-    = 0.7
     * F     = 0.0 (Fail)
     * 
     * @return The numeric grade point value
     */
    public double getGradePoint() {
        // Handle null grade
        if (grade == null) {
            return 0.0;
        }
        
        // Convert to uppercase and remove spaces
        String g = grade.toUpperCase().trim();
        
        // Use if-else to check each grade
        // This is simpler than using a switch statement
        
        if (g.equals("A") || g.equals("A+")) {
            return 4.0;
        }
        
        if (g.equals("A-")) {
            return 3.7;
        }
        
        if (g.equals("B+")) {
            return 3.3;
        }
        
        if (g.equals("B")) {
            return 3.0;
        }
        
        if (g.equals("B-")) {
            return 2.7;
        }
        
        if (g.equals("C+")) {
            return 2.3;
        }
        
        if (g.equals("C")) {
            return 2.0;
        }
        
        if (g.equals("C-")) {
            return 1.7;
        }
        
        if (g.equals("D+")) {
            return 1.3;
        }
        
        if (g.equals("D")) {
            return 1.0;
        }
        
        if (g.equals("D-")) {
            return 0.7;
        }
        
        // F or any other grade = 0.0
        return 0.0;
    }
}
