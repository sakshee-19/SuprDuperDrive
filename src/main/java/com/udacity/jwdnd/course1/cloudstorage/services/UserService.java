package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Users;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    HashService hashService;

    public int createUser(Users users) {
        logger.info("**START** create User {}", users);
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        logger.info("encoded salt");
        users.setSalt(encodedSalt);
        String hashedPassword = hashService.getHashedValue(users.getPassword(), encodedSalt);
        logger.info("password hashed");
        users.setPassword(hashedPassword);
        int userId = userMapper.insert(users);
        logger.info("**END** create User userId={}", userId);
        return userId;
    }

    public boolean isUserAvailable(String username) {
        logger.info("**START** UserAvailable");
        Users user = userMapper.findUser(username);
        return user == null;
    }

    public Users findUserByUsername(String username) {
        return userMapper.findUser(username);
    }
}
