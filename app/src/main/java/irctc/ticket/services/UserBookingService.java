package irctc.ticket.services;

import irctc.ticket.entities.Ticket;
import irctc.ticket.entities.Train;
import irctc.ticket.entities.User;
import irctc.ticket.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserBookingService {

    private List<User> userList;
    private List<Train> trainList;
    private User user;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "/Users/amansaifi/Desktop/Coding/SpringBoot/irctc-ticket-booking-system/app/src/main/java/irctc/localDb/users.json";
    private static final String TRAINS_PATH = "/Users/amansaifi/Desktop/Coding/SpringBoot/irctc-ticket-booking-system/app/src/main/java/irctc/localDb/trains.json"; // <--
                                                                                                                                                                    // Add
                                                                                                                                                                    // this

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        this.userList = loadUsers();
        this.trainList = loadTrains();
    }

    public UserBookingService() throws IOException {
        this.userList = loadUsers();
        this.trainList = loadTrains();
    }

    // Load trains from file
    public List<Train> loadTrains() {
        File trains = new File(TRAINS_PATH);
        if (!trains.exists()) {
            System.out.println("Train file doesn't exist at: " + trains.getAbsolutePath());
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(trains, new TypeReference<List<Train>>() {
            });
        } catch (IOException e) {
            System.out.println("Error reading train file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Load data from file
    public List<User> loadUsers() throws IOException {
        File users = new File(USERS_PATH);
        if (!users.exists()) {
            System.out.println("User file doesn't exist at: " + users.getAbsolutePath());
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(users, new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
            throw e;
        }
    }

    // Login User code
    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream()
                .filter(u -> u.getUsername().equals(user.getUsername())
                        && UserServiceUtil.checkPassword(user.getPassword(), u.getHashedPassword()))
                .findFirst();
        if (foundUser.isPresent()) {
            this.user = foundUser.get(); // <-- Set the actual user with tickets!
            return true;
        }
        return false;
    }

    // SignUp or register User code
    public boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException e) {
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File user = new File(USERS_PATH);
        objectMapper.writeValue(user, userList);
    }

    public void fetchBooking() {
        user.printTickets();
    }

    public boolean cancelBooking(String tickedId) {
        try {
            userList.removeIf(u -> u.getBookedTickets().removeIf(ticket -> ticket.getTicketId().equals(tickedId)));
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            if (trainList == null) {
                trainList = loadTrains();
            }
            // Filter trains by source and destination (case-insensitive)
            List<Train> result = new ArrayList<>();
            for (Train t : trainList) {
                List<String> stations = t.getStations();
                if (stations != null && !stations.isEmpty()) {
                    int sourceIdx = -1, destIdx = -1;
                    for (int i = 0; i < stations.size(); i++) {
                        if (stations.get(i).equalsIgnoreCase(source))
                            sourceIdx = i;
                        if (stations.get(i).equalsIgnoreCase(destination))
                            destIdx = i;
                    }
                    if (sourceIdx != -1 && destIdx != -1 && sourceIdx < destIdx) {
                        result.add(t);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error searching for trains: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<List<Integer>> fetchSeats(Train train) {
        return train.getSeatNumbers();
    }

    // Select a seat for a train
    public void selectSeat(Train train, int row, int column) {
        // In a real application, seat data should be persisted and validated
        List<List<Integer>> seats = fetchSeats(train);
        if (row >= 0 && row < seats.size() && column >= 0 && column < seats.get(row).size()) {
            if (seats.get(row).get(column) == 0) {
                seats.get(row).set(column, 1); // 1 means booked
                // Persist seat selection if needed
            } else {
                System.out.println("Seat already booked.");
            }
        } else {
            System.out.println("Invalid seat selection.");
        }
        System.out
                .println("Seat selected for train " + train.getTrainNumber() + " at row " + row + ", column " + column);
    }

    public boolean isUserLoggedIn() {
        return this.user != null;
    }

    // Book a seat for a train
    public Boolean bookTrainSeat(Train trainSelectedForBooking, int row, int seat) {
        try {
            // Reload the latest train list for real-time data
            this.trainList = loadTrains();

            // Find the train in the list by trainId
            Train trainToBook = this.trainList.stream()
                    .filter(t -> t.getTrainId() != null && t.getTrainId().equals(trainSelectedForBooking.getTrainId()))
                    .findFirst()
                    .orElse(null);

            if (trainToBook == null) {
                System.out.println("Train not found!");
                return Boolean.FALSE;
            }

            List<List<Integer>> seats = trainToBook.getSeatNumbers();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 1) {
                    System.out.println("❌ This seat is already booked. Please choose another seat.");
                    return Boolean.FALSE;
                }
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    trainToBook.setSeatNumbers(seats);

                    // Persist the updated train list
                    saveTrainListToFile();

                    // Create and add ticket to user
                    Ticket ticket = new Ticket();
                    ticket.setSourceStation(trainToBook.getStations().getFirst());
                    ticket.setDestinationStation(trainToBook.getStations().getLast());
                    ticket.setTrain(trainToBook);
                    ticket.setPassengerName(user.getUsername());
                    ticket.setTravelDate(trainToBook.getTravelDate());
                    ticket.setTicketId(UserServiceUtil.generateTicketId());

                    user.getBookedTickets().add(ticket);

                    saveUserListToFile();
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE; // Seat already booked
                }
            } else {
                return Boolean.FALSE; // Invalid row or seat index
            }
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    // Add this helper method to persist the train list
    private void saveTrainListToFile() throws IOException {
        File trains = new File(TRAINS_PATH);
        objectMapper.writeValue(trains, trainList);
    }
}