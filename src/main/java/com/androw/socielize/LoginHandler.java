package com.androw.socielize;

import com.androw.socielize.db.UserRepository;
import com.androw.socielize.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Androw on 15/08/2014.
 */
@Component
public class LoginHandler implements AuthenticationProvider {
    @Autowired
    private UserRepository users;

    @Autowired
    private ShaPasswordEncoder passwordEncoder;

    public LoginHandler() {
    }

    public Authentication authenticate(Authentication login) {
        User user = users.findByEmailAndPassword(login.getName(), passwordEncoder.encodePassword((String) login.getCredentials(), login.getName()));
        if (user == null) {
            throw new BadCredentialsException("Bad credentials");
        }
        ArrayList<GrantedAuthority> right = new ArrayList<GrantedAuthority>();
        right.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authUser = new UsernamePasswordAuthenticationToken(login.getPrincipal(), login.getCredentials(), right);
        return authUser;
    }

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
