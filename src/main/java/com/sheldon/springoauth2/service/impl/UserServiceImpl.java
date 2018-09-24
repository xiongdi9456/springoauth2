package com.sheldon.springoauth2.service.impl;

import com.sheldon.springoauth2.config.AuthUserDetails;
import com.sheldon.springoauth2.entity.User;
import com.sheldon.springoauth2.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @Description:
 * @Author: Sheldon
 * @Date: 2018/09/24 10:31
 */

@Service
public class UserServiceImpl implements UserService {


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /*模拟数据库操作*/
        User user = new User();
        user.setUsername("10086");
        user.setPassword("123456");
        return new AuthUserDetails(user);
    }
}
