package com.androw.socielize;

import com.androw.socielize.db.AutoCompletionDB;
import com.androw.socielize.db.FlightRepository;
import com.androw.socielize.db.PassengerRepository;
import com.androw.socielize.db.UserRepository;
import com.androw.socielize.model.Flight;
import com.androw.socielize.model.Passenger;
import com.androw.socielize.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class GreetingController {
    private static AutoCompletionDB db = new AutoCompletionDB();

    @Autowired
    private UserRepository users;
    @Autowired
    private FlightRepository flights;
    @Autowired
    private PassengerRepository passengers;

    @RequestMapping("/")
    public String index(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        String date = (new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        model.addAttribute("name", name);
        model.addAttribute("content", "index");
        model.addAttribute("flights", flights.findByDateStringGreaterThanEqual(date));
        return "two-cols-layout";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("content", "about");
        return "two-cols-layout";
    }

    @RequestMapping(value = "/getIATAList", method = RequestMethod.GET, headers = "Accept=*/*")
    public
    @ResponseBody
    List<String> getIATAList(@RequestParam("term") String query) {
        return db.getIATAList(query);
    }

    @RequestMapping("/findFlight")
    public String findFlight(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("content", "index");
        return "two-cols-layout";
    }

    @RequestMapping(value = "/user/addFlight", method = RequestMethod.GET)
    public String addFlight(Model model) {
        User currUser = getCurrentUser();
        model.addAttribute("flight", new Flight());
        model.addAttribute("user", currUser);
        model.addAttribute("content", "addFlight");
        return "two-cols-layout";
    }

    @RequestMapping(value = "/user/addFlight", method = RequestMethod.POST)
    public String addFlightSubmit(@Valid Flight flight, BindingResult result, Model model) {
        User currUser = getCurrentUser();
        if (result.hasErrors()) {
            model.addAttribute("user", currUser);
            model.addAttribute("content", "addFlight");
            return "two-cols-layout";
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
        return "redirect:/user/myFlights";
    }

    @RequestMapping("/user/myFlights")
    public String myFlights(Model model) {
        User currUser = getCurrentUser();
        model.addAttribute("flights", currUser.getFlights());
        model.addAttribute("user", currUser);
        model.addAttribute("content", "myFlights");
        return "two-cols-layout";
    }

    @RequestMapping(value = "/user/onFlight/{flightId}")
    public String addFlight(@PathVariable("flightId") String flightId, @RequestParam(value = "date", required = false, defaultValue = "today") String date, Model model) {
        if (date.equals("today")) date = (new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        User currUser = getCurrentUser();
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
        model.addAttribute("content", "onFlight");
        return "two-cols-layout";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return users.findByEmail(email);
    }
}
