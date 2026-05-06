public class Flight {
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int totalSeats;
    private int availableSeats;
    private double economyPrice;
    private double businessPrice;
    private double firstClassPrice;

    public Flight(String flightNumber, String airline, String origin, String destination,
                  String departureTime, String arrivalTime, int totalSeats,
                  double economyPrice, double businessPrice, double firstClassPrice) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
        this.firstClassPrice = firstClassPrice;
    }

    public boolean checkAvailability(int seatsRequested) {
        return availableSeats >= seatsRequested;
    }

    public boolean reserveSeats(int seats) {
        if (checkAvailability(seats)) {
            availableSeats -= seats;
            return true;
        }
        return false;
    }

    public double calculatePrice(String seatClass) {
        switch (seatClass.toLowerCase()) {
            case "economy":
                return economyPrice;
            case "business":
                return businessPrice;
            case "first":
                return firstClassPrice;
            default:
                return economyPrice;
        }
    }

    // Getters and setters
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getEconomyPrice() {
        return economyPrice;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public double getFirstClassPrice() {
        return firstClassPrice;
    }


    @Override
    public String toString() {
        return airline + " " + flightNumber + " from " + origin + " to " + destination +
                " (Departure: " + departureTime + ", Arrival: " + arrivalTime +  firstClassPrice+ "first class price)";
    }
}