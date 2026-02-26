package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Concert {
    private String id;
    private Artist artist;
    private String venue;
    private String date; // In real use, use LocalDate
    private List<Seat> seats;

    private Queue<User> waitingList = new LinkedList<>();

    public Concert(String id, Artist artist, String venue, String date, List<Seat> seats) {
        this.id = id;
        this.artist = artist;
        this.venue = venue;
        this.date = date;
        this.seats = seats;
    }
    public Queue<User> getWaitingList() {
        return waitingList;
    }

    public String getId() {
        return id;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}