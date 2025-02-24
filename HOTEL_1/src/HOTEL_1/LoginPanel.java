package HOTEL_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginPanel extends JPanel {
    private JTextField nameField;
    private JPasswordField passwordField;
    private static HashMap<String, String> users = new HashMap<>(); // Static HashMap to store users and passwords

    public LoginPanel(JFrame frame) {
        setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        setBackground(new Color(245, 245, 245)); // Light gray background

        // Initialize some default users (you can add more dynamically later)
        users.put("AHMED SAEED", "12345678");
        users.put("HASSAN SAEED", "12345678");
        users.put("AHMED FAHMY", "12345678");
        users.put(" ", " ");
        // Panel to hold the login form
        JPanel loginFormPanel = new JPanel();
        loginFormPanel.setLayout(new GridBagLayout());
        loginFormPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white background
        loginFormPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));

        // Constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Title Label
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 128, 255)); // Blue color for the title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginFormPanel.add(titleLabel, gbc);

        // Username Label
        JLabel nameLabel = new JLabel("Username:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(new Color(33, 33, 33)); // Dark gray for labels
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginFormPanel.add(nameLabel, gbc);

        // Username Field
        nameField = new JTextField(15);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameField.setForeground(new Color(33, 33, 33)); // Dark gray for text
        nameField.setBackground(Color.WHITE); // White background
        nameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginFormPanel.add(nameField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordLabel.setForeground(new Color(33, 33, 33)); // Dark gray for labels
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginFormPanel.add(passwordLabel, gbc);

        // Password Field
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setForeground(new Color(33, 33, 33)); // Dark gray for text
        passwordField.setBackground(Color.WHITE); // White background
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginFormPanel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 128, 255)); // Blue button background
        loginButton.setForeground(Color.WHITE); // White text on the button
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginFormPanel.add(loginButton, gbc);

        // Add hover effect to the login button
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 102, 204)); // Darker blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 128, 255)); // Original blue when not hovered
            }
        });

        // Add action listener for login button
        ActionListener loginAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(frame);
            }
        };
        loginButton.addActionListener(loginAction);

        // Add Enter key binding for login
        bindEnterKey(frame);

        // Add the login form panel to the main panel
        add(loginFormPanel);
    }

    // Method to handle login
    private void login(JFrame frame) {
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        if (authenticate(name, password)) {
            // Redirect to HomePanel
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new HomePanel(frame));
            frame.revalidate();
            frame.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }

    // Simple authentication
 // Modify the authenticate method in LoginPanel
    private boolean authenticate(String name, String password) {
        try {
            HashMap<String, String> users = DatabaseHandler.loadLoginData();
            return users.containsKey(name) && users.get(name).equals(password);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading login data: " + e.getMessage());
            return false;
        }
    }

    // Static method to add new users and passwords
    public static void addUser(String username, String password) {
        users.put(username, password);
    }

    // Static method to get the users HashMap (for debugging or other purposes)
    public static HashMap<String, String> getUsers() {
        return users;
    }

    // Bind Enter key to login action
    private void bindEnterKey(JFrame frame) {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "login");
        actionMap.put("login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(frame);
            }
        });
    }
}