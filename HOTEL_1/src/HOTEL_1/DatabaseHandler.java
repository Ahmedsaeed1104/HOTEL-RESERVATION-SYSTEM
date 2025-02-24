package HOTEL_1;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseHandler {

    // Initialize required files if they don't exist
	public static void initializeFiles() {
	    String[] requiredFiles = {"login.txt", "sysedit.txt", "security.txt", "guests.txt", "rooms.txt", "reservations.txt"};
	    for (String fileName : requiredFiles) {
	        File file = new File(fileName);
	        if (!file.exists()) {
	            try {
	                file.createNewFile(); // Create the file if it doesn't exist
	                System.out.println("Created file: " + fileName);

	                // Add default content to specific files
	                switch (fileName) {
	                    case "login.txt":
	                        // Add default user "00" with password "00"
	                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	                            writer.write("00:00"); // Write the default user and password
	                            writer.newLine();
	                        }
	                        break;
	                    case "sysedit.txt":
	                        // Add default system edit password "0000"
	                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	                            writer.write("0000"); // Write the default password
	                            writer.newLine();
	                        }
	                        break;
	                    case "security.txt":
	                        // Add default security password "0000"
	                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	                            writer.write("0000"); // Write the default password
	                            writer.newLine();
	                        }
	                        break;
	                    default:
	                        // No default content for other files
	                        break;
	                }
	            } catch (IOException e) {
	                System.out.println("Error creating file " + fileName + ": " + e.getMessage());
	            }
	        }
	    }
	}

    // Load data from a specified file and return as an ArrayList of strings
    public static ArrayList<String> loadData(String fileName) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        }
        return data;
    }

    // Save a new guest's information to the guests file
    public static void saveGuest(String name, String phone, String idNumber, String ROOMNumber) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("guests.txt", true))) {
            writer.write("NAME:" + name + " | " + "PHONE:" + phone + " | " + "ID:" + idNumber + " | " + "ROOM:" + ROOMNumber);
            writer.newLine();
            System.out.println("Guest information saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving guest information: " + e.getMessage());
        }
    }

    // Load room data from a file and return as an ArrayList of strings
    public static ArrayList<String> loadRooms() throws IOException {
        ArrayList<String> rooms = loadData("rooms.txt");

        // Sort rooms based on the room number
        rooms.sort((room1, room2) -> {
            // Extract room numbers from the strings
            int roomNumber1 = extractRoomNumber(room1);
            int roomNumber2 = extractRoomNumber(room2);
            return Integer.compare(roomNumber1, roomNumber2); // Sort by room number in ascending order
        });

        return rooms;
    }

    // Helper method to extract the room number from the room information string
    private static int extractRoomNumber(String roomInfo) {
        // Assuming the format "Room: <roomNumber> | Status: <status> | Until: <endDate>"
        String[] parts = roomInfo.split("\\|");
        String roomNumberPart = parts[0].trim(); // "Room: <roomNumber>"
        return Integer.parseInt(roomNumberPart.replace("Room:", "").trim()); // Extract room number
    }

    // Save a new room's information to the rooms file
    public static void saveRoom(String roomNumber, String status, String endDate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rooms.txt", true))) {
            writer.write("Room: " + roomNumber + " | Status: " + status + " | Until: " + endDate);
            writer.newLine();
            System.out.println("Room information saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving room information: " + e.getMessage());
        }
    }

    // Load reservation data from a file and return as an ArrayList of strings
    public static ArrayList<String> loadReservations() throws IOException {
        return loadData("reservations.txt");
    }

    // Save a new reservation's information to the reservations file
    public static void saveReservation(String roomNumber, String guestName, String startDate, String endDate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true))) {
            writer.write("Room: " + roomNumber + " | Guest: " + guestName + " | From: " + startDate + " | To: " + endDate);
            writer.newLine();
            System.out.println("Reservation information saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservation information: " + e.getMessage());
        }
    }

    // Update the status of a room in the rooms file
    public static void updateRoomStatus(String roomNumber, String newStatus, String newEndDate) {
        try {
            File inputFile = new File("rooms.txt");
            File tempFile = new File("rooms_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean roomFound = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(roomNumber)) {
                    writer.write("Room: " + roomNumber + " | Status: " + newStatus + " | Until: " + newEndDate);
                    roomFound = true;
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
            reader.close();
            writer.close();

            if (!inputFile.delete()) {
                System.out.println("Could not delete original file.");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename temporary file.");
            }

            if (roomFound) {
                System.out.println("Room status updated successfully.");
            } else {
                System.out.println("Room not found.");
            }
        } catch (IOException e) {
            System.out.println("Error updating room status: " + e.getMessage());
        }
    }

    // Delete a room from the rooms file
    public static void deleteRoom(String roomNumber) {
        try {
            File inputFile = new File("rooms.txt");
            File tempFile = new File("rooms_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean roomDeleted = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (!parts[0].equals(roomNumber)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    roomDeleted = true;
                }
            }
            reader.close();
            writer.close();

            if (!inputFile.delete()) {
                System.out.println("Could not delete original file.");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename temporary file.");
            }

            if (roomDeleted) {
                System.out.println("Room deleted successfully.");
            } else {
                System.out.println("Room not found.");
            }
        } catch (IOException e) {
            System.out.println("Error deleting room: " + e.getMessage());
        }
    }

    public static void saveData(String fileName, ArrayList<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Data saved successfully to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving data to " + fileName + ": " + e.getMessage());
        }
    }

    // Method to remove a guest from guests.txt
    public static void removeGuest(String guestID) {
        try {
            File inputFile = new File("guests.txt");
            File tempFile = new File("guests_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean guestDeleted = false;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("ID:" + guestID)) { // Check if the line contains the guest ID
                    writer.write(line);
                    writer.newLine();
                } else {
                    guestDeleted = true;
                }
            }
            reader.close();
            writer.close();

            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Error updating guests file.");
                return;
            }

            if (guestDeleted) {
                System.out.println("Guest removed successfully.");
            } else {
                System.out.println("Guest not found.");
            }
        } catch (IOException e) {
            System.out.println("Error removing guest: " + e.getMessage());
        }
    }

    // Method to remove a reservation from reservations.txt
    public static void removeReservation(String roomNumber) {
        try {
            File inputFile = new File("reservations.txt");
            File tempFile = new File("reservations_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean reservationDeleted = false;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("Room: " + roomNumber)) { // Check if the line contains the room number
                    writer.write(line);
                    writer.newLine();
                } else {
                    reservationDeleted = true;
                }
            }
            reader.close();
            writer.close();

            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Error updating reservations file.");
                return;
            }

            if (reservationDeleted) {
                System.out.println("Reservation removed successfully.");
            } else {
                System.out.println("Reservation not found.");
            }
        } catch (IOException e) {
            System.out.println("Error removing reservation: " + e.getMessage());
        }
    }

    // Method to remove a room from rooms.txt and update the list
    public static void removeRoom(String roomNumber) {
        try {
            File inputFile = new File("rooms.txt");
            File tempFile = new File("rooms_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean roomDeleted = false;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("Room: " + roomNumber)) { // Check if the line contains the room number
                    writer.write(line);
                    writer.newLine();
                } else {
                    roomDeleted = true;
                }
            }
            reader.close();
            writer.close();

            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.out.println("Error updating rooms file.");
                return;
            }

            if (roomDeleted) {
                System.out.println("Room removed successfully.");
            } else {
                System.out.println("Room not found.");
            }
        } catch (IOException e) {
            System.out.println("Error removing room: " + e.getMessage());
        }
    }

    // Load login data from login.txt
    public static HashMap<String, String> loadLoginData() throws IOException {
        HashMap<String, String> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("login.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        }
        return users;
    }

    // Save login data to login.txt
    public static void saveLoginData(HashMap<String, String> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("login.txt"))) {
            for (String username : users.keySet()) {
                writer.write(username + ":" + users.get(username));
                writer.newLine();
            }
        }
    }

    // Load the system edit password from sysedit.txt
    public static String loadSysEditPassword() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("sysedit.txt"))) {
            return reader.readLine();
        }
    }

    // Save the system edit password to sysedit.txt
    public static void saveSysEditPassword(String password) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sysedit.txt"))) {
            writer.write(password);
        }
    }

    // Load the security password from security.txt
    public static String loadSecurityPassword() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("security.txt"))) {
            return reader.readLine();
        }
    }

    // Save the security password to security.txt
    public static void saveSecurityPassword(String password) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("security.txt"))) {
            writer.write(password);
        }
    }
}
