package com.androw.socielize;

import com.androw.socielize.db.UserRepository;
import com.androw.socielize.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by Androw on 15/08/2014.
 */
@Controller
public class AuthController {
    @Autowired
    private UserRepository users;

    @RequestMapping("/login")
    public String login(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String registerSubmit(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        if (users.findByEmail(user.getEmail()) != null) {
            result.addError(new ObjectError("email", "Already registered"));
            return "register";
        }
        users.save(user);
        return "redirect:/login";
    }
}
