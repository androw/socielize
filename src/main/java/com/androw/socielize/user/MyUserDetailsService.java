package com.androw.socielize.user;

import com.androw.socielize.db.UserRepository;
import com.androw.socielize.model.oauth.MySocialUser;
import com.androw.socielize.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Androw on 17/08/2014.
 */
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository repository;

    @Autowired
    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);

        System.out.println(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user");
        }

        Set<GrantedAuthority> gta = new HashSet<GrantedAuthority>();
        gta.add(new SimpleGrantedAuthority(user.getRole().toString()));

        UserDetails socialUser = new MySocialUser(user.getEmail(), user.getPassword(), gta, user.getId(), user.getFirstName(), user.getLastName(), user.getDesc(), user.getRole(), user.getSignInProvider());
        return socialUser;
    }
}
