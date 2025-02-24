package HOTEL_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AddReservationPanel extends JPanel {
    private JTextField nameField, phoneField, idField, roomNumberField, startDateField, endDateField;
    private JTable roomTable; // Use JTable for room list
    private DefaultTableModel tableModel; // Table model for room list
    private JFrame frame;

    public AddReservationPanel(JFrame frame) {
        this.frame = frame;
        setLayout(null); // Free layout
        setBackground(new Color(245, 245, 245)); // Light gray background

        initializeUI();
        addActionListeners();
    }

    private void initializeUI() {
        // Title Label
        JLabel titleLabel = createLabel("Add Reservation", new Font("Segoe UI", Font.BOLD, 32), new Color(33, 33, 33));
        titleLabel.setBounds(50, 20, 400, 40);
        add(titleLabel);

        // Name Field
        nameField = createTextField(220, 100);
        add(createLabel("Name:", 100, 100));
        add(nameField);

        // Phone Field
        phoneField = createTextField(220, 140);
        add(createLabel("Phone:", 100, 140));
        add(phoneField);

        // ID Field
        idField = createTextField(220, 180);
        add(createLabel("ID Number:", 100, 180));
        add(idField);

        // Room Number Field
        roomNumberField = createTextField(220, 220);
        add(createLabel("Room Number:", 100, 220, 160, 25, new Font("Segoe UI", Font.PLAIN, 16))); // Increased width to 150
        add(roomNumberField);

        // Start Date Field
        startDateField = createTextField(220, 260);
        add(createLabel("Start Date:", 100, 260));
        add(startDateField);

        // End Date Field
        endDateField = createTextField(220, 300);
        add(createLabel("End Date:", 100, 300));
        add(endDateField);

        // Buttons
        JButton saveButton = createButton("Save Reservation", new Color(0, 128, 0), e -> saveReservation());
        saveButton.setBounds(220, 340, 200, 40);
        add(saveButton);

        JButton showRoomsButton = createButton("Show Rooms", new Color(0, 128, 128), e -> showRooms());
        showRoomsButton.setBounds(220, 390, 200, 40);
        add(showRoomsButton);

        JButton updateRoomButton = createButton("Update Room", new Color(0, 128, 128), e -> updateRoom());
        updateRoomButton.setBounds(220, 440, 200, 40);
        add(updateRoomButton);
        
        JButton homeButton = createButton("Home", new Color(128, 0, 0), e -> goHome());
        homeButton.setBounds(220, 490, 200, 40);
        add(homeButton);

        // Room List Table
        String[] columnNames = {"Room Number", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        roomTable = new JTable(tableModel);
        roomTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roomTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setBounds(500, 100, 300, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        add(scrollPane);
    }

    private void addActionListeners() {
        // Action listeners are already added in the button creation
    }

    private void saveReservation() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String id = idField.getText();
        String roomNumber = roomNumberField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        if (!name.isEmpty() && !phone.isEmpty() && !id.isEmpty() && !roomNumber.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
            DatabaseHandler.saveReservation(roomNumber, name, startDate, endDate);
            JOptionPane.showMessageDialog(null, "Reservation saved successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all fields.");
        }
    }

    private void goHome() {
        frame.getContentPane().removeAll();
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }

    private void showRooms() {
        try (BufferedReader reader = new BufferedReader(new FileReader("rooms.txt"))) {
            tableModel.setRowCount(0); // Clear the table
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                tableModel.addRow(parts); // Add room data to the table
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + ex.getMessage());
        }
    }

    private void updateRoom() {
        SystemEditPanel systemEditPanel = new SystemEditPanel(frame);
        systemEditPanel.showUpdateRoomDialog();
    }

    private JLabel createLabel(String text, int x, int y) {
        return createLabel(text, x, y, 100, 25, new Font("Segoe UI", Font.PLAIN, 16));
    }

    private JLabel createLabel(String text, int x, int y, int width, int height, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(font);
        label.setForeground(new Color(33, 33, 33));
        return label;
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 200, 30);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return textField;
    }

    private JButton createButton(String text, Color bgColor, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addActionListener(action);
        return button;
    }
}