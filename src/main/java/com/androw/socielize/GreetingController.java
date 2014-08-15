package com.androw.socielize;

import com.androw.socielize.db.FlightDB;
import com.androw.socielize.db.FlightRepository;
import com.androw.socielize.db.PassengerRepository;
import com.androw.socielize.db.UserRepository;
import com.androw.socielize.model.Flight;
import com.androw.socielize.model.Passenger;
import com.androw.socielize.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class GreetingController {
    private static FlightDB db = new FlightDB();

    @Autowired
    private UserRepository users;
    @Autowired
    private FlightRepository flights;
    @Autowired
    private PassengerRepository passengers;

    @RequestMapping("/")
    public String index(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping(value = "/getIATAList", method = RequestMethod.GET, headers = "Accept=*/*")
    public
    @ResponseBody
    List<String> getIATAList(@RequestParam("term") String query) {
        return db.getIATAList(query);
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("name", "World");
        return "index";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String registerSubmit(@Valid User user, BindingResult result, Model model) {
        User currUser = users.findByEmail("androw95220@gmail.com");
        if (result.hasErrors()) {
            model.addAttribute("user", currUser);
            return "register";
        }
        return "redirect:/yourFlights";
    }

    @RequestMapping(value = "/addFlight", method = RequestMethod.GET)
    public String addFlight(Model model) {
        User currUser = users.findByEmail("androw95220@gmail.com");
        model.addAttribute("flight", new Flight());
        model.addAttribute("user", currUser);
        return "addFlight";
    }

    @RequestMapping(value = "/addFlight", method = RequestMethod.POST)
    public String addFlightSubmit(@Valid Flight flight, BindingResult result, Model model) {
        User currUser = users.findByEmail("androw95220@gmail.com");
        if (result.hasErrors()) {
            model.addAttribute("user", currUser);
            return "addFlight";
        }
        Flight oldFlight = flights.findByCarrierAndFlightNumberAndDateString(flight.getCarrier(), flight.getFlightNumber(), flight.getDateString());
        if (oldFlight != null) {
            Passenger passenger = new Passenger(currUser, oldFlight);
            passengers.save(passenger);
            flights.save(oldFlight);
            users.save(currUser);
        } else {
            flight = flights.save(flight);
            Passenger passenger = new Passenger(currUser, flight);
            passengers.save(passenger);
            flights.save(flight);
            users.save(currUser);
        }
        return "redirect:/yourFlights";
    }

    @RequestMapping("/yourFlights")
    public String yourFlights(Model model) {
        User currUser = users.findByEmail("androw95220@gmail.com");
        model.addAttribute("flights", currUser.getFlights());
        model.addAttribute("user", currUser);
        model.addAttribute("content", "yourFlights");
        return "two-cols-layout";
    }

    @RequestMapping(value = "/onFlight/{flightId}")
    public String addFlight(@PathVariable("flightId") String flightId, @RequestParam(value = "date", required = false, defaultValue = "today") String date, Model model) {
        User currUser = users.findByEmail("androw95220@gmail.com");
        Flight search = new Flight(flightId.substring(0, 2), flightId.substring(2), date);
        Flight oldFlight = flights.findByCarrierAndFlightNumberAndDateString(search.getCarrier(), search.getFlightNumber(), search.getDateString());
        if (oldFlight != null) {
            model.addAttribute("find", true);
            model.addAttribute("flight", oldFlight);
            model.addAttribute("passengers", oldFlight.getUsers());
        } else {
            model.addAttribute("find", false);
            model.addAttribute("flight", search);
        }
        model.addAttribute("user", currUser);
        return "onFlight";
    }
}
