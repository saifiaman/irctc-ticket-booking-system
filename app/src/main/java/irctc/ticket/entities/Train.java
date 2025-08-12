package irctc.ticket.entities;

import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private String trainNumber;
    private String trainName;
    private String sourceStation;
    private String destinationStation;
    private String travelDate;
    private String departureTime;
    private String arrivalTime;
    private double fare;
    private List<List<Integer>> seatNumbers;
    private Map<String, String> stationTimes;
    private List<String> stations;

    // constructor to initialize train details
    public Train(String trainId, String trainNumber, String trainName, String sourceStation, String destinationStation,
            String travelDate, String departureTime, String arrivalTime, double fare,
            List<List<Integer>> seatNumbers, Map<String, String> stationTimes, List<String> stations) {
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.travelDate = travelDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fare = fare;
        this.seatNumbers = seatNumbers;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public Train() {
        // Default constructor for Jackson
    }

    // Getters and Setters
    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
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

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public List<List<Integer>> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<List<Integer>> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public Map<String, String> getStationTimes() {
        return stationTimes;
    }

    public void setStationTimes(Map<String, String> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }
}
