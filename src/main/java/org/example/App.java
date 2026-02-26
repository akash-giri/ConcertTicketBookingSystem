package org.example;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // --- 1. SETUP DATA ---
        BookingService service = new BookingService();

        Artist taylor = new Artist("A1", "Taylor Swift");

        // Creating specific seats
        Seat s1 = new Seat("S1", "VIP", 500.0);
        Seat s2 = new Seat("S2", "General", 100.0);
        List<Seat> seatList = new ArrayList<>();
        seatList.add(s1);
        seatList.add(s2);

        Concert erasTour = new Concert("C1", taylor, "Madison Square Garden", "2026-12-01", seatList);
        taylor.addConcert(erasTour);
        service.addConcert(erasTour);

        // Setup Users
        User alice = new User("U1", "Alice", "alice@test.com", "111");
        User bob = new User("U2", "Bob", "bob@test.com", "222");
        User charlie = new User("U3", "Charlie", "charlie@test.com", "333");

        System.out.println("=== WELCOME TO THE CONCERT BOOKING SYSTEM ===\n");

        // --- 2. SEARCH REQUIREMENT ---
        System.out.println("Step 1: Searching for concerts by 'Taylor Swift'...");
        List<Concert> searchResults = service.search("Taylor Swift", null, null);
        for (Concert c : searchResults) {
            System.out.println("Found Concert: " + c.getArtist().getName() + " at " + c.getVenue());
        }

        // --- 3. VIEW SEATING ARRANGEMENT ---
        System.out.println("\nStep 2: Viewing available seats for " + erasTour.getVenue() + "...");
        service.displayAvailableSeats(erasTour);

        // --- 4. CONCURRENCY & BOOKING ---
        System.out.println("\nStep 3: Processing bookings for VIP Seat (S1)...");

        // Alice books first
        System.out.println("Alice Attempt: " + service.bookTicket(alice, erasTour, s1));

        // Bob tries to book the same seat (Should fail and go to Waiting List)
        System.out.println("Bob Attempt: " + service.bookTicket(bob, erasTour, s1));

        // Charlie tries to book the same seat (Should fail and go to Waiting List behind Bob)
        System.out.println("Charlie Attempt: " + service.bookTicket(charlie, erasTour, s1));

        // --- 5. FAIRNESS & CANCELLATION ---
        System.out.println("\nStep 4: Alice cancels her ticket. System should trigger Fairness logic...");
        // Requirement: Bob is 1st in waiting list, so he should get the seat automatically.
        service.cancelTicket(alice, erasTour, s1);

        // --- 6. FINAL STATUS CHECK ---
        System.out.println("\nFinal Status Check:");
        System.out.println("Is Seat S1 booked? Status: " + s1.getStatus());
        System.out.println("Remaining people in waiting list: " + erasTour.getWaitingList().size());
        // Charlie should still be in the waiting list because Bob took Alice's spot.
    }
}