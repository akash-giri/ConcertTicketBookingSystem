Concert Ticket Booking System (LLD)
A robust, thread-safe Low-Level Design for a concert booking system. This system allows users to search for concerts, view available seats, and book tickets while handling high-concurrency scenarios to prevent double-booking.

🚀 Features
Search Engine: Filter concerts by Artist name, Venue, or Date.

Real-time Availability: View seating arrangements with live status (Available/Booked).

Concurrency Control: Prevents multiple users from booking the same seat simultaneously using thread-safe synchronization.

Waiting List: Automatically adds users to a queue if a concert is sold out.

Notification & Payment: Simulated integration for post-booking workflows.

🛠️ Tech Stack
Language: Java 8

Concepts: Object-Oriented Design (OOD), Java Streams, Multi-threading (Synchronized blocks).

🏗️ Design Decisions
1. Handling Double-Booking
   To satisfy the requirement: "The system should handle concurrent booking requests", I implemented a two-layer protection:

Thread-Safe Seat Selection: The Seat.reserve() method is synchronized. Even if two threads attempt to book the same seat object, only one will successfully change the status from AVAILABLE to BOOKED.

Atomic Transactions: The BookingService.bookTicket() method ensures that payment and notification only trigger if the seat reservation is successful.

2. Fairness & Waiting List
   First-Come-First-Served (FCFS): By using a LinkedList as a Queue for the waiting list, we ensure that users who attempted to book first are prioritized if a seat becomes available later.

3. Separation of Concerns
   Models: User, Artist, Concert, Seat (Hold data).

Service: BookingService (Holds logic). This keeps the code clean and follows the Single Responsibility Principle.

📂 Class Structure

org.example
├── Main.java             # Entry point / Test Runner
├── BookingService.java   # Core Logic (Search, Book, Notify)
├── models/
│   ├── User.java         # User details
│   ├── Artist.java       # Artist & their concert list
│   ├── Concert.java      # Concert details & Waiting List
│   ├── Seat.java         # Seat status & locking logic
│   └── SeatStatus.java   # Enum (AVAILABLE, BOOKED)


🏃 How to Run:
Ensure you have JDK 8 or higher installed.

Compile the files:

Bash
javac org/example/*.java
Run the Main class:

Bash
java org.example.Main
🧪 Example Scenario
Search: User searches for "Taylor Swift".

View: System displays 2 available VIP seats.

Concurrent Booking: * User A and User B both try to book "Seat S1".

System grants Seat S1 to User A.

System automatically moves User B to the Waiting List.