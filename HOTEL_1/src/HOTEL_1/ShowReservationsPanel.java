package HOTEL_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class ShowReservationsPanel extends JPanel {
    private final JTable reservationsTable;
    private final JScrollPane scrollPane;

    public ShowReservationsPanel(JFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = createLabel("Reservations List", new Font("Segoe UI", Font.BOLD, 32), new Color(33, 33, 33));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Reservations Display Table
        String[] columnNames = {"Room", "Guest", "From", "To"};
        Object[][] data = {}; // Empty data initially
        reservationsTable = new JTable(data, columnNames);
        reservationsTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        reservationsTable.setRowHeight(30);
        reservationsTable.setBackground(Color.WHITE);
        reservationsTable.setForeground(new Color(33, 33, 33));
        reservationsTable.setGridColor(new Color(200, 200, 200));
        reservationsTable.setSelectionBackground(new Color(0, 128, 128));
        reservationsTable.setSelectionForeground(Color.WHITE);
        reservationsTable.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(reservationsTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        gbc.gridy = 1;
        add(scrollPane, gbc);

        // Buttons
        JButton loadReservationsButton = createButton("Load Reservations", new Color(0, 128, 128), e -> loadReservations());
        JButton homeButton = createButton("Home", new Color(128, 0, 0), e -> navigateToHome(frame));

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(loadReservationsButton, gbc);

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

    private void loadReservations() {
        try {
            ArrayList<String> reservations = DatabaseHandler.loadReservations();
            Object[][] tableData = new Object[reservations.size()][4];

            for (int i = 0; i < reservations.size(); i++) {
                String[] reservationInfo = reservations.get(i).split(" \\| ");
                for (int j = 0; j < reservationInfo.length; j++) {
                    tableData[i][j] = reservationInfo[j].split(":")[1].trim();
                }
            }

            String[] columnNames = {"Room", "Guest", "From", "To"};
            reservationsTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading reservations.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToHome(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }
}
