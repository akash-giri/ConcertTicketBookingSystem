package org.example;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    private String id;
    private String name;
    private List<Concert> concerts;

    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
        this.concerts = new ArrayList<>();
    }

    public void addConcert(Concert concert) {
        this.concerts.add(concert);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Concert> getConcerts() { return concerts; }
}