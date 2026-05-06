import java.util.List;

public class Booking {
    private String bookingReference;
    private Customer customer;
    private Flight flight;
    private List<Passenger> passengers;
    private String seatClass;
    public String status;
    private Payment payment;

    public Booking(String bookingReference, Customer customer, Flight flight,
                   List<Passenger> passengers, String seatClass) {
        this.bookingReference = bookingReference;
        this.customer = customer;
        this.flight = flight;
        this.passengers = passengers;
        this.seatClass = seatClass;
        this.status = "Reserved";

    }



    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public double calculateTotalPrice() {
        return flight.calculatePrice(seatClass) * passengers.size();
    }

    public boolean confirmBooking(Payment payment) {
        if (flight.reserveSeats(passengers.size())) {
            this.payment = payment;
            status = "Confirmed";
            return true;
        }
        return false;
    }

    public boolean cancelBooking() {
        if (status.equals("Confirmed")) {
            status = "Cancelled";
            return true;
        }
        return false;
    }
//trip road
    public String generateItinerary() {
        String itinerary = "";
        itinerary += "Booking Reference: " + bookingReference + "\n";
        itinerary += "Customer: " + customer.getName() + "\n";
        itinerary += "Flight: " + flight.toString() + "\n";
        itinerary += "Passengers:\n";
        for (Passenger passenger : passengers) {
            itinerary += "- " + passenger.getName() + "\n";
        }
        itinerary += "Seat Class: " + seatClass + "\n";
        itinerary += "Total Price: $" + calculateTotalPrice() + "\n";
        itinerary += "Status: " + status + "\n";
        return itinerary;
    }

    // Getters and setters
    public String getBookingReference() {
        return bookingReference;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getStatus() {
        return status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void updateStatus(String part) {
    }
}