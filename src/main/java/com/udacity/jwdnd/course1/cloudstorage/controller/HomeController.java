package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    NoteService noteService;

    @Autowired
    FileService fileService;

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

    @PostMapping("/file/save")
    public String saveFile(Authentication auth, @ModelAttribute("fileForm") FileForm fileForm, Model model) {
        logger.info("**START** saveFile files={}", fileForm);
        String username = auth.getName();
        Users users = userService.findUserByUsername(username);
        if (users == null) {
            model.addAttribute("errorMsg", "You are not logged-in, please login to continue");
            model.addAttribute("logout", true);
            return "result";
        }
        Files files = new Files();
        files.setContentType(fileForm.getFileUpload().getContentType());
        files.setFileSize(String.valueOf(fileForm.getFileUpload().getSize()));
        files.setUserId(users.getUserId());
        files.setFileName(fileForm.getFileUpload().getOriginalFilename());
        try {
            files.setFileData(fileForm.getFileUpload().getBytes());
        } catch (Exception e){
            logger.error("Exception e={}", e.getMessage());
            model.addAttribute("errorMsg", "error in file");
            return "result";
        }
        logger.info("Saving file in DB [{}]", files);
        int s = fileService.addNewFile(files);
        logger.info("Saved Note noteId={}", s);
        if (s<0)
            model.addAttribute("saveError", true);
        else   model.addAttribute("saveSuccess", true);
        logger.info("**END** saveFile ");
        return "result";
    }


    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model) {
        logger.info("**START** deleteFile noteId={}", fileId);
        int s = fileService.deleteFile(fileId);
        if (s<0)
            model.addAttribute("saveError", true);
        else   model.addAttribute("saveSuccess", true);
        logger.info("**END** deleteFile ");
        return "result";
    }

    @GetMapping("/file/view/{fileId}")
    public ResponseEntity fileView(@PathVariable("fileId") String fileId) {
        Files file = fileService.findFileById(fileId);
        String fileName = file.getFileName();
        if (fileName.indexOf(".") > 0)
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        return new ResponseEntity(file.getFileData(), headersPdfResponse(fileName, file.getContentType()), HttpStatus.OK);
    }

    public MultiValueMap<String, String> headersPdfResponse(String filename, String type) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, type);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename);
        return headers;
    }


    public void getAllItems(Model model) {
        logger.info("**START** getAllItems");
        List<CredentialsForm> credentialsList = credentialsService.findAllCredentials();
        model.addAttribute("credentialsList", credentialsList);
        List<Notes> notesList = noteService.getAllNotes();
        model.addAttribute("notesList", notesList);
        List<Files> filesList = fileService.getAllFiles();
        model.addAttribute("filesList", filesList);

        logger.info("**END** getAllItems");
    }
}
