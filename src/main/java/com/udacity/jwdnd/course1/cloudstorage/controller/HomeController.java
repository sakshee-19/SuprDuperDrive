package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.dbentities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.dbentities.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    CredentialsService credentialsService;


    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public String getHome(@ModelAttribute("credentials") Credentials credentials, Model model) {
        getAllItems(model);
        return "home";
    }


    @PostMapping("/credentials/save")
    public String saveCredentials(Authentication authentication, @ModelAttribute("credentials") Credentials credentials, Model model) {
        logger.info("**START** saveCredentials");
        Integer credId = credentials.getCredentialId();
        Users user = userService.findUserByUsername(authentication.getName());
        credentials.setUserId(user.getUserId());
        if(credId == null) {
            //perform addition
            int s = credentialsService.addNewCredentials(credentials);
            logger.info("addition s={}",s);

        } else {
            //perform edit
            int s = credentialsService.editCredentials(credentials, credId);
            logger.info("edition s={}, credId={}",s,credId);
        }
        getAllItems(model);
        logger.info("**END** saveCredentials");
        return "home";
    }

    @PostMapping("/credential/delete/{credId}")
    public String deleteCredentials(@PathVariable("credId") Integer credId, Model model) {
        credentialsService.deleteCredentials(credId);
        getAllItems(model);
        return "home";
    }

    public void getAllItems(Model model) {
        logger.info("**START** getAllItems");
        List<Credentials> credentialsList = credentialsService.findAllCredentials();
        model.addAttribute("credentialsList", credentialsList);
//        List<Notes> notesList =
        logger.info("**END** getAllItems");
    }
}
