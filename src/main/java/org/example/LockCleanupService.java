package org.example;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LockCleanupService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startCleanupTask(List<Concert> concerts) {
        scheduler.scheduleAtFixedRate(() -> {
            for (Concert concert : concerts) {
                for (Seat seat : concert.getSeats()) {
                    // If seat is locked and time is up, release it
                    if (seat.getStatus() == SeatStatus.LOCKED && 
                        Instant.now().isAfter(seat.getLockExpiry())) {
                        seat.release();
                        System.out.println("Cleanup: Released expired lock on seat " + seat.getId());
                    }
                }
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}