package irctc.ticket.services;

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

    private static final String USERS_PATH = "../localDb/users.json";

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
    public boolean SignUp(User user1) {
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

    // fetch seats for a train
    public List<List<Integer>> fetchSeats(String trainNumber) {

        List<List<Integer>> seats = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                row.add(0); // 0 means available, you can change logic as per your requirement
            }
            seats.add(row);
        }
        return seats;
    }

    // Select a seat for a train
    public void selectSeat(String trainNumber, int row, int column) {
        // In a real application, seat data should be persisted and validated
        List<List<Integer>> seats = fetchSeats(trainNumber);
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
        System.out.println("Seat selected for train " + trainNumber + " at row " + row + ", column " + column);
    }

}