package com.crs.eligibility.gui;

// Import our model and service classes
import com.crs.eligibility.model.Student;
import com.crs.eligibility.model.GradeRecord;
import com.crs.eligibility.model.Enrollment;
import com.crs.eligibility.service.DataService;

// Import Swing GUI classes
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;

// Import AWT classes for layouts and colors
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Import List for storing data
import java.util.List;

/**
 * ============================================
 * MAIN FRAME CLASS
 * ============================================
 * 
 * This is the main window of our application.
 * It shows:
 * 1. A header with the application title
 * 2. Two tabs:
 *    - Eligibility Check: Shows students and their eligibility
 *    - Enrollment History: Shows all previous enrollments
 * 
 * WHAT IS A JFRAME?
 * JFrame is a window container. It's the main window that
 * holds all our buttons, tables, and other components.
 * 
 * WHAT IS A JPANEL?
 * JPanel is a container that holds other components.
 * We use panels to organize our layout.
 */
public class MainFrame extends JFrame {
    
    // ==========================================
    // FIELDS
    // ==========================================
    
    // The data service handles all data operations
    private DataService dataService;
    
    // Student table and its data model
    private JTable studentTable;
    private DefaultTableModel tableModel;
    
    // Search and filter controls
    private JTextField searchField;
    private JComboBox<String> filterCombo;
    
    // Enrollment history table and its model
    private JTable enrollmentTable;
    private DefaultTableModel enrollmentModel;
    
    // ==========================================
    // CONSTRUCTOR
    // ==========================================
    
    /**
     * Create the main window
     */
    public MainFrame() {
        // Create the data service
        this.dataService = new DataService();
        
        // Load all data from files
        dataService.loadAllData();
        
        // Set up the window
        setupWindow();
        
        // Create all the components
        createComponents();
        
        // Fill the tables with data
        refreshStudentTable();
        refreshEnrollmentTable();
    }
    
    // ==========================================
    // WINDOW SETUP
    // ==========================================
    
    /**
     * Set up the window properties
     */
    private void setupWindow() {
        // Set the title that appears in the title bar
        setTitle("Course Recovery System - Eligibility Check & Enrolment");
        
        // When user clicks X, close the program
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the window size (width, height)
        setSize(1150, 750);
        
        // Center the window on screen
        setLocationRelativeTo(null);
    }
    
    // ==========================================
    // CREATE ALL COMPONENTS
    // ==========================================
    
    /**
     * Create all the GUI components
     */
    private void createComponents() {
        // Use BorderLayout for the main window
        // BorderLayout has 5 areas: NORTH, SOUTH, EAST, WEST, CENTER
        setLayout(new BorderLayout());
        
        // Create and add the header panel at the top
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Create the tabbed pane (container for tabs)
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(250, 250, 250));
        
        // Add the Eligibility Check tab
        JPanel eligibilityPanel = createEligibilityPanel();
        tabbedPane.addTab("  Eligibility Check  ", eligibilityPanel);
        
        // Add the Enrollment History tab
        JPanel enrollmentPanel = createEnrollmentPanel();
        tabbedPane.addTab("  Enrollment History  ", enrollmentPanel);
        
        // Add the tabbed pane to the center of the window
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    // ==========================================
    // HEADER PANEL
    // ==========================================
    
    /**
     * Create the header panel with the title
     */
    private JPanel createHeaderPanel() {
        // Create a panel with BorderLayout
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        // Set the background color to blue
        headerPanel.setBackground(new Color(52, 152, 219));
        
        // Set the height
        headerPanel.setPreferredSize(new Dimension(0, 65));
        
        // Add padding around the edges
        headerPanel.setBorder(new EmptyBorder(12, 25, 12, 25));
        
        // Create the title label
        JLabel titleLabel = new JLabel("Course Recovery System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Create the subtitle on the right
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);  // Transparent background
        
        JLabel subtitleLabel = new JLabel("Eligibility Check & Enrolment");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(236, 240, 241));
        rightPanel.add(subtitleLabel);
        
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    // ==========================================
    // ELIGIBILITY PANEL
    // ==========================================
    
    /**
     * Create the eligibility check panel
     * This shows the student table and controls
     */
    private JPanel createEligibilityPanel() {
        // Create the main panel with BorderLayout
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(250, 250, 250));
        
        // Create the top panel with search and filter
        JPanel topPanel = createSearchFilterPanel();
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Create the student table
        JScrollPane tableScrollPane = createStudentTable();
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Create the button panel at the bottom
        JPanel buttonPanel = createButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Create the search and filter panel
     */
    private JPanel createSearchFilterPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        // Search label
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchLabel.setForeground(new Color(52, 73, 94));
        topPanel.add(searchLabel);
        
        // Search text field
        searchField = new JTextField(18);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        
        // Add key listener to filter as user types
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filterTable();
            }
        });
        topPanel.add(searchField);
        
        // Add some space
        topPanel.add(Box.createHorizontalStrut(30));
        
        // Filter label
        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        filterLabel.setForeground(new Color(52, 73, 94));
        topPanel.add(filterLabel);
        
        // Filter dropdown
        String[] filterOptions = {"All Students", "Eligible", "Ineligible", "Already Enrolled"};
        filterCombo = new JComboBox<String>(filterOptions);
        filterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filterCombo.setPreferredSize(new Dimension(150, 30));
        
        // Add action listener to filter when selection changes
        filterCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterTable();
            }
        });
        topPanel.add(filterCombo);
        
        return topPanel;
    }
    
    /**
     * Create the student table
     */
    private JScrollPane createStudentTable() {
        // Define the column names
        String[] columns = {"ID", "Name", "Major", "Year", "CGPA", "Failed", "Status"};
        
        // Create the table model
        // This holds the data for the table
        tableModel = new DefaultTableModel(columns, 0) {
            // Make cells not editable
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Create the table with the model
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        studentTable.setRowHeight(32);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setGridColor(new Color(220, 220, 220));
        studentTable.setShowGrid(true);
        
        // Set up the table header
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        studentTable.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        // Create custom header renderer for blue color
        studentTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                // Create a label for the header cell
                JLabel label = new JLabel(value.toString());
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setBackground(new Color(41, 128, 185));  // Blue
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, new Color(30, 100, 160)));
                return label;
            }
        });
        
        // Create custom row renderer for row colors
        studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                // Call the parent method first
                Component comp = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                
                // Get the status from column 6
                String status = "";
                try {
                    status = (String) tableModel.getValueAt(row, 6);
                } catch (Exception e) {
                    status = "";
                }
                
                // Set colors based on selection and status
                if (isSelected) {
                    // Selected row - blue
                    comp.setBackground(new Color(52, 152, 219));
                    comp.setForeground(Color.WHITE);
                } else {
                    // Not selected - color based on status
                    comp.setForeground(new Color(44, 62, 80));
                    
                    if (status.equals("Eligible")) {
                        // Green for eligible
                        comp.setBackground(new Color(212, 239, 223));
                    } else if (status.equals("Already Enrolled")) {
                        // Blue for enrolled
                        comp.setBackground(new Color(214, 234, 248));
                    } else if (status.contains("Ineligible")) {
                        // Red for ineligible
                        comp.setBackground(new Color(250, 219, 216));
                    } else {
                        // White for others
                        comp.setBackground(Color.WHITE);
                    }
                }
                
                // Center align CGPA and Failed columns
                if (column == 4 || column == 5) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }
                
                return comp;
            }
        });
        
        // Wrap the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
        return scrollPane;
    }
    
    /**
     * Create the button panel with action buttons
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        buttonPanel.setBackground(new Color(250, 250, 250));
        
        // View Details button - Blue
        JButton viewBtn = createStyledButton("View Details", 
            new Color(52, 152, 219), new Color(41, 128, 185), 130, 38);
        viewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewStudentDetails();
            }
        });
        buttonPanel.add(viewBtn);
        
        // Enroll Student button - Green
        JButton enrollBtn = createStyledButton("Enroll Student", 
            new Color(46, 204, 113), new Color(39, 174, 96), 140, 38);
        enrollBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enrollStudent();
            }
        });
        buttonPanel.add(enrollBtn);
        
        // Refresh button - Purple
        JButton refreshBtn = createStyledButton("Refresh", 
            new Color(155, 89, 182), new Color(142, 68, 173), 110, 38);
        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshStudentTable();
                refreshEnrollmentTable();
                JOptionPane.showMessageDialog(MainFrame.this, 
                    "Data refreshed!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(refreshBtn);
        
        return buttonPanel;
    }
    
    // ==========================================
    // ENROLLMENT HISTORY PANEL
    // ==========================================
    
    /**
     * Create the enrollment history panel
     */
    private JPanel createEnrollmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(250, 250, 250));
        
        // Title at the top
        JLabel titleLabel = new JLabel("Enrollment History");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(new EmptyBorder(0, 5, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Create the enrollment table
        String[] columns = {"Enrollment ID", "Student Name", "From", "To", "CGPA", "Date", "Enrolled By"};
        enrollmentModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        enrollmentTable = new JTable(enrollmentModel);
        enrollmentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        enrollmentTable.setRowHeight(32);
        enrollmentTable.setGridColor(new Color(220, 220, 220));
        enrollmentTable.setShowGrid(true);
        
        // Set up the header
        enrollmentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        enrollmentTable.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        // Custom header renderer for green color
        enrollmentTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setBackground(new Color(39, 174, 96));  // Green
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, new Color(30, 140, 70)));
                return label;
            }
        });
        
        // Custom row renderer for alternating colors
        enrollmentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component comp = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    comp.setBackground(new Color(39, 174, 96));
                    comp.setForeground(Color.WHITE);
                } else {
                    comp.setForeground(new Color(44, 62, 80));
                    // Alternate row colors (zebra stripes)
                    if (row % 2 == 0) {
                        comp.setBackground(Color.WHITE);
                    } else {
                        comp.setBackground(new Color(245, 245, 245));
                    }
                }
                
                return comp;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(enrollmentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ==========================================
    // HELPER: CREATE STYLED BUTTON
    // ==========================================
    
    /**
     * Create a nice-looking button with hover effect
     * 
     * @param text        Button text
     * @param normalColor Normal background color
     * @param hoverColor  Background color when mouse hovers
     * @param width       Button width
     * @param height      Button height
     * @return The styled button
     */
    private JButton createStyledButton(String text, Color normalColor, Color hoverColor, 
                                        int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(normalColor);
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add mouse listener for hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
        
        return button;
    }
    
    // ==========================================
    // REFRESH TABLES
    // ==========================================
    
    /**
     * Refresh the student table with latest data
     */
    private void refreshStudentTable() {
        // Clear all rows
        tableModel.setRowCount(0);
        
        // Get all students from the data service
        List<Student> students = dataService.getStudents();
        
        // Add each student as a row
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            
            // Create an array with the row data
            Object[] rowData = {
                student.getId(),
                student.getFullName(),
                student.getMajor(),
                student.getYear(),
                String.format("%.2f", student.calculateCGPA()),  // Format to 2 decimals
                student.countFailedCourses(),
                student.getEligibilityStatus()
            };
            
            // Add the row to the table
            tableModel.addRow(rowData);
        }
        
        // Repaint to show the colors
        studentTable.repaint();
    }
    
    /**
     * Refresh the enrollment history table
     */
    private void refreshEnrollmentTable() {
        // Clear all rows
        enrollmentModel.setRowCount(0);
        
        // Get all enrollments
        List<Enrollment> enrollments = dataService.getEnrollments();
        
        // Add each enrollment as a row
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment enrollment = enrollments.get(i);
            
            Object[] rowData = {
                enrollment.getEnrollmentId(),
                enrollment.getStudentName(),
                enrollment.getFromLevel(),
                enrollment.getToLevel(),
                String.format("%.2f", enrollment.getCgpa()),
                enrollment.getFormattedDate(),
                enrollment.getEnrolledBy()
            };
            
            enrollmentModel.addRow(rowData);
        }
    }
    
    // ==========================================
    // FILTER TABLE
    // ==========================================
    
    /**
     * Filter the student table based on search and filter settings
     */
    private void filterTable() {
        // Get the search text (lowercase for case-insensitive search)
        String searchText = searchField.getText().toLowerCase().trim();
        
        // Get the selected filter
        String selectedFilter = (String) filterCombo.getSelectedItem();
        
        // Clear the table
        tableModel.setRowCount(0);
        
        // Get all students
        List<Student> students = dataService.getStudents();
        
        // Check each student
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            String status = student.getEligibilityStatus();
            
            // Check if student matches the filter
            boolean matchesFilter = false;
            
            if (selectedFilter.equals("All Students")) {
                matchesFilter = true;
            } else if (selectedFilter.equals("Eligible") && status.equals("Eligible")) {
                matchesFilter = true;
            } else if (selectedFilter.equals("Ineligible") && status.contains("Ineligible")) {
                matchesFilter = true;
            } else if (selectedFilter.equals("Already Enrolled") && status.equals("Already Enrolled")) {
                matchesFilter = true;
            }
            
            // Check if student matches the search
            boolean matchesSearch = false;
            
            if (searchText.isEmpty()) {
                matchesSearch = true;
            } else {
                // Check if any field contains the search text
                String id = student.getId().toLowerCase();
                String name = student.getFullName().toLowerCase();
                String major = student.getMajor().toLowerCase();
                
                if (id.contains(searchText) || name.contains(searchText) || major.contains(searchText)) {
                    matchesSearch = true;
                }
            }
            
            // If both conditions match, add the student to the table
            if (matchesFilter && matchesSearch) {
                Object[] rowData = {
                    student.getId(),
                    student.getFullName(),
                    student.getMajor(),
                    student.getYear(),
                    String.format("%.2f", student.calculateCGPA()),
                    student.countFailedCourses(),
                    status
                };
                tableModel.addRow(rowData);
            }
        }
        
        // Repaint to show the colors
        studentTable.repaint();
    }
    
    // ==========================================
    // VIEW STUDENT DETAILS
    // ==========================================
    
    /**
     * Show details for the selected student
     */
    private void viewStudentDetails() {
        // Get the selected row
        int selectedRow = studentTable.getSelectedRow();
        
        // Check if a row is selected
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a student first.", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get the student ID from the first column
        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
        
        // Find the student
        Student student = dataService.getStudentById(studentId);
        if (student == null) {
            return;
        }
        
        // Build the details text
        StringBuilder details = new StringBuilder();
        
        // Header
        details.append("========================================\n");
        details.append("            STUDENT DETAILS\n");
        details.append("========================================\n\n");
        
        // Personal information
        details.append("ID:       " + student.getId() + "\n");
        details.append("Name:     " + student.getFullName() + "\n");
        details.append("Major:    " + student.getMajor() + "\n");
        details.append("Year:     " + student.getYear() + "\n");
        details.append("Email:    " + student.getEmail() + "\n\n");
        
        // Grades section
        details.append("========================================\n");
        details.append("               GRADES\n");
        details.append("========================================\n\n");
        
        List<GradeRecord> grades = student.getGrades();
        for (int i = 0; i < grades.size(); i++) {
            GradeRecord grade = grades.get(i);
            
            // Mark failed courses
            String failedMark = "";
            if (grade.getGrade().equalsIgnoreCase("F")) {
                failedMark = " [FAILED]";
            }
            
            // Format: Course Name    Grade (Credits)
            String line = String.format("%-25s %s (%d cr)%s\n",
                grade.getCourseName(), 
                grade.getGrade(), 
                grade.getCredits(),
                failedMark);
            details.append(line);
        }
        
        // Eligibility section
        details.append("\n========================================\n");
        details.append("          ELIGIBILITY STATUS\n");
        details.append("========================================\n\n");
        details.append("CGPA:     " + String.format("%.2f", student.calculateCGPA()) + " (minimum: 2.0)\n");
        details.append("Failed:   " + student.countFailedCourses() + " (maximum: 3)\n");
        details.append("Status:   " + student.getEligibilityStatus() + "\n");
        
        // Create a text area to display the details
        JTextArea textArea = new JTextArea(details.toString());
        textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setBackground(new Color(250, 250, 250));
        
        // Put in a scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(480, 450));
        
        // Show in a dialog
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Student Details - " + student.getFullName(), 
            JOptionPane.PLAIN_MESSAGE);
    }
    
    // ==========================================
    // ENROLL STUDENT
    // ==========================================
    
    /**
     * Enroll the selected student for the next year
     */
    private void enrollStudent() {
        // Get the selected row
        int selectedRow = studentTable.getSelectedRow();
        
        // Check if a row is selected
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a student first.", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get the student ID
        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
        
        // Find the student
        Student student = dataService.getStudentById(studentId);
        if (student == null) {
            JOptionPane.showMessageDialog(this, 
                "Error: Student not found.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if already enrolled
        if (student.isEnrolled()) {
            JOptionPane.showMessageDialog(this, 
                student.getFullName() + " is already enrolled for next year.", 
                "Already Enrolled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Check eligibility
        double cgpa = student.calculateCGPA();
        int failedCourses = student.countFailedCourses();
        
        if (cgpa < 2.0 || failedCourses > 3) {
            // Build message explaining why not eligible
            String message = "========================================\n";
            message += "      NOT ELIGIBLE FOR ENROLLMENT\n";
            message += "========================================\n\n";
            message += "Student: " + student.getFullName() + "\n\n";
            message += "CGPA: " + String.format("%.2f", cgpa) + " (need >= 2.0)\n";
            message += "Failed: " + failedCourses + " (max 3 allowed)\n\n";
            message += "Status: " + student.getEligibilityStatus();
            
            JOptionPane.showMessageDialog(this, message, 
                "Not Eligible", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Ask for confirmation
        String nextLevel = dataService.getNextLevel(student.getYear());
        
        String confirmMessage = "========================================\n";
        confirmMessage += "         CONFIRM ENROLLMENT\n";
        confirmMessage += "========================================\n\n";
        confirmMessage += "Student: " + student.getFullName() + "\n";
        confirmMessage += "From: " + student.getYear() + " -> To: " + nextLevel + "\n\n";
        confirmMessage += "CGPA: " + String.format("%.2f", cgpa) + "\n";
        confirmMessage += "Failed Courses: " + failedCourses + "\n\n";
        confirmMessage += "Proceed with enrollment?";
        
        int confirm = JOptionPane.showConfirmDialog(this, confirmMessage,
            "Confirm Enrollment", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        // If user clicked Yes
        if (confirm == JOptionPane.YES_OPTION) {
            // Perform the enrollment
            boolean success = dataService.enrollStudent(student, "System");
            
            if (success) {
                // Show success message
                String successMessage = "========================================\n";
                successMessage += "       ENROLLMENT SUCCESSFUL!\n";
                successMessage += "========================================\n\n";
                successMessage += student.getFullName() + "\n";
                successMessage += "has been enrolled for " + nextLevel + "!";
                
                JOptionPane.showMessageDialog(this, successMessage,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the tables
                refreshStudentTable();
                refreshEnrollmentTable();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error: Could not enroll student.\nPlease try again.", 
                    "Enrollment Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
