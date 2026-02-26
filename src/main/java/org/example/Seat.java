package org.example;

import java.time.Instant;

class Seat {
    private String id;
    private String type; // VIP, General
    private double price;

    private String lockedByUserId;
    private Instant lockExpiry;

    public String getLockedByUserId() {
        return lockedByUserId;
    }

    public Instant getLockExpiry() {
        return lockExpiry;
    }

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

    public synchronized boolean lock(String userId, int durationInMinutes) {
        if (status == SeatStatus.AVAILABLE ||
                (status == SeatStatus.LOCKED && Instant.now().isAfter(lockExpiry))) {

            this.status = SeatStatus.LOCKED;
            this.lockedByUserId = userId;
            this.lockExpiry = Instant.now().plusSeconds(durationInMinutes * 60);
            return true;
        }
        return false;
    }

    public synchronized boolean confirmBooking(String userId) {
        if (status == SeatStatus.LOCKED && userId.equals(lockedByUserId) && Instant.now().isBefore(lockExpiry)) {
            this.status = SeatStatus.BOOKED;
            this.lockExpiry = null;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        this.status = SeatStatus.AVAILABLE;
    }

    public void release1() {
        this.status = SeatStatus.AVAILABLE;
        this.lockedByUserId = null;
        this.lockExpiry = null;
    }

    public SeatStatus getStatus() {
        return status;
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