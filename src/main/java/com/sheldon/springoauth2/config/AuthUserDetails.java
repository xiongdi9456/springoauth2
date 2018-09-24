package com.sheldon.springoauth2.config;

import com.sheldon.springoauth2.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * @Description:
 * @Author: Sheldon
 * @Date: 2018/09/24 11:05
 */
public class AuthUserDetails extends org.springframework.security.core.userdetails.User {

    private User user;

    public AuthUserDetails(User user){
        super(user.getUsername(), user.getPassword(), true, true, true, true, Collections.EMPTY_SET);
        this.user = user;
    }

    public AuthUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }
}
