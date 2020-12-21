package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.dbentities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.dbentities.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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

    @Autowired
    NoteService noteService;

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
        if (user == null) {
            model.addAttribute("errorMsg", "You are not logged-in, please login to continue");
            model.addAttribute("logout", true);
            return "result";
        }
        int s = -1;
        if(credId == null) {
            //perform addition
            s = credentialsService.addNewCredentials(credentials);
            logger.info("addition s={}",s);
        } else {
            //perform edit
            s = credentialsService.editCredentials(credentials, credId);
            logger.info("edition s={}, credId={}",s,credId);
        }
        if (s<0)
            model.addAttribute("saveError", true);
        else   model.addAttribute("saveSuccess", true);
        logger.info("**END** saveCredentials");
        return "result";
    }

    @GetMapping("/credential/delete/{credId}")
    public String deleteCredentials(@PathVariable("credId") Integer credId, Model model) {
        int s = credentialsService.deleteCredentials(credId);
        if (s<0)
            model.addAttribute("saveError", true);
        else   model.addAttribute("saveSuccess", true);
        return "result";
    }

    @PostMapping("/note/save")
    public String saveNote(Authentication auth, @ModelAttribute("notes") Notes notes, Model model) {
        logger.info("**START** saveNote notes={}", notes);
        String username = auth.getName();
        Users users = userService.findUserByUsername(username);
        if (users == null) {
            model.addAttribute("errorMsg", "You are not logged-in, please login to continue");
            model.addAttribute("logout", true);
            return "result";
        }
        Integer noteId = notes.getNoteId();
        notes.setUserId(users.getUserId());
        int s = -1;
        if(noteId == null) {
            s = noteService.addNewNote(notes);
            logger.info("Saved Note noteId={}", s);
        } else {
            //perform edit
            s = noteService.updateNote(notes);
        }
        if (s<0)
            model.addAttribute("saveError", true);
        else   model.addAttribute("saveSuccess", true);
        logger.info("**END** saveNote ");
        return "result";
    }

    @GetMapping("/note/delete/{noteid}")
    public String deleteNote(@PathVariable("noteid") Integer noteId, Model model) {
        logger.info("**START** deleteNote noteId={}", noteId);
        int s = noteService.deleteNote(noteId);
        if (s<0)
            model.addAttribute("saveError", true);
        else   model.addAttribute("saveSuccess", true);
        logger.info("**END** deleteNote ");
        return "result";
    }


    public void getAllItems(Model model) {
        logger.info("**START** getAllItems");
        List<Credentials> credentialsList = credentialsService.findAllCredentials();
        model.addAttribute("credentialsList", credentialsList);
        List<Notes> notesList = noteService.getAllNotes();
        model.addAttribute("notesList", notesList);
        logger.info("**END** getAllItems");
    }
}
