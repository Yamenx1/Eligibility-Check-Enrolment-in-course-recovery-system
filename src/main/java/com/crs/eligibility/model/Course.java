package com.crs.eligibility.model;

/**
 * ============================================
 * COURSE CLASS
 * ============================================
 * 
 * This class stores information about a course.
 * Each course has:
 * - An ID and name
 * - Number of credits
 * - Which semester it's offered
 * - Who teaches it
 * - How the grade is calculated (exam vs assignment weights)
 */
public class Course {
    
    // ==========================================
    // FIELDS
    // ==========================================
    
    private String courseId;        // Like "C201"
    private String courseName;      // Like "Database Systems"
    private int credits;            // 2, 3, or 4 credits
    private String semester;        // "Fall", "Spring", or "Summer"
    private String instructor;      // Like "Dr. Miller"
    private int examWeight;         // Exam percentage (e.g., 60)
    private int assignmentWeight;   // Assignment percentage (e.g., 40)
    
    // ==========================================
    // CONSTRUCTORS
    // ==========================================
    
    /**
     * Default constructor
     */
    public Course() {
        // Empty
    }
    
    /**
     * Constructor with all details
     */
    public Course(String courseId, String courseName, int credits, 
                  String semester, String instructor, 
                  int examWeight, int assignmentWeight) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.semester = semester;
        this.instructor = instructor;
        this.examWeight = examWeight;
        this.assignmentWeight = assignmentWeight;
    }
    
    // ==========================================
    // GETTERS
    // ==========================================
    
    public String getCourseId() { 
        return courseId; 
    }
    
    public String getCourseName() { 
        return courseName; 
    }
    
    public int getCredits() { 
        return credits; 
    }
    
    public String getSemester() { 
        return semester; 
    }
    
    public String getInstructor() { 
        return instructor; 
    }
    
    public int getExamWeight() { 
        return examWeight; 
    }
    
    public int getAssignmentWeight() { 
        return assignmentWeight; 
    }
    
    /**
     * Get a nice display name for the course
     * Example: "Database Systems (C201)"
     */
    public String getDisplayName() {
        return courseName + " (" + courseId + ")";
    }
}
