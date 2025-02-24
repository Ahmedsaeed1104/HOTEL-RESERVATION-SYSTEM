package HOTEL_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class SecurityPanel extends JPanel {
    private JFrame frame;

    public SecurityPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout for better organization
        setBackground(new Color(245, 245, 245)); // Light gray background
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(245, 245, 245)); // Match background color
        JLabel titleLabel = new JLabel("Security Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 128, 255)); // Blue color for the title
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column, with spacing
        buttonPanel.setBackground(new Color(245, 245, 245)); // Match background color
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Add padding

        // Add User Button
        JButton addUserButton = createButton("Add User", new Color(0, 128, 0)); // Green
        addUserButton.addActionListener(e -> addUser());
        buttonPanel.add(addUserButton);

        // Remove User Button
        JButton removeUserButton = createButton("Remove User", new Color(0, 128, 128)); // Red
        removeUserButton.addActionListener(e -> removeUser());
        buttonPanel.add(removeUserButton);

        // Change System Edit Password Button
        JButton changePasswordButton = createButton("Change System Edit Password", new Color(0, 128, 128)); // Teal
        changePasswordButton.addActionListener(e -> changeSystemEditPassword());
        buttonPanel.add(changePasswordButton);

        // Change Security Password Button
        JButton changeSecurityPasswordButton = createButton("Change Security Password", new Color(0, 128, 128)); // Purple
        changeSecurityPasswordButton.addActionListener(e -> changeSecurityPassword());
        buttonPanel.add(changeSecurityPasswordButton);

        // Home Button
        JButton homeButton = createButton("Home", new Color(128, 0, 0)); // Red
        homeButton.addActionListener(e -> navigateToHome());
        buttonPanel.add(homeButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    // Method to add a new user
    private void addUser() {
        String username = JOptionPane.showInputDialog(frame, "Enter the new username:");
        if (username != null && !username.trim().isEmpty()) {
            String password = JOptionPane.showInputDialog(frame, "Enter the new password:");
            if (password != null && !password.trim().isEmpty()) {
                try {
                    HashMap<String, String> users = DatabaseHandler.loadLoginData();
                    users.put(username, password);
                    DatabaseHandler.saveLoginData(users);
                    JOptionPane.showMessageDialog(frame, "User added successfully!");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, "Error saving user data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Username cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to remove an existing user
    private void removeUser() {
        try {
            HashMap<String, String> users = DatabaseHandler.loadLoginData();
            if (users.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No users available to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] usernames = users.keySet().toArray(new String[0]);
            String selectedUser = (String) JOptionPane.showInputDialog(
                    frame, "Select a user to remove:", "Remove User",
                    JOptionPane.QUESTION_MESSAGE, null, usernames, usernames[0]);

            if (selectedUser != null) {
                users.remove(selectedUser);
                DatabaseHandler.saveLoginData(users);
                JOptionPane.showMessageDialog(frame, "User removed successfully!");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error removing user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to change the system edit password
    private void changeSystemEditPassword() {
        try {
            String oldPassword = DatabaseHandler.loadSysEditPassword();
            String enteredPassword = JOptionPane.showInputDialog(frame, "Enter the old password:");
            if (enteredPassword == null) {
                return; // User canceled the operation
            }

            if (!enteredPassword.equals(oldPassword)) {
                JOptionPane.showMessageDialog(frame, "Incorrect old password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newPassword = JOptionPane.showInputDialog(frame, "Enter the new password:");
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                DatabaseHandler.saveSysEditPassword(newPassword);
                JOptionPane.showMessageDialog(frame, "System edit password changed successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "New password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error changing password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to change the security password
    private void changeSecurityPassword() {
        try {
            String oldPassword = DatabaseHandler.loadSecurityPassword();
            String enteredPassword = JOptionPane.showInputDialog(frame, "Enter the old security password:");
            if (enteredPassword == null) {
                return; // User canceled the operation
            }

            if (!enteredPassword.equals(oldPassword)) {
                JOptionPane.showMessageDialog(frame, "Incorrect old password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newPassword = JOptionPane.showInputDialog(frame, "Enter the new security password:");
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                DatabaseHandler.saveSecurityPassword(newPassword);
                JOptionPane.showMessageDialog(frame, "Security password changed successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "New password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error changing security password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to navigate back to the HomePanel
    private void navigateToHome() {
        frame.getContentPane().removeAll();
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }

    // Helper method to create custom buttons
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        return button;
    }
}
