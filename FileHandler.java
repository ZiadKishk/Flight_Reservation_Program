import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    //  paths
    private static final String USERS_ = "users.txt";
    private static final String FLIGHTS_ = "flights.txt";
    private static final String BOOKINGS_ = "bookings.txt";
    private static final String PASSENGERS_ = "passengers.txt";

    // Initialize s if they don't exist
    public static void initializes() {
        try {
          File usersFile = new File(USERS_);
          File flightsFile = new File(FLIGHTS_);
          File bookingsFile = new File(BOOKINGS_);
          File passengersFile = new File(PASSENGERS_);
        } catch (Exception e) {
            System.err.println("Error initializing s: " + e.getMessage());
        }
    }

    // Save all users to 
    public static void saveUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(USERS_)) {
            for (User user : users) {
                writeUserToWriter(writer, user);
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Add a single user to  (appends to existing data)
    public static void addUserTo(User user) {
        try (FileWriter writer = new FileWriter(USERS_, true)) {
            writeUserToWriter(writer, user);
        } catch (IOException e) {
            System.out.println("Error adding user to : " + e.getMessage());
        }
    }

    // Helper method to write user data to a FileWriter
    private static void writeUserToWriter(FileWriter writer, User user) {
        String role = "";
        String additionalInfo = "";

        if (user instanceof Customer) {
            role = "Customer";
            Customer customer = (Customer) user;
            additionalInfo = customer.getCustomerId() + "," +
                    customer.getAddress() + "," +
                    customer.getPreferences();
        } else if (user instanceof Agent) {
            role = "Agent";
            Agent agent = (Agent) user;
            additionalInfo = agent.getAgentId() + "," +
                    agent.getDepartment() + "," +
                    agent.getCommission();
        } else if (user instanceof Administrator) {
            role = "Administrator";
            Administrator admin = (Administrator) user;
            additionalInfo = admin.getAdminId() + "," +
                    admin.getSecurityLevel();
        }

        try {
            writer.write(user.getUserId() + "," +
                    user.getUsername() + "," +
                    user.getPassword() + "," +
                    user.getName() + "," +
                    user.getEmail() + "," +
                    user.getContactInfo() + "," +
                    role + "," +
                    additionalInfo+"\n");
        } catch (IOException e) {
            System.out.println("Error writing user data: " + e.getMessage());
        }
    }

    // Load all users from 
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (Scanner reader = new Scanner(USERS_)) {
            String line;
            while ((line = reader.nextLine()) != null) {
                User user = parseUserFromLine(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // Helper method to parse a user from a line of text
    public static User parseUserFromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length >= 7) {
            String userId = parts[0];
            String username = parts[1];
            String password = parts[2];
            String name = parts[3];
            String email = parts[4];
            String contactInfo = parts[5];
            String role = parts[6];

            switch (role) {
                case "Customer":
                    if (parts.length >= 10) {
                        return new Customer(userId, username, password, name, email, contactInfo,
                                parts[7], parts[8], parts[9]);
                    }
                    break;
                case "Agent":
                    if (parts.length >= 10) {
                        return new Agent(userId, username, password, name, email, contactInfo,
                                parts[7], parts[8], Double.parseDouble(parts[9]));
                    }
                    break;
                case "Administrator":
                    if (parts.length >= 9) {
                        return new Administrator(userId, username, password, name, email, contactInfo,
                                parts[7], Integer.parseInt(parts[8]));
                    }
                    break;
            }
        }
        return null;
    }

    // Save all flights to 
    public static void saveFlights(List<Flight> flights) {
        try (FileWriter writer = new FileWriter(FLIGHTS_)) {
            for (Flight flight : flights) {
                writer.write(flight.getFlightNumber() + "," +
                        flight.getAirline() + "," +
                        flight.getOrigin() + "," +
                        flight.getDestination() + "," +
                        flight.getDepartureTime() + "," +
                        flight.getArrivalTime() + "," +
                        flight.getTotalSeats() + "," +
                        flight.getAvailableSeats() + "," +
                        flight.getEconomyPrice() + "," +
                        flight.getBusinessPrice() + "," +
                        flight.getFirstClassPrice());
            }
        } catch (IOException e) {
            System.out.println("Error saving flights: " + e.getMessage());
        }
    }

    // Load all flights from 
    public static List<Flight> loadFlights() {
        List<Flight> flights = new ArrayList<>();
        try (Scanner reader = new Scanner(FLIGHTS_)) {
            String line;
            while ((line = reader.nextLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 11) {
                    Flight flight = new Flight(
                            parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], Integer.parseInt(parts[6]),
                            Double.parseDouble(parts[8]),
                            Double.parseDouble(parts[9]),
                            Double.parseDouble(parts[10]));
                    // Manually set available seats as it's not in constructor
                    flight.reserveSeats(Integer.parseInt(parts[6]) - Integer.parseInt(parts[7]));
                    flights.add(flight);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading flights: " + e.getMessage());
        }
        return flights;
    }

    // Save all bookings to 
    public static void saveBookings(List<Booking> bookings) {
        try (FileWriter writer = new FileWriter(BOOKINGS_)) {
            for (Booking booking : bookings) {
                // First write booking info
                writer.write("BOOKING:" + booking.getBookingReference() + "," +
                        booking.getCustomer().getUserId() + "," +
                        booking.getFlight().getFlightNumber() + "," +
                        booking.getSeatClass() + "," +
                        booking.getStatus());

                // Then write passengers for this booking
                for (Passenger passenger : booking.getPassengers()) {
                    writer.write("PASSENGER:" + booking.getBookingReference() + "," +
                            passenger.getPassengerId() + "," +
                            passenger.getName() + "," +
                            passenger.getPassportNumber() + "," +
                            passenger.getDateOfBirth() + "," +
                            passenger.getSpecialRequests());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    // Load all bookings
    public static List<Booking> loadBookings(List<User> users, List<Flight> flights) {
        List<Booking> bookings = new ArrayList<>();
        Booking currentBooking = null;

        try (Scanner reader = new Scanner(BOOKINGS_)) {
            String line;
            while ((line = reader.nextLine()) != null) {
                if (line.startsWith("BOOKING:")) {
                    String[] parts = line.substring(8).split(",");
                    if (parts.length == 5) {
                        String bookingRef = parts[0];
                        String customerId = parts[1];
                        String flightNumber = parts[2];
                        String seatClass = parts[3];
                        String status = parts[4];

                        // Find customer and flight
                        Customer customer = findCustomer(users, customerId);
                        Flight flight = findFlight(flights, flightNumber);

                        if (customer != null && flight != null) {
                            currentBooking = new Booking(bookingRef, customer, flight,
                                    new ArrayList<>(), seatClass);
                            currentBooking.status = status;
                            bookings.add(currentBooking);
                        }
                    }
                } else if (line.startsWith("PASSENGER:") && currentBooking != null) {
                    String[] parts = line.substring(10).split(",");
                    if (parts.length == 6) {
                        Passenger passenger = new Passenger(
                                parts[1], parts[2], parts[3], parts[4], parts[5]);
                        currentBooking.getPassengers().add(passenger);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
        return bookings;
    }

    // Helper method to find customer by ID
    private static Customer findCustomer(List<User> users, String customerId) {
        for (User user : users) {
            if (user instanceof Customer && user.getUserId().equals(customerId)) {
                return (Customer) user;
            }
        }
        return null;
    }

    // Helper method to find flight by number
    private static Flight findFlight(List<Flight> flights, String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }




    public static List<String> readFrom(String filePath) {
        List<String> lines = new ArrayList<>();
        try (Scanner reader = new Scanner(new File(filePath))) {
            while (reader.hasNextLine()) {  // Check if there's another line to read
                String line = reader.nextLine();
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filePath);
        } catch (Exception e) {
            System.out.println("Error reading from " + filePath + ": " + e.getMessage());
        }
        return lines;
    }



}