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
    private User user;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "/Users/amansaifi/Desktop/Coding/SpringBoot/irctc-ticket-booking-system/app/src/main/java/irctc/localDb/users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
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
        return foundUser.isPresent();
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
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
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

    // Book a seat for a train
    public Boolean bookTrainSeat(Train trainSelectedForBooking, int row, int seat) {
        try {
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = trainSelectedForBooking.getSeatNumbers();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);

                    trainSelectedForBooking.setSeatNumbers(seats);
                    trainService.addTrain(trainSelectedForBooking);

                    Ticket ticket = new Ticket();

                    ticket.setSourceStation(trainSelectedForBooking.getStations().getFirst());
                    ticket.setDestinationStation(trainSelectedForBooking.getStations().getLast());
                    ticket.setTrain(trainSelectedForBooking);
                    ticket.setPassengerName(user.getUsername());
                    ticket.setTravelDate("2021-09-01");
                    ticket.setTicketId(UserServiceUtil.generateTicketId());

                    user.getBookedTickets().add(ticket);

                    System.out.println("Seat booked successfully  !  ");

                    System.out.println(ticket.getTicketDetails());

                    saveUserListToFile();
                    return true; // Booking successful
                } else {
                    return false; // Execute when Seat is already booked
                }
            } else {
                return false; // Execute when Invalid row or seat index
            }
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

}