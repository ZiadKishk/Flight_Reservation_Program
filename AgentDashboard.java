import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AgentDashboard extends JFrame {
    public Agent agent;

    public AgentDashboard(Agent agent) {
        this.agent = agent;
        setTitle("Flight Booking System - Agent Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 204));

        JLabel welcomeLabel = new JLabel("Welcome, Agent " + agent.getName() + "!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            agent.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });
        headerPanel.add(logoutButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        // Flight Management tab
        JPanel flightManagementPanel = new JPanel(new BorderLayout());
        flightManagementPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Flight form
        JPanel flightFormPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField flightNumberField = new JTextField();
        JTextField airlineField = new JTextField();
        JTextField originField = new JTextField();
        JTextField destinationField = new JTextField();
        JTextField departureField = new JTextField();
        JTextField arrivalField = new JTextField();
        JTextField totalSeatsField = new JTextField();
        JTextField economyPriceField = new JTextField();
        JTextField businessPriceField = new JTextField();
        JTextField firstClassPriceField = new JTextField();
        JButton addFlightButton = new JButton("Add Flight");

        flightFormPanel.add(new JLabel("Flight Number:"));
        flightFormPanel.add(flightNumberField);
        flightFormPanel.add(new JLabel("Airline:"));
        flightFormPanel.add(airlineField);
        flightFormPanel.add(new JLabel("Origin:"));
        flightFormPanel.add(originField);
        flightFormPanel.add(new JLabel("Destination:"));
        flightFormPanel.add(destinationField);
        flightFormPanel.add(new JLabel("Departure Time:"));
        flightFormPanel.add(departureField);
        flightFormPanel.add(new JLabel("Arrival Time:"));
        flightFormPanel.add(arrivalField);
        flightFormPanel.add(new JLabel("Total Seats:"));
        flightFormPanel.add(totalSeatsField);
        flightFormPanel.add(new JLabel("Economy Price:"));
        flightFormPanel.add(economyPriceField);
        flightFormPanel.add(new JLabel("Business Price:"));
        flightFormPanel.add(businessPriceField);
        flightFormPanel.add(new JLabel("First Class Price:"));
        flightFormPanel.add(firstClassPriceField);
        flightFormPanel.add(new JLabel());
        flightFormPanel.add(addFlightButton);

        flightManagementPanel.add(flightFormPanel, BorderLayout.NORTH);

        // Flights table
        JTable flightsTable = new JTable();
        JScrollPane flightsScrollPane = new JScrollPane(flightsTable);
        flightManagementPanel.add(flightsScrollPane, BorderLayout.CENTER);

        JButton refreshFlightsButton = new JButton("Refresh Flights");
        refreshFlightsButton.addActionListener(e -> {
            List<Flight> flights = BookingSystem.getInstance().getFlights();
            String[] columnNames = {"Flight Number", "Airline", "Route", "Departure", "Arrival", "Available Seats"};
            Object[][] data = new Object[flights.size()][9];

            for (int i = 0; i < flights.size(); i++) {
                Flight flight = flights.get(i);
                data[i][0] = flight.getFlightNumber();
                data[i][1] = flight.getAirline();
                data[i][2] = flight.getOrigin() + " to " + flight.getDestination();
                data[i][3] = flight.getDepartureTime();
                data[i][4] = flight.getArrivalTime();
                data[i][5] = flight.getAvailableSeats();
                data[i][6] = flight.getEconomyPrice();
                data[i][7] = flight.getBusinessPrice();
                data[i][8] = flight.getFirstClassPrice();
            }

            flightsTable.setModel(new javax.swing.table.DefaultTableModel(
                    data, columnNames
            ));
        });

        flightManagementPanel.add(refreshFlightsButton, BorderLayout.SOUTH);

        addFlightButton.addActionListener(e -> {
            try {
                Flight flight = agent.addFlight(
                        flightNumberField.getText(),
                        airlineField.getText(),
                        originField.getText(),
                        destinationField.getText(),
                        departureField.getText(),
                        arrivalField.getText(),
                        Integer.parseInt(totalSeatsField.getText()),
                        Double.parseDouble(economyPriceField.getText()),
                        Double.parseDouble(businessPriceField.getText()),
                        Double.parseDouble(firstClassPriceField.getText())
                );
                JOptionPane.showMessageDialog(this, "Flight added successfully!");
                refreshFlightsButton.doClick();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid numbers for seats and prices",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tabbedPane.addTab("Flight Management", flightManagementPanel);

        // Customer Bookings tab
        JPanel customerBookingsPanel = new JPanel(new BorderLayout());
        customerBookingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search customer form
        JPanel customerSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JTextField customerIdField = new JTextField(15);
        JButton searchCustomerButton = new JButton("Search Customer");
        customerSearchPanel.add(new JLabel("Customer ID:"));
        customerSearchPanel.add(customerIdField);
        customerSearchPanel.add(searchCustomerButton);
        customerBookingsPanel.add(customerSearchPanel, BorderLayout.NORTH);

        // Bookings table
        JTable customerBookingsTable = new JTable();
        JScrollPane customerBookingsScrollPane = new JScrollPane(customerBookingsTable);
        customerBookingsPanel.add(customerBookingsScrollPane, BorderLayout.CENTER);

        searchCustomerButton.addActionListener(e -> {
            List<Booking> bookings = BookingSystem.getInstance().getBookingsForCustomer(customerIdField.getText());
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

            customerBookingsTable.setModel(new javax.swing.table.DefaultTableModel(
                    data, columnNames
            ));
        });

        tabbedPane.addTab("Customer Bookings", customerBookingsPanel);

        // Profile tab
        JPanel profilePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField(agent.getName());
        JTextField emailField = new JTextField(agent.getEmail());
        JTextField contactField = new JTextField(agent.getContactInfo());
        JTextField departmentField = new JTextField(agent.getDepartment());
        JTextField commissionField = new JTextField(String.valueOf(agent.getCommission()));
        JButton updateProfileButton = new JButton("Update Profile");

        profilePanel.add(new JLabel("Name:"));
        profilePanel.add(nameField);
        profilePanel.add(new JLabel("Email:"));
        profilePanel.add(emailField);
        profilePanel.add(new JLabel("Contact Info:"));
        profilePanel.add(contactField);
        profilePanel.add(new JLabel("Department:"));
        profilePanel.add(departmentField);
        profilePanel.add(new JLabel("Commission:"));
        profilePanel.add(commissionField);
        profilePanel.add(new JLabel());
        profilePanel.add(updateProfileButton);

        updateProfileButton.addActionListener(e -> {
            agent.updateProfile(
                    nameField.getText(),
                    emailField.getText(),
                    contactField.getText()
            );
            agent.setDepartment(departmentField.getText());
            try {
                agent.setCommission(Double.parseDouble(commissionField.getText()));
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for commission",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tabbedPane.addTab("My Profile", profilePanel);

        add(tabbedPane, BorderLayout.CENTER);


    }
}
