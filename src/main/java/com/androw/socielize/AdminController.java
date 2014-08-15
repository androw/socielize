package com.androw.socielize;


import com.androw.socielize.db.FlightRepository;
import com.androw.socielize.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Androw on 14/08/2014.
 */
@Controller
public class AdminController {
    @Autowired
    private UserRepository users;

    @Autowired
    private FlightRepository flights;

    @RequestMapping("/listUsers")
    public String listUsers(Model model) {
        model.addAttribute("users", users.findAll());
        return "listUsers";
    }
}
