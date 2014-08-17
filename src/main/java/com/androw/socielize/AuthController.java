package com.androw.socielize;

import com.androw.socielize.db.UserRepository;
import com.androw.socielize.model.SocialMediaService;
import com.androw.socielize.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

/**
 * Created by Androw on 15/08/2014.
 */
@Controller
@SessionAttributes("user")
public class AuthController {
    @Autowired
    private UserRepository users;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("content", "login");
        return "two-cols-layout";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String redirectRequestToRegistrationPage() {
        return "redirect:/register";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        model.addAttribute("content", "logout");
        return "two-cols-layout";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(WebRequest request, Model model) {
        Connection<?> connection = new ProviderSignInUtils().getConnectionFromSession(request);

        model.addAttribute("user", createRegistrationDTO(connection));
        model.addAttribute("content", "register");
        return "two-cols-layout";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerSubmit(@Valid User user, BindingResult result, WebRequest request, Model model) {
        if (users.findByEmail(user.getEmail()) != null) {
            result.addError(new FieldError("user", "email", "Already registered"));
        }
        if (result.hasErrors()) {
            model.addAttribute("content", "register");
            return "two-cols-layout";
        }  else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            users.save(user);
            new ProviderSignInUtils().doPostSignUp(user.getEmail(), request);
            return "redirect:/login";
        }
    }

    private User createRegistrationDTO(Connection<?> connection) {
        User dto = new User();
        if (connection != null) {
            UserProfile socialMediaProvile = connection.fetchUserProfile();
            dto.setEmail(socialMediaProvile.getEmail());
            dto.setFirstName(socialMediaProvile.getFirstName());
            dto.setLastName(socialMediaProvile.getLastName());

            ConnectionKey providerKey = connection.getKey();
            dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
        }
        return dto;
    }

}
