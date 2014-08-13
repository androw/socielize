package com.androw.socielize.model;

/**
 * Created by Androw on 12/08/2014.
 */
public class Passenger {
    private User user;
    private int seatRow;
    private String seat;
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
