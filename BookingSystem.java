import java.util.ArrayList;
import java.util.List;

public class BookingSystem {
    private static BookingSystem instance;
    private List<User> users;
    private List<Flight> flights;
    private List<Booking> bookings;

    private BookingSystem() {
        FileHandler.initializes();
        users = FileHandler.loadUsers();
        flights = FileHandler.loadFlights();
        bookings = FileHandler.loadBookings(users, flights);
    }
//test example
    public static BookingSystem getInstance() {
        if (instance == null) {
            instance = new BookingSystem();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);

    }

    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
        FileHandler.saveFlights(flights); // Save after modification
    }

    public boolean removeFlight(String flightNumber) {
        boolean removed = flights.removeIf(flight -> flight.getFlightNumber().equals(flightNumber));
        if (removed) {
            FileHandler.saveFlights(flights); // Save after modification
        }
        return removed;
    }

    public List<Flight> searchFlights(String origin, String destination, String date) {
        List<Flight> results = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getOrigin().equalsIgnoreCase(origin) &&
                    flight.getDestination().equalsIgnoreCase(destination)) {
                results.add(flight);
            }
        }
        return results;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        FileHandler.saveBookings(bookings); // Save after modification
    }

    public Booking getBooking(String bookingReference) {
        for (Booking booking : bookings) {
            if (booking.getBookingReference().equals(bookingReference)) {
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getBookingsForCustomer(String customerId) {
        List<Booking> customerBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomer().getCustomerId().equals(customerId)) {
                customerBookings.add(booking);
            }
        }
        return customerBookings;
    }

    // Getters
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public List<Flight> getFlights() {
        return new ArrayList<>(flights);
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    // Method to explicitly save all data
    public void saveAllData() {
        FileHandler.saveUsers(users);
        FileHandler.saveFlights(flights);
        FileHandler.saveBookings(bookings);
    }
}