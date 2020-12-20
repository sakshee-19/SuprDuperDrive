package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    HashService hashService;

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("**START** Authentication");
        String username = authentication.getName();
        String pass = authentication.getCredentials().toString();
        logger.info("find user by username");
        Users user = userService.findUserByUsername(username);
        if(user != null) {
            String hashedPass = hashService.getHashedValue(pass, user.getSalt());
            if(hashedPass.equals(user.getPassword())){
                logger.info("** LOGIN successful **");
                return new UsernamePasswordAuthenticationToken(username, pass, new ArrayList<>());
            }
        }
        logger.info("**END** Authentication");
        return null;
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
