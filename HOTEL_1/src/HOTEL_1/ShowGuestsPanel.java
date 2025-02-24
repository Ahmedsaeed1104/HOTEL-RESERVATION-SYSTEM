package HOTEL_1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShowGuestsPanel extends JPanel {
    private final JTable guestsTable;
    private final JScrollPane scrollPane;

    public ShowGuestsPanel(JFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245)); // Light gray background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = createLabel("Guests List", new Font("Segoe UI", Font.BOLD, 32), new Color(33, 33, 33));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Guests Display Table
        String[] columnNames = {"Name", "Phone", "ID", "Room"};
        Object[][] data = {}; // Empty data initially
        guestsTable = new JTable(data, columnNames);
        guestsTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        guestsTable.setRowHeight(30);
        guestsTable.setBackground(Color.WHITE);
        guestsTable.setForeground(new Color(33, 33, 33));
        guestsTable.setGridColor(new Color(200, 200, 200));
        guestsTable.setSelectionBackground(new Color(0, 128, 128));
        guestsTable.setSelectionForeground(Color.WHITE);
        guestsTable.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(guestsTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        gbc.gridy = 1;
        add(scrollPane, gbc);

        // Buttons
        JButton loadGuestsButton = createButton("Load Guests", new Color(0, 128, 128), e -> loadGuests());
        JButton homeButton = createButton("Home", new Color(128, 0, 0), e -> navigateToHome(frame));

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        add(loadGuestsButton, gbc);

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

    private void loadGuests() {
        try {
            ArrayList<String> guests = DatabaseHandler.loadData("guests.txt");
            Object[][] tableData = new Object[guests.size()][4];

            for (int i = 0; i < guests.size(); i++) {
                String[] guestInfo = guests.get(i).split(" \\| ");
                for (int j = 0; j < guestInfo.length; j++) {
                    tableData[i][j] = guestInfo[j].split(":")[1].trim();
                }
            }

            String[] columnNames = {"Name", "Phone", "ID", "Room"};
            guestsTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading guests.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToHome(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.add(new HomePanel(frame));
        frame.revalidate();
        frame.repaint();
    }
}