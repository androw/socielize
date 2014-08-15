package com.androw.socielize.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Androw on 12/08/2014.
 */
@Document(collection = "passengers")
public class Passenger {
    @Id
    private String id;
    @DBRef
    private User user;
    private int seatRow;
    private String seat;
    @DBRef
    private Flight flight;

    public Passenger(User user, int seatRow, String seat, Flight flight) {
        this.user = user;
        this.seatRow = seatRow;
        this.seat = seat;
        this.flight = flight;
        this.user.addPassenger(this);
        this.flight.addPassenger(this);
    }

    public Passenger(User user, Flight flight) {
        this.user = user;
        this.flight = flight;
        this.user.addPassenger(this);
        this.flight.addPassenger(this);
    }

    public Passenger() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (!flight.equals(passenger.flight)) return false;
        if (!user.equals(passenger.user)) return false;

        return true;
    }
}
