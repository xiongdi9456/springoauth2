package com.sheldon.springoauth2.config;

import com.sheldon.springoauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * @Description:
 * @Author: Sheldon
 * @Date: 2018/09/24 10:59
 */

@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public WebSecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);

    }

}
