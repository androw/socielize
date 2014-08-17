package com.androw.socielize.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Androw on 12/08/2014.
 */
@Document(collection = "flights")
public class Flight implements Serializable {
    @Id
    private String id;
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 2)
    @Pattern(regexp="^[A-Z][A-Z]$")
    private String carrier;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 4)
    @Pattern(regexp="^[0-9][0-9][0-9][0-9]?$")
    private String flightNumber;

    @NotEmpty
    @NotNull
    @Size(min = 10, max = 10)
    private String dateString;
    @DBRef
    private List<Passenger> passengers;

    public Flight() {
        this.passengers = new ArrayList<Passenger>();
    }

    public Flight(String carrier, String flightNumber) {
        this.carrier = carrier;
        this.flightNumber = flightNumber;
        this.passengers = new ArrayList<Passenger>();
    }

    public Flight(String carrier, String flightNumber, String dateString) {
        this.carrier = carrier;
        this.flightNumber = flightNumber;
        this.dateString = dateString;
        this.passengers = new ArrayList<Passenger>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier.toUpperCase();
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

    public void addPassenger(Passenger passenger) {
        if (!this.passengers.contains(passenger))
            this.passengers.add(passenger);
    }

    public List<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        for (Passenger passenger : this.passengers) {
            users.add(passenger.getUser());
        }
        return users;
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
