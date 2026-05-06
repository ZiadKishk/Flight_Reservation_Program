import javax.swing.*;


class flightbookingsystem {


    public static void main(String[] args) {
        // Initialize with some sample data
        BookingSystem bookingSystem = BookingSystem.getInstance();

        // Load users from file
//        User user = FileHandler.parseUserFromLine(String.valueOf(("users.txt")));


        // Add an admin user
        Administrator admin = new Administrator(
                "ADMN001", "admin", "admin123", "System Admin",
                "admin@airline.com", "1234567890", "ADMN001", 1
        );
        bookingSystem.addUser(admin);

        // Add an agent
        Agent agent = new Agent(
                "AGNT001", "agent1", "agent123", "Travel Agent",
                "agent@airline.com", "0987654321", "AGNT001", "Sales", 5.0
        );
        bookingSystem.addUser(agent);

        // Add a customer
        Customer customer = new Customer(
                "CUST001", "customer1", "customer123", "John Doe",
                "john@example.com", "5551234567", "CUST001", "123 Main St", "Window seat preferred"
        );
        bookingSystem.addUser(customer);

        // Add some flights
        Flight flight1 = new Flight(
                "FL101", "EgyptAir", "CAI", "LHR",
                "2025-12-01 08:00", "2025-12-01 12:00",
                150, 400.0, 800.0, 1200.0
        );
        bookingSystem.addFlight(flight1);

        Flight flight2 = new Flight(
                "FL202", "British Airways", "LHR", "JFK",
                "2025-12-05 14:00", "2025-12-05 18:00",
                200, 500.0, 1000.0, 1500.0
        );
        bookingSystem.addFlight(flight2);

        // Start the application
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}