package irctc.ticket.entities;

public class Ticket {
    private String ticketId;
    private String trainNumber;
    private String passengerName;
    private String sourceStation;
    private String destinationStation;
    private String seatNumber;
    private String travelDate;
    private Train Train;
    private double fare;

    // Constructor to initialize ticket details
    public Ticket(String ticketId, String trainNumber, String passengerName, String sourceStation,
            String destinationStation, String seatNumber, String travelDate, double fare, Train train) {
        this.ticketId = ticketId;
        this.trainNumber = trainNumber;
        this.passengerName = passengerName;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.seatNumber = seatNumber;
        this.travelDate = travelDate;
        this.fare = fare;
        this.Train = train;
    }

    // Default constructor
    public Ticket() {
    }

    // get ticket details
    public String getTicketDetails() {
        return String.format(
                "Ticket ID: %s, Train Number: %s, Passenger Name: %s, Source: %s, Destination: %s, Seat: %s, Date: %s, Fare: %.2f, Train: %s",
                ticketId, trainNumber, passengerName, sourceStation, destinationStation, seatNumber, travelDate, fare,
                Train);
    }

    // Getters and Setters
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public Train getTrain() {
        return Train;
    }

    public void setTrain(Train train) {
        this.Train = train;
    }
}
