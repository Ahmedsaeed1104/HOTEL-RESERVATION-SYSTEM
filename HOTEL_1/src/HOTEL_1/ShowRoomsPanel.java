package HOTEL_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class ShowRoomsPanel extends JPanel {
    private final JTable roomsTable;
    private final JScrollPane scrollPane;

    public ShowRoomsPanel(JFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = createLabel("Room Status", new Font("Segoe UI", Font.BOLD, 32), new Color(33, 33, 33));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Rooms Display Table
        String[] columnNames = {"Room Number", "Status"};
        Object[][] data = {}; // Empty data initially
        roomsTable = new JTable(data, columnNames);
        roomsTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roomsTable.setRowHeight(30);
        roomsTable.setBackground(Color.WHITE);
        roomsTable.setForeground(new Color(33, 33, 33));
        roomsTable.setGridColor(new Color(200, 200, 200));
        roomsTable.setSelectionBackground(new Color(0, 128, 128));
        roomsTable.setSelectionForeground(Color.WHITE);
        roomsTable.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(roomsTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        gbc.gridy = 1;
        add(scrollPane, gbc);

        // Buttons
        JButton loadRoomsButton = createButton("Load Rooms", new Color(0, 128, 128), e -> loadRooms());
        JButton homeButton = createButton("Home", new Color(128, 0, 0), e -> navigateToHome(frame));

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(loadRoomsButton, gbc);

        gbc.gridx = 1;
        add(homeButton, gbc);
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

    private void loadRooms() {
        try {
            ArrayList<String> rooms = DatabaseHandler.loadData("rooms.txt");
            Object[][] tableData = new Object[rooms.size()][2]; // Only 2 columns: Room Number and Status

            for (int i = 0; i < rooms.size(); i++) {
                String roomInfo = rooms.get(i);
                // Split the room info into room number and status
                String[] parts = roomInfo.split(" - ");
                if (parts.length == 2) {
                    tableData[i][0] = parts[0].trim(); // Room Number
                    tableData[i][1] = parts[1].trim(); // Status
                }
            }

            String[] columnNames = {"Room Number", "Status"};
            roomsTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading rooms: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToHome(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }
}
