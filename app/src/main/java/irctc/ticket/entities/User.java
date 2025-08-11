package irctc.ticket.entities;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String hashedPassword;
    private String email;
    private String phoneNumber;
    private List<Ticket> bookedTickets;

    public User(String username, String password, String hashedPassword, List<Ticket> bookedTickets,
            String phoneNumber) {
        this.username = username;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.bookedTickets = bookedTickets;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public void printTickets() {
        for (int i = 0; i < bookedTickets.size(); i++) {
            System.out.println("Ticket " + (i + 1) + ": " + bookedTickets.get(i).getTicketDetails());
        }
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hassPassword) {
        this.hashedPassword = hassPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public void setBookedTickets(List<Ticket> bookedTickets) {
        this.bookedTickets = bookedTickets;
    }
}
