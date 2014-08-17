package com.androw.socielize.config;

/**
 * Created by Androw on 12/08/2014.
 */

import com.androw.socielize.db.UserRepository;
import com.androw.socielize.db.oauth.MongoUsersConnectionRepository;
import com.androw.socielize.db.oauth.UserSocialConnectionRepository;
import com.androw.socielize.model.User;
import com.androw.socielize.user.MySocialUserDetailsService;
import com.androw.socielize.user.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.security.*;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import javax.inject.Inject;

@Configuration
@EnableWebMvcSecurity
//@EnableSocial
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository repository;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private UsersConnectionRepository usersConnectionRepository;

    @Inject
    private SocialAuthenticationServiceLocator socialAuthenticationServiceLocator;

    @Inject
    private UserIdSource userIdSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").authenticated()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and().apply(new SpringSocialConfigurer());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    public
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new MySocialUserDetailsService(userDetailsService());
    }

    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(repository);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }



    @Bean
    public SocialAuthenticationFilter socialAuthenticationFilter() {
        SocialAuthenticationFilter filter = new SocialAuthenticationFilter(authenticationManager, userIdSource,
                usersConnectionRepository, socialAuthenticationServiceLocator);
        //filter.setFilterProcessesUrl('/signin');
        //filter.setSignupUrl(null);
        //filter.setConnectionAddedRedirectUrl("/myAccount");
        //filter.setPostLoginUrl("/myAccount");
        return filter;
    }


}