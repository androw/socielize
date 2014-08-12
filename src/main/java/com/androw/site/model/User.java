package com.androw.site.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Androw on 12/08/2014.
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Passenger> passengers;

    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passengers = new ArrayList<Passenger>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addPassengers(Passenger passenger) {
        if (!this.passengers.contains(passenger))
            this.passengers.add(passenger);
    }

    public List<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        for (Passenger passenger : this.passengers) {
            flights.add(passenger.getFlight());
        }
        return flights;
    }
}
