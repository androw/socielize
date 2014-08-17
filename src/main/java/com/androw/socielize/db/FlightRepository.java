package com.androw.socielize.db;

import com.androw.socielize.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Androw on 14/08/2014.
 */
public interface FlightRepository extends MongoRepository<Flight, Long> {
    public Flight findById(Long id);
    public Flight findByCarrierAndFlightNumberAndDateString(String carrier, String flightNumber, String dateString);
    public List<Flight> findByDateStringGreaterThanEqual(String date);
}
