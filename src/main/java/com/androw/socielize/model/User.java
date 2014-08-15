package com.androw.socielize.model;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Androw on 12/08/2014.
 */
@Document(collection = "users")
public class User implements Serializable {
    @Id
    private String id;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotEmpty
    @NotNull
    private String lastName;

    @NotEmpty
    @NotNull
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=6, max=20)
    private String password;

    private String desc;
    @DBRef
    private List<Passenger> passengers;

    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.desc = "";
        this.passengers = new ArrayList<Passenger>();
    }

    public User() {
        this.passengers = new ArrayList<Passenger>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public void addPassenger(Passenger passenger) {
        if (!this.passengers.contains(passenger))
            this.passengers.add(passenger);
    }

    public boolean isAdmin() {
        return false;
    }

    public List<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        for (Passenger passenger : this.passengers) {
            flights.add(passenger.getFlight());
        }
        return flights;
    }
}
