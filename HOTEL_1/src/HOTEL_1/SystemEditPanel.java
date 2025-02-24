package HOTEL_1;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class SystemEditPanel extends JPanel {

    private JTextField roomNumberField;
    private JComboBox<String> statusComboBox;
    private JTable roomTable; // Replace JTextArea with JTable
    private DefaultTableModel tableModel; // Table model to manage data
    private ArrayList<String> rooms;
    private JComboBox<String> roomSelectComboBox;
    private JTextField updatedRoomNumberField;
    private JComboBox<String> updatedStatusComboBox;

    public SystemEditPanel(JFrame frame) {
        setLayout(null); // Use free-position layout
        setBackground(new Color(245, 245, 245)); // Light gray background

        rooms = new ArrayList<>(); // Store room information

        // Title Label
        JLabel titleLabel = createLabel("System Management", new Font("Segoe UI", Font.BOLD, 32), new Color(33, 33, 33));
        titleLabel.setBounds(50, 20, 400, 40); // Position and size
        add(titleLabel);

        // Instruction Label
        JLabel instructionLabel = createLabel("Add or Edit Rooms:", new Font("Segoe UI", Font.PLAIN, 18), new Color(33, 33, 33));
        instructionLabel.setBounds(150, 80, 200, 30); // Position and size
        add(instructionLabel);

        // Room Number Label
        JLabel roomNumberLabel = createLabel("Room Number:", new Font("Segoe UI", Font.PLAIN, 16), new Color(33, 33, 33));
        roomNumberLabel.setBounds(150, 130, 120, 30); // Position and size
        add(roomNumberLabel);

        // Room Number Field
        roomNumberField = new JTextField();
        roomNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roomNumberField.setBounds(280, 130, 200, 30); // Position and size
        add(roomNumberField);

        // Room Status Label
        JLabel statusLabel = createLabel("Room Status:", new Font("Segoe UI", Font.PLAIN, 16), new Color(33, 33, 33));
        statusLabel.setBounds(150, 180, 120, 30); // Position and size
        add(statusLabel);

        // Room Status ComboBox
        statusComboBox = new JComboBox<>(new String[]{"Available", "Maintenance"});
        statusComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusComboBox.setBounds(280, 180, 200, 30); // Position and size
        add(statusComboBox);

        // Room List Display Area (Replacing the JTextArea with JTable)
        String[] columnNames = {"Room Number", "Status"}; // Column headers
        tableModel = new DefaultTableModel(columnNames, 0); // Initialize table model
        roomTable = new JTable(tableModel);
        roomTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roomTable.setRowHeight(25); // Set row height
        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setBounds(550, 130, 300, 380); // Position and size
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        add(scrollPane);

        // Add Room Button
        JButton addRoomButton = createButton("Add Room", new Color(0, 128, 0), e -> addRoom());
        addRoomButton.setBounds(150, 230, 330, 40); // Position and size
        add(addRoomButton);

        // Update Room Button
        JButton updateRoomButton = createButton("Update Room", new Color(0, 128, 0), e -> showUpdateRoomDialog());
        updateRoomButton.setBounds(150, 290, 330, 40); // Position and size
        add(updateRoomButton);

        // Remove Guest Button
        JButton removeGuestButton = createButton("Remove Guest", new Color(0, 128, 128), e -> removeGuest());
        removeGuestButton.setBounds(150, 350, 330, 40); // Position and size
        add(removeGuestButton);

        // Remove Reservation Button
        JButton removeReservationButton = createButton("Remove Reservation", new Color(0, 128, 128), e -> removeReservation());
        removeReservationButton.setBounds(150, 410, 330, 40); // Position and size
        add(removeReservationButton);

        // Home Button
        JButton homeButton = createButton("Home", new Color(128, 0, 0), e -> navigateToHome(frame));
        homeButton.setBounds(150, 470, 330, 40); // Position and size
        add(homeButton);

        // Load existing rooms from file on startup
        loadExistingRooms();
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
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

    private void addRoom() {
        String roomNumber = roomNumberField.getText();
        String roomStatus = (String) statusComboBox.getSelectedItem();

        if (!roomNumber.isEmpty()) {
            if (isRoomNumberUnique(roomNumber)) {
                String roomInfo = roomNumber + " - " + roomStatus;
                rooms.add(roomInfo); // Add the room to the in-memory list
                saveRoom(roomInfo);  // Save to file
                loadExistingRooms(); // Reload the rooms from the file to reflect the updated state
                updateRoomList();    // Update the room list in the table
                clearFields();       // Clear input fields after adding
            } else {
                JOptionPane.showMessageDialog(null, "Room number already exists. Please enter a unique room number.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a room number.");
        }
    }

    void showUpdateRoomDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Update Room");
        dialog.setSize(400, 300);
        dialog.setLocation(500, 200);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel selectRoomLabel = createLabel("Select Room:", new Font("Segoe UI", Font.PLAIN, 16), new Color(33, 33, 33));
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(selectRoomLabel, gbc);

        roomSelectComboBox = new JComboBox<>();
        for (String room : rooms) {
            roomSelectComboBox.addItem(room);
        }
        roomSelectComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roomSelectComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        dialog.add(roomSelectComboBox, gbc);

        JLabel updatedRoomNumberLabel = createLabel("Updated Room Number:", new Font("Segoe UI", Font.PLAIN, 16), new Color(33, 33, 33));
        gbc.gridy = 1;
        gbc.gridx = 0;
        dialog.add(updatedRoomNumberLabel, gbc);

        updatedRoomNumberField = new JTextField();
        updatedRoomNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        updatedRoomNumberField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        dialog.add(updatedRoomNumberField, gbc);

        JLabel updatedStatusLabel = createLabel("Updated Status:", new Font("Segoe UI", Font.PLAIN, 16), new Color(33, 33, 33));
        gbc.gridy = 2;
        gbc.gridx = 0;
        dialog.add(updatedStatusLabel, gbc);

        updatedStatusComboBox = new JComboBox<>(new String[]{"Available", "Occupied", "BOOKED", "Maintenance"});
        updatedStatusComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        updatedStatusComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        dialog.add(updatedStatusComboBox, gbc);

        JButton updateButton = createButton("Update", new Color(0, 128, 128), e -> updateRoom(dialog));
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        dialog.add(updateButton, gbc);

        dialog.setVisible(true);
    }

    private void updateRoom(JDialog dialog) {
        String selectedRoom = (String) roomSelectComboBox.getSelectedItem();
        String updatedRoomNumber = updatedRoomNumberField.getText();
        String updatedStatus = (String) updatedStatusComboBox.getSelectedItem();

        if (!updatedRoomNumber.isEmpty()) {
            if (isRoomNumberUnique(updatedRoomNumber) || selectedRoom.contains(updatedRoomNumber)) {
                // Find the index of the selected room in the list
                int index = rooms.indexOf(selectedRoom);
                if (index != -1) { // Ensure the room is found in the list
                    // Replace the old room info with the updated info
                    String updatedRoomInfo = updatedRoomNumber + " - " + updatedStatus;
                    rooms.set(index, updatedRoomInfo); // Update the room in its original position
                    updateRoomList(); // Refresh the table
                    saveUpdatedRooms(); // Save the updated rooms to file
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Room not found in the list.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Room number already exists. Please enter a unique room number.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a room number.");
        }
    }

    private void navigateToHome(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }

    private void loadExistingRooms() {
        try {
            ArrayList<String> loadedRooms = DatabaseHandler.loadData("rooms.txt");
            rooms.clear();
            rooms.addAll(loadedRooms); // Load rooms in the order they appear in the file
            updateRoomList(); // Update the table without sorting
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading rooms: " + e.getMessage());
        }
    }

    private void updateRoomList() {
        tableModel.setRowCount(0); // Clear the table
        for (String room : rooms) {
            String[] parts = room.split(" - "); // Split room info into columns
            tableModel.addRow(parts); // Add room data to the table in the original order
        }
    }

    private int extractRoomNumber(String roomInfo) {
        String[] parts = roomInfo.split(" ");
        try {
            return Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void clearFields() {
        roomNumberField.setText("");
        statusComboBox.setSelectedIndex(0);
    }

    private void saveRoom(String roomInfo) {
        DatabaseHandler.saveData("rooms.txt", new ArrayList<>(rooms));
    }

    private boolean isRoomNumberUnique(String roomNumber) {
        for (String room : rooms) {
            if (room.startsWith(roomNumber + " - ")) {
                return false;
            }
        }
        return true;
    }

    private void saveUpdatedRooms() {
        DatabaseHandler.saveData("rooms.txt", new ArrayList<>(rooms));
    }

    private void removeGuest() {
        List<String> guests = readFile("guests.txt");
        if (guests.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No guests available.");
            return;
        }

        String selectedGuest = (String) JOptionPane.showInputDialog(
                this, "Select a guest to remove:", "Remove Guest",
                JOptionPane.QUESTION_MESSAGE, null,
                guests.toArray(), guests.get(0));

        if (selectedGuest != null) {
            guests.remove(selectedGuest);
            writeFile("guests.txt", guests);
            JOptionPane.showMessageDialog(this, "Guest removed successfully.");
        }
    }

    private void removeReservation() {
        List<String> reservations = readFile("reservations.txt");
        if (reservations.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No reservations available.");
            return;
        }

        String selectedReservation = (String) JOptionPane.showInputDialog(
                this, "Select a reservation to remove:", "Remove Reservation",
                JOptionPane.QUESTION_MESSAGE, null,
                reservations.toArray(), reservations.get(0));

        if (selectedReservation != null) {
            reservations.remove(selectedReservation);
            writeFile("reservations.txt", reservations);
            JOptionPane.showMessageDialog(this, "Reservation removed successfully.");
        }
    }

    private List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + filename);
        }
        return lines;
    }

    private void writeFile(String filename, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing file: " + filename);
        }
    }
}