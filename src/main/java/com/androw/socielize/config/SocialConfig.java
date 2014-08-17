package com.androw.socielize.config;

import com.androw.socielize.db.oauth.MongoUsersConnectionRepository;
import com.androw.socielize.db.oauth.UserSocialConnectionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.*;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.twitter.api.Twitter;

import javax.inject.Inject;

/**
 * Created by Androw on 17/08/2014.
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

    @Inject
    private UserSocialConnectionRepository userSocialConnectionRepository;


    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {

    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        MongoUsersConnectionRepository repository = new MongoUsersConnectionRepository(
                userSocialConnectionRepository, (SocialAuthenticationServiceLocator) connectionFactoryLocator, Encryptors.noOpText());
        //repository.setConnectionSignUp(autoConnectionSignUp());
        return repository;
    }


    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Facebook facebook(ConnectionRepository repository) {
        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
        return connection != null ? connection.getApi() : null;
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Twitter twitter(ConnectionRepository repository) {
        Connection<Twitter> connection = repository.findPrimaryConnection(Twitter.class);
        return connection != null ? connection.getApi() : null;
    }


}