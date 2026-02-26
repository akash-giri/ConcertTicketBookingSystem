package org.example;

class Seat {
    private String id;
    private String type; // VIP, General
    private double price;

    private SeatStatus status = SeatStatus.AVAILABLE;

    public Seat(String id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    // Synchronized to prevent two threads from locking the same seat
    public synchronized boolean reserve() {
        if (status == SeatStatus.AVAILABLE) {
            status = SeatStatus.BOOKED;
            return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }


    public String getType() {
        return type;
    }


    public double getPrice() {
        return price;
    }


}