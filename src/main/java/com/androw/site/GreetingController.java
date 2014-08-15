package com.androw.site;

import com.androw.site.db.FlightDB;
import com.androw.site.model.Flight;
import com.androw.site.model.Passenger;
import com.androw.site.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GreetingController {
    private static FlightDB db = new FlightDB();

    private static List<Flight> flightList = new ArrayList<Flight>();
    private static List<User> userList = new ArrayList<User>();

    private static User testUser = new User("androw95220@gmail.com", "Nicolas", "Lorin");

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

    @RequestMapping("/register")
    public String register(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping(value = "/addFlight", method = RequestMethod.GET)
    public String addFlight(Model model) {
        Flight flight = new Flight();
        model.addAttribute("flight", flight);
        model.addAttribute("user", testUser);
        return "addFlight";
    }

    @RequestMapping(value = "/addFlight", method = RequestMethod.POST)
    public String addFlightSubmit(@Valid Flight flight, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", testUser);
            return "addFlight";
        }
        if (flightList.contains(flight)) {
            Flight oldFlight = flightList.get(flightList.indexOf(flight));
            Passenger passenger = new Passenger(testUser, oldFlight);
        } else {
            flightList.add(flight);
            Passenger passenger = new Passenger(testUser, flight);
        }
        return "redirect:/yourFlights";
    }

    @RequestMapping("/yourFlights")
    public String yourFlights(Model model) {
        model.addAttribute("flights", testUser.getFlights());
        model.addAttribute("user", testUser);
        model.addAttribute("content", "yourFlights");
        return "two-cols-layout";
    }
}
