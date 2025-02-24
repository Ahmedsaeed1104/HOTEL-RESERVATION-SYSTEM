package HOTEL_1;

import javax.swing.*;

public class MAIN {
    public static void main(String[] args) {
       
    	// Initialize required files
        DatabaseHandler.initializeFiles();
    	// Create the main frame for the application
        JFrame frame = new JFrame("Hotel Reservation System");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Center the frame
        frame.setResizable(false); 
        // Initially, load the SignInPanel
        frame.add(new LoginPanel(frame));
 
        // Set the frame to be visible
        frame.setVisible(true);
    }
}
