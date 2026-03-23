package com.crs.eligibility;

// Import the GUI library (Swing)
import com.crs.eligibility.gui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * ============================================
 * MAIN CLASS - THE STARTING POINT OF THE PROGRAM
 * ============================================
 * 
 * This is where our program begins.
 * When you run the program, Java looks for the main() method
 * and starts executing from there.
 * 
 * What this class does:
 * 1. Sets up the window to look nice on Windows/Mac
 * 2. Creates and shows the main application window
 */
public class Main {
    
    /**
     * The main method - this is where everything starts!
     * 
     * @param args - Command line arguments (we don't use these)
     */
    public static void main(String[] args) {
        
        // ------------------------------------------
        // STEP 1: Make the window look nice
        // ------------------------------------------
        // This makes our program look like a native Windows/Mac app
        // instead of the default Java look
        try {
            String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            // If it fails, just use the default look
            // This is not a big problem
            System.out.println("Could not set look and feel");
        }
        
        // ------------------------------------------
        // STEP 2: Create and show the main window
        // ------------------------------------------
        // SwingUtilities.invokeLater makes sure we create the window
        // on the correct thread (the Event Dispatch Thread)
        // This is important for GUI programs
        
        SwingUtilities.invokeLater(new Runnable() {
            // This run() method will be called by Swing
            public void run() {
                // Create a new main window
                MainFrame window = new MainFrame();
                
                // Make the window visible
                window.setVisible(true);
                
                // Print a message to show the program started
                System.out.println("Application started successfully!");
            }
        });
    }
}
