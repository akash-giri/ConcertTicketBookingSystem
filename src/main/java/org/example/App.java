package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    /**
     * Requirements
     * The concert ticket booking system should allow users to view
     * available concerts and their
     *   seating arrangements.
     * Users should be able to search for concerts based on various
     * criteria such as artist, venue,
     *   date, and time.
     * Users should be able to select seats and purchase tickets for
     * a specific concert.
     * The system should handle concurrent booking requests to avoid
     * double-booking of seats.
     * The system should ensure fair booking opportunities for all users.
     * The system should handle payment processing securely.
     * The system should generate booking confirmations and send them
     * to users via email or SMS.
     * The system should provide a waiting list functionality for
     * sold-out concerts.
     * @param args
     */
    public static void main( String[] args )
    {
        // 1. Setup Data
        Artist artist = new Artist("A1", "Taylor Swift");
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat("S1", "VIP", 500.0));
        seats.add(new Seat("S2", "General", 100.0));

        Concert erasTour = new Concert("C1", artist, "Madison Square Garden", "2024-12-01", seats);
        artist.addConcert(erasTour);

        BookingService service = new BookingService();
        service.addConcert(erasTour);

        User user1 = new User("U1", "Alice", "alice@example.com", "123456789");
        User user2 = new User("U2", "Bob", "bob@example.com", "987654321");

        // 2. Search Requirement
        System.out.println("Searching for Taylor Swift concerts...");
        List<Concert> results = service.search("Taylor Swift", null, null);
        System.out.println("Found: " + results.size() + " concert(s)");

        // 3. Booking Requirement (Concurrency handled inside service)
        Seat seatToBook = erasTour.getSeats().get(0); // Seat S1

        System.out.println(service.bookTicket(user1, erasTour, seatToBook)); // Success
        System.out.println(service.bookTicket(user2, erasTour, seatToBook)); // Added to Waiting List (Sold Out)
    }
}
