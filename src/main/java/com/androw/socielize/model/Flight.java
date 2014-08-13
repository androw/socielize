package com.androw.socielize.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Androw on 12/08/2014.
 */
public class Flight {
    @NotNull
    @Size(min = 2, max = 2)
    private String carrier;
    @NotNull
    @Size(min = 3, max = 4)
    private String flightNumber;
    @NotNull
    private String dateString;
    private List<Passenger> passengers;

    public Flight() {
        this.passengers = new ArrayList<Passenger>();
    }

    public Flight(String carrier, String flightNumber) {
        this.carrier = carrier;
        this.flightNumber = flightNumber;
        this.passengers = new ArrayList<Passenger>();
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String date) {
        this.dateString = date;
    }

    public void addPassengers(Passenger passenger) {
        if (!this.passengers.contains(passenger))
            this.passengers.add(passenger);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (!carrier.equals(flight.carrier)) return false;
        if (!dateString.equals(flight.dateString)) return false;
        if (!flightNumber.equals(flight.flightNumber)) return false;

        return true;
    }
}
