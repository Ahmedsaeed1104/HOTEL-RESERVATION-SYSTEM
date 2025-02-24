package HOTEL_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HomePanel extends JPanel {

    private BufferedImage backgroundImage;
    private float backgroundOpacity = 0.5f; // Adjust this value for opacity (0.0f is fully transparent, 1.0f is fully opaque)

    public HomePanel(JFrame frame) {
        setLayout(null);  // Use null layout for absolute positioning

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("A:\\HH.png"));  // Replace with the path to your image
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Font settings
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);

        // Color scheme - Blue and White
        Color buttonColor = new Color(70, 130, 180); // Steel Blue color for buttons
        Color buttonTextColor = Color.WHITE;         // White text for buttons
        Color borderColor = new Color(0, 0, 139);    // Dark Blue color for borders
        Color titleColor = new Color(30, 144, 255);  // Dodger Blue for title text
        Color buttonColor2 = new Color(128, 0, 0);  // Dodger Blue for title text

        // Title label setup
        JLabel titleLabel = new JLabel("");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(titleColor);  // Dodger Blue color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(300, 30, 300, 50);  // Set position and size
        add(titleLabel);

        // Button setup
        JButton addGuestButton = createCustomButton("Add Guest", buttonColor, buttonTextColor, borderColor, buttonFont);
        JButton showGuestsButton = createCustomButton("Show Guests", buttonColor, buttonTextColor, borderColor, buttonFont);
        JButton showRoomsButton = createCustomButton("Show Rooms", buttonColor, buttonTextColor, borderColor, buttonFont);
        JButton addReservationButton = createCustomButton("Add Reservation", buttonColor, buttonTextColor, borderColor, buttonFont);
        JButton showReservationsButton = createCustomButton("Show Reservations", buttonColor, buttonTextColor, borderColor, buttonFont);
        JButton systemEditButton = createCustomButton("System Edit", buttonColor, buttonTextColor, borderColor, buttonFont);

        // Set bounds (position and size) for each button
        int buttonWidth = 210;
        int buttonHeight = 40;
        int xPos = 150;  // Horizontal center point for buttons
        int yPos = 100;  // Starting vertical position

        
        showGuestsButton.setBounds       (50 ,  35 ,           buttonWidth, buttonHeight);
        showRoomsButton.setBounds        (620 , 35 ,           buttonWidth, buttonHeight);
        showReservationsButton.setBounds (335,  20 ,  buttonWidth, buttonHeight);
        
        addGuestButton.setBounds         (50 ,500,           buttonWidth, buttonHeight);
        systemEditButton.setBounds       (335, 500,     buttonWidth, buttonHeight);
        addReservationButton.setBounds   (620, 500 ,    buttonWidth, buttonHeight);
        
        

        // Add buttons to the panel
        add(addGuestButton);
        add(showGuestsButton);
        add(showRoomsButton);
        add(addReservationButton);
        add(showReservationsButton);
        add(systemEditButton);
        
     // Add Security Button
        JButton securityButton = createCustomButton("Security", buttonColor2, buttonTextColor, borderColor, buttonFont);
        securityButton.setBounds(335, 450, 210, 40); // Position and size
        add(securityButton);
        
        // Action listeners for buttons
        addGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(new AddGuestPanel(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        showGuestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(new ShowGuestsPanel(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        showRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(new ShowRoomsPanel(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        addReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(new AddReservationPanel(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        showReservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(new ShowReservationsPanel(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        systemEditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = JOptionPane.showInputDialog(frame, "Enter the password:");

                if (password == null) {
                    return;
                }

                try {
                    String sysEditPassword = DatabaseHandler.loadSysEditPassword();
                    if (password.equals(sysEditPassword)) {
                        frame.getContentPane().removeAll();
                        frame.add(new SystemEditPanel(frame));
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Incorrect password. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error loading system edit password: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
     // Modify the securityButton action listener in HomePanel
        securityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = JOptionPane.showInputDialog(frame, "Enter the security password:");

                if (password == null) {
                    return;
                }

                try {
                    String securityPassword = DatabaseHandler.loadSecurityPassword();
                    if (password.equals(securityPassword)) {
                        frame.getContentPane().removeAll();
                        frame.add(new SecurityPanel(frame));
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Incorrect password. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error loading security password: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Helper method to create custom buttons
    private JButton createCustomButton(String text, Color background, Color textColor, Color borderColor, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(background);
        button.setForeground(textColor);
        button.setFocusPainted(false);  // Removes the focus rectangle
        button.setBorder(BorderFactory.createLineBorder(borderColor, 2)); // Adds a border to the button
        return button;
    }

    // Override paintComponent to draw the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Set opacity for the background image
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, backgroundOpacity));

            // Draw the image to fit the entire panel
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

            g2d.dispose();
        }
    }
    
   
    
}
