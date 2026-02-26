package org.example;

import java.util.*;
import java.util.stream.Collectors;

class BookingService {
    private List<Concert> concerts = new ArrayList<>();

    public void addConcert(Concert c) { concerts.add(c); }

    // Requirement: Search based on criteria
    public List<Concert> search(String artistName, String venue, String date) {
        return concerts.stream()
            .filter(c -> (artistName == null || c.getArtist().getName().equalsIgnoreCase(artistName)))
            .filter(c -> (venue == null || c.getVenue().equalsIgnoreCase(venue)))
            .filter(c -> (date == null || c.getDate().equals(date)))
            .collect(Collectors.toList());
    }

    // view available seat
    public void displayAvailableSeats(Concert concert) {
        System.out.println("Available Seats for: " + concert.getId());

        List<Seat> availableSeats = concert.getSeats().stream()
                .filter(s -> s.getStatus() == SeatStatus.AVAILABLE)
                .collect(Collectors.toList());

        if (availableSeats.isEmpty()) {
            System.out.println("No seats available! Join the waiting list?");
        } else {
            availableSeats.forEach(s ->
                    System.out.println("ID: " + s.getId() + " | Type: " + s.getType() + " | Price: $" + s.getPrice())
            );
        }
    }

    // Requirement: Handle concurrent booking
    public synchronized String bookTicket(User user, Concert concert, Seat seat) {
        if (seat.reserve()) {
            processPayment(user, seat.getPrice());
            sendNotification(user, "Booking Confirmed for " + concert.getId());
            return "SUCCESS: Ticket ID " + UUID.randomUUID().toString();
        } else {
            concert.getWaitingList().add(user);
            return "SOLD OUT: Added to Waiting List";
        }
    }

    public synchronized void cancelTicket(User user, Concert concert, Seat seat) {
        System.out.println("\n--- Processing Cancellation for " + user.getName() + " ---");

        // 1. Release the seat
        seat.release();

        // 2. Check the Waiting List for Fairness
        Queue<User> waitingList = concert.getWaitingList();
        if (!waitingList.isEmpty()) {
            User nextInLine = waitingList.poll(); // Get the first person who waited
            System.out.println("Fairness Check: Offering released seat " + seat.getId() + " to " + nextInLine.getName());

            // 3. Automatically book for the next person
            bookTicket(nextInLine, concert, seat);
        } else {
            System.out.println("Seat " + seat.getId() + " is now available for everyone.");
        }
    }

    private void processPayment(User user, double amount) {
        System.out.println("Processing payment of $" + amount + " for " + user.getName());
    }

    private void sendNotification(User user, String msg) {
        System.out.println("Notification sent to " + user.getName() + ": " + msg);
    }
}