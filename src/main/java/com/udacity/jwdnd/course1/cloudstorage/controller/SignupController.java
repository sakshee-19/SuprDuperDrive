package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    UserService userService;

    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String registerUser(@ModelAttribute Users users, Model model) {
        logger.info("**START** registerUser users={}", users);
        model.addAttribute("signupSuccess", false);
        if(!userService.isUserAvailable(users.getUsername())){
            model.addAttribute("signupError", "user already present");
        } else {
            userService.createUser(users);
            model.addAttribute("signupSuccess", true);
        }
        logger.info("**END** register User");
        return "signup";
    }
}
