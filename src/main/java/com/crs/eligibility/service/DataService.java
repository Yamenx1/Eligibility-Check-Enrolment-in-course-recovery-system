package com.crs.eligibility.service;

// Import our model classes
import com.crs.eligibility.model.Student;
import com.crs.eligibility.model.Course;
import com.crs.eligibility.model.GradeRecord;
import com.crs.eligibility.model.Enrollment;

// Import file handling classes
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// Import list and map
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ============================================
 * DATA SERVICE CLASS
 * ============================================
 * 
 * This class handles all data operations:
 * 1. Loading students from CSV file
 * 2. Loading courses from CSV file
 * 3. Loading grades from CSV file
 * 4. Loading and saving enrollments (binary file)
 * 5. Enrolling students
 * 
 * WHAT IS A CSV FILE?
 * CSV = Comma-Separated Values
 * It's a simple text file where each line is a record
 * and values are separated by commas.
 * Example: "S001,John,Smith,Computer Science,Freshman,john@email.com"
 */
public class DataService {
    
    // ==========================================
    // FILE PATHS
    // ==========================================
    // These are the locations of our data files
    
    private static final String STUDENTS_FILE = "data/students.csv";
    private static final String COURSES_FILE = "data/courses.csv";
    private static final String GRADES_FILE = "data/grades.csv";
    private static final String ENROLLMENTS_FILE = "data/enrollments.dat";
    
    // ==========================================
    // DATA STORAGE
    // ==========================================
    // Lists to store all our data in memory
    
    private List<Student> students;      // All students
    private List<Course> courses;        // All courses
    private List<Enrollment> enrollments; // All enrollments
    
    // ==========================================
    // CONSTRUCTOR
    // ==========================================
    
    /**
     * Create a new DataService
     * Initialize all the lists as empty
     */
    public DataService() {
        // Create empty lists
        this.students = new ArrayList<Student>();
        this.courses = new ArrayList<Course>();
        this.enrollments = new ArrayList<Enrollment>();
    }
    
    // ==========================================
    // LOAD ALL DATA
    // ==========================================
    
    /**
     * Load all data from files
     * This method calls other methods to load each type of data
     */
    public void loadAllData() {
        // Step 1: Load students
        loadStudents();
        
        // Step 2: Load courses
        loadCourses();
        
        // Step 3: Load grades and add them to students
        loadGrades();
        
        // Step 4: Load previous enrollments
        loadEnrollments();
        
        // Step 5: Mark students who are already enrolled
        markEnrolledStudents();
    }
    
    /**
     * Mark students who have already been enrolled
     * by checking the enrollments list
     */
    private void markEnrolledStudents() {
        // Loop through all enrollments
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment enrollment = enrollments.get(i);
            String enrolledStudentId = enrollment.getStudentId();
            
            // Find the student and mark them as enrolled
            for (int j = 0; j < students.size(); j++) {
                Student student = students.get(j);
                if (student.getId().equals(enrolledStudentId)) {
                    student.setEnrolled(true);
                }
            }
        }
    }
    
    // ==========================================
    // LOAD STUDENTS FROM CSV
    // ==========================================
    
    /**
     * Load students from the students.csv file
     * 
     * CSV Format:
     * StudentID,FirstName,LastName,Major,Year,Email
     * S001,Fiona,Smith,Computer Science,Senior,fiona.smith@university.edu
     */
    private void loadStudents() {
        try {
            // Create a reader to read the file line by line
            BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE));
            
            // Read the first line (header) and skip it
            String header = reader.readLine();
            
            // Read each line until there are no more
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Split the line by commas
                String[] parts = line.split(",");
                
                // Make sure we have enough parts
                if (parts.length >= 6) {
                    // Create a new student with the data
                    String id = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    String major = parts[3].trim();
                    String year = parts[4].trim();
                    String email = parts[5].trim();
                    
                    Student student = new Student(id, firstName, lastName, major, year, email);
                    
                    // Add to our list
                    students.add(student);
                }
            }
            
            // Close the reader
            reader.close();
            
            // Print how many we loaded
            System.out.println("Loaded " + students.size() + " students");
            
        } catch (Exception e) {
            // If something goes wrong, print an error
            System.out.println("Error loading students: " + e.getMessage());
        }
    }
    
    // ==========================================
    // LOAD COURSES FROM CSV
    // ==========================================
    
    /**
     * Load courses from the courses.csv file
     */
    private void loadCourses() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE));
            
            // Skip header line
            reader.readLine();
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                
                if (parts.length >= 7) {
                    String courseId = parts[0].trim();
                    String courseName = parts[1].trim();
                    int credits = Integer.parseInt(parts[2].trim());
                    String semester = parts[3].trim();
                    String instructor = parts[4].trim();
                    int examWeight = Integer.parseInt(parts[5].trim());
                    int assignmentWeight = Integer.parseInt(parts[6].trim());
                    
                    Course course = new Course(courseId, courseName, credits, 
                                              semester, instructor, examWeight, assignmentWeight);
                    courses.add(course);
                }
            }
            
            reader.close();
            System.out.println("Loaded " + courses.size() + " courses");
            
        } catch (Exception e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }
    
    // ==========================================
    // LOAD GRADES FROM CSV
    // ==========================================
    
    /**
     * Load grades and link them to students
     */
    private void loadGrades() {
        // First, create a map for quick student lookup
        // A map lets us find a student by their ID quickly
        Map<String, Student> studentMap = new HashMap<String, Student>();
        
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            studentMap.put(student.getId(), student);
        }
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(GRADES_FILE));
            
            // Skip header
            reader.readLine();
            
            int gradeCount = 0;
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                
                if (parts.length >= 9) {
                    // Parse all the grade data
                    String recordId = parts[0].trim();
                    String studentId = parts[1].trim();
                    String courseId = parts[2].trim();
                    String courseName = parts[3].trim();
                    String grade = parts[4].trim();
                    int credits = Integer.parseInt(parts[5].trim());
                    String semester = parts[6].trim();
                    String academicYear = parts[7].trim();
                    int attemptNumber = Integer.parseInt(parts[8].trim());
                    
                    // Create the grade record
                    GradeRecord gradeRecord = new GradeRecord(
                        recordId, studentId, courseId, courseName, 
                        grade, credits, semester, academicYear, attemptNumber
                    );
                    
                    // Find the student and add this grade to them
                    Student student = studentMap.get(studentId);
                    if (student != null) {
                        student.addGrade(gradeRecord);
                        gradeCount++;
                    }
                }
            }
            
            reader.close();
            System.out.println("Loaded " + gradeCount + " grade records");
            
        } catch (Exception e) {
            System.out.println("Error loading grades: " + e.getMessage());
        }
    }
    
    // ==========================================
    // LOAD ENROLLMENTS (BINARY FILE)
    // ==========================================
    
    /**
     * Load enrollments from binary file
     * 
     * WHAT IS A BINARY FILE?
     * Unlike a text/CSV file, a binary file stores data
     * in a format that only the computer can read directly.
     * We use ObjectInputStream to read Java objects from it.
     */
    @SuppressWarnings("unchecked")  // This suppresses a warning about casting
    private void loadEnrollments() {
        try {
            // Check if the file exists
            File file = new File(ENROLLMENTS_FILE);
            if (!file.exists()) {
                // No previous enrollments
                return;
            }
            
            // Create an input stream to read objects
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            // Read the list of enrollments
            enrollments = (List<Enrollment>) objectIn.readObject();
            
            // Close the streams
            objectIn.close();
            fileIn.close();
            
            System.out.println("Loaded " + enrollments.size() + " enrollments");
            
        } catch (Exception e) {
            System.out.println("Error loading enrollments: " + e.getMessage());
        }
    }
    
    // ==========================================
    // SAVE ENROLLMENTS (BINARY FILE)
    // ==========================================
    
    /**
     * Save enrollments to binary file
     */
    public void saveEnrollments() {
        try {
            // Create an output stream to write objects
            FileOutputStream fileOut = new FileOutputStream(ENROLLMENTS_FILE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            
            // Write the entire list of enrollments
            objectOut.writeObject(enrollments);
            
            // Close the streams
            objectOut.close();
            fileOut.close();
            
            System.out.println("Saved " + enrollments.size() + " enrollments");
            
        } catch (Exception e) {
            System.out.println("Error saving enrollments: " + e.getMessage());
        }
    }
    
    // ==========================================
    // ENROLL A STUDENT
    // ==========================================
    
    /**
     * Enroll a student for the next academic year
     * 
     * @param student    The student to enroll
     * @param enrolledBy Who is doing the enrollment
     * @return true if successful, false if failed
     */
    public boolean enrollStudent(Student student, String enrolledBy) {
        // Check if student is valid
        if (student == null) {
            return false;
        }
        
        // Check if student is eligible
        if (!student.isEligible()) {
            return false;
        }
        
        // Check if already enrolled
        if (student.isEnrolled()) {
            return false;
        }
        
        // Create a unique enrollment ID
        // UUID creates a unique random string
        String uuid = UUID.randomUUID().toString();
        String enrollmentId = "ENR-" + uuid.substring(0, 8).toUpperCase();
        
        // Get the next academic level
        String fromLevel = student.getYear();
        String toLevel = getNextLevel(fromLevel);
        
        // Create the enrollment record
        Enrollment enrollment = new Enrollment(
            enrollmentId,
            student.getId(),
            student.getFullName(),
            fromLevel,
            toLevel,
            student.calculateCGPA(),
            student.countFailedCourses(),
            enrolledBy
        );
        
        // Add to our list
        enrollments.add(enrollment);
        
        // Mark the student as enrolled
        student.setEnrolled(true);
        
        // Save to file
        saveEnrollments();
        
        return true;
    }
    
    // ==========================================
    // HELPER METHODS
    // ==========================================
    
    /**
     * Get the next academic level for a student
     * 
     * @param currentYear Current year (Freshman, Sophomore, etc.)
     * @return Next year
     */
    public String getNextLevel(String currentYear) {
        if (currentYear == null) {
            return "Next Year";
        }
        
        // Convert to lowercase for easier comparison
        String year = currentYear.toLowerCase();
        
        if (year.equals("freshman")) {
            return "Sophomore";
        }
        
        if (year.equals("sophomore")) {
            return "Junior";
        }
        
        if (year.equals("junior")) {
            return "Senior";
        }
        
        if (year.equals("senior")) {
            return "Graduate";
        }
        
        return "Next Year";
    }
    
    /**
     * Find a student by their ID
     * 
     * @param id The student ID to search for
     * @return The student, or null if not found
     */
    public Student getStudentById(String id) {
        // Loop through all students
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId().equals(id)) {
                return student;
            }
        }
        
        // Not found
        return null;
    }
    
    // ==========================================
    // GETTERS
    // ==========================================
    
    public List<Student> getStudents() { 
        return students; 
    }
    
    public List<Course> getCourses() { 
        return courses; 
    }
    
    public List<Enrollment> getEnrollments() { 
        return enrollments; 
    }
}
