package com.androw.socielize.model.oauth;

import com.androw.socielize.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;

/**
 * Created by Androw on 17/08/2014.
 */
public class MySocialUser extends SocialUser {
    private String id;

    private String firstName;

    private String lastName;

    private String desc;

    private Role role;

    private SocialMediaService socialMediaService;

    public MySocialUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String id, String firstName, String lastName, String desc, Role role, SocialMediaService socialMediaService) {
        super(username, password, authorities);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.desc = desc;
        this.role = role;
        this.socialMediaService = socialMediaService;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDesc() {
        return desc;
    }

    public Role getRole() {
        return role;
    }

    public SocialMediaService getSocialMediaService() {
        return socialMediaService;
    }
}
