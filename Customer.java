import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String customerId;
    private String address;
    private List<Booking> bookingHistory;
    private String preferences;
    public Flight selectedFlight;

    public Customer(String userId, String username, String password, String name, String email, String contactInfo,
                    String customerId, String address, String preferences) {
        super(userId, username, password, name, email, contactInfo);
        this.customerId = customerId;
        this.address = address;
        this.preferences = preferences;
        this.bookingHistory = new ArrayList<>();
    }

    @Override
    public void showDashboard() {
        // Implementation for customer dashboard
        System.out.println("Customer Dashboard");
    }

    @Override
    public void bookFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public List<Flight> searchFlights(String origin, String destination, String date) {
        // Implementation for flight search
        return BookingSystem.getInstance().searchFlights(origin, destination, date);
    }

    public Booking createBooking(Flight flight, List<Passenger> passengers, String seatClass) {
        String bookingRef = "B" + System.currentTimeMillis();
        Booking booking = new Booking(bookingRef, this, flight, passengers, seatClass);

        bookingHistory.add(booking);
        BookingSystem.getInstance().addBooking(booking);

        return booking;
    }

    public List<Booking> viewBookings() {
        return new ArrayList<>(bookingHistory);
    }

    public boolean cancelBooking(String bookingReference) {
        for (Booking booking : bookingHistory) {
            if (booking.getBookingReference().equals(bookingReference)) {
                booking.cancelBooking();
                return true;
            }
        }
        return false;
    }

    // Getters and setters
    public String getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
}
