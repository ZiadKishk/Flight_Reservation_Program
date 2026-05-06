import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
public class CustomerDashboard extends JFrame {
    public Customer customer;
    public List<Flight> currentSearchResults = new ArrayList<>();

    public CustomerDashboard(Customer customer) {
        this.customer = customer;
        setTitle("Flight Booking System - Customer Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(14, 19, 110));

        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getName() + "!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            customer.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });
        headerPanel.add(logoutButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Search Flights tab
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search form
        JPanel searchFormPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField originField = new JTextField();
        JTextField destinationField = new JTextField();
        JTextField dateField = new JTextField();
        JButton searchButton = new JButton("Search Flights");

        searchFormPanel.add(new JLabel("From:"));
        searchFormPanel.add(originField);
        searchFormPanel.add(new JLabel("To:"));
        searchFormPanel.add(destinationField);
        searchFormPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        searchFormPanel.add(dateField);
        searchFormPanel.add(new JLabel());
        searchFormPanel.add(searchButton);

        searchPanel.add(searchFormPanel, BorderLayout.NORTH);

        // Results table
        JTable resultsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        // Booking button
        JButton bookButton = new JButton("Book Selected Flight");
        bookButton.setEnabled(false);
        searchPanel.add(bookButton, BorderLayout.SOUTH);

        // Booking refresh setup
        JPanel bookingsPanel = new JPanel(new BorderLayout());
        bookingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTable bookingsTable = new JTable();
        JScrollPane bookingsScrollPane = new JScrollPane(bookingsTable);
        bookingsPanel.add(bookingsScrollPane, BorderLayout.CENTER);
        JButton refreshBookingsButton = new JButton("Refresh Bookings");

        // Add Bookings tab early to allow use of refresh button
        tabbedPane.addTab("Search Flights", searchPanel);
        tabbedPane.addTab("My Bookings", bookingsPanel);

        searchButton.addActionListener((ActionEvent _) -> {
            currentSearchResults = customer.searchFlights(
                    originField.getText(),
                    destinationField.getText(),
                    dateField.getText()
            );

            String[] columnNames = {
                    "Flight Number", "Airline", "Departure", "Arrival",
                    "Price (Economy)", "Price (Business)", "Price (First Class)"
            };
            Object[][] data = new Object[currentSearchResults.size()][7];

            for (int i = 0; i < currentSearchResults.size(); i++) {
                Flight flight = currentSearchResults.get(i);
                data[i][0] = flight.getFlightNumber();
                data[i][1] = flight.getAirline();
                data[i][2] = flight.getDepartureTime() + " from " + flight.getOrigin();
                data[i][3] = flight.getArrivalTime() + " at " + flight.getDestination();
                data[i][4] = "$" + flight.getEconomyPrice();
                data[i][5] = "$" + flight.getBusinessPrice();
                data[i][6] = "$" + flight.getFirstClassPrice();
            }

            resultsTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
            bookButton.setEnabled(!currentSearchResults.isEmpty());
        });

        bookButton.addActionListener(e -> {
            int selectedRow = resultsTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < currentSearchResults.size()) {
                Flight selectedFlight = currentSearchResults.get(selectedRow);
                customer.bookFlight(selectedFlight);
                JOptionPane.showMessageDialog(this, "Flight booked successfully!");
                refreshBookingsButton.doClick();  // Refresh the bookings table
            } else {
                JOptionPane.showMessageDialog(this, "Please select a flight to book.");
            }
        });

        refreshBookingsButton.addActionListener(e -> {
            List<Booking> bookings = customer.viewBookings();
            String[] columnNames = {"Booking Ref", "Flight", "Status", "Passengers", "Total Price"};
            Object[][] data = new Object[bookings.size()][5];

            for (int i = 0; i < bookings.size(); i++) {
                Booking booking = bookings.get(i);
                data[i][0] = booking.getBookingReference();
                data[i][1] = booking.getFlight().getFlightNumber();
                data[i][2] = booking.getStatus();
                data[i][3] = booking.getPassengers().size();
                data[i][4] = "$" + booking.calculateTotalPrice();
            }

            bookingsTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        });

        bookingsPanel.add(refreshBookingsButton, BorderLayout.SOUTH);

        // Profile tab
        JPanel profilePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField(customer.getName());
        JTextField emailField = new JTextField(customer.getEmail());
        JTextField contactField = new JTextField(customer.getContactInfo());
        JTextField addressField = new JTextField(customer.getAddress());
        JTextField preferencesField = new JTextField(customer.getPreferences());
        JButton updateProfileButton = new JButton("Update Profile");

        profilePanel.add(new JLabel("Name:"));
        profilePanel.add(nameField);
        profilePanel.add(new JLabel("Email:"));
        profilePanel.add(emailField);
        profilePanel.add(new JLabel("Contact Info:"));
        profilePanel.add(contactField);
        profilePanel.add(new JLabel("Address:"));
        profilePanel.add(addressField);
        profilePanel.add(new JLabel("Preferences:"));
        profilePanel.add(preferencesField);
        profilePanel.add(new JLabel());
        profilePanel.add(updateProfileButton);

        updateProfileButton.addActionListener((ActionEvent e) -> {
            customer.updateProfile(
                    nameField.getText(),
                    emailField.getText(),
                    contactField.getText()
            );
            customer.setAddress(addressField.getText());
            customer.setPreferences(preferencesField.getText());
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        });

        tabbedPane.addTab("My Profile", profilePanel);
        add(tabbedPane, BorderLayout.CENTER);


    }
}


