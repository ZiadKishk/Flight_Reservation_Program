import java.util.List;

public class Agent extends User {
    private String agentId;
    private String department;
    private double commission;

    public Agent(String userId, String username, String password, String name, String email, String contactInfo,
                 String agentId, String department, double commission) {
        super(userId, username, password, name, email, contactInfo);
        this.agentId = agentId;
        this.department = department;
        this.commission = commission;
    }

    @Override
    public void showDashboard() {
        // Implementation for agent dashboard
        System.out.println("Agent Dashboard");
    }

    @Override
    public void bookFlight(Flight selectedFlight) {

    }

    public Flight addFlight(String flightNumber, String airline, String origin, String destination,
                            String departureTime, String arrivalTime, int totalSeats,
                            double economyPrice, double businessPrice, double firstClassPrice) {
        Flight flight = new Flight(
                flightNumber,
                airline,
                origin,
                destination,
                departureTime,
                arrivalTime,
                totalSeats,
                economyPrice,
                businessPrice,
                firstClassPrice
        );
        BookingSystem.getInstance().addFlight(flight);
        return flight;
    }

    public boolean removeFlight(String flightNumber) {
        return BookingSystem.getInstance().removeFlight(flightNumber);
    }

    public Booking createBookingForCustomer(Customer customer, Flight flight, List<Passenger> passengers, String seatClass) {
        Booking booking = new Booking(
                "B" + System.currentTimeMillis(),
                customer,
                flight,
                passengers,
                seatClass
        );
        BookingSystem.getInstance().addBooking(booking);
        return booking;
    }

    public boolean modifyBooking(String bookingReference, Flight newFlight, String newSeatClass) {
        Booking booking = BookingSystem.getInstance().getBooking(bookingReference);
        if (booking != null) {
            booking.setFlight(newFlight);
            booking.setSeatClass(newSeatClass);
            return true;
        }
        return false;
    }



    // Getters and setters
    public String getAgentId() {
        return agentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }
}