package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Notes;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    Logger logger = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    NotesMapper notesMapper;

    public List<Notes> getAllNotes() {
        logger.info("**START** getAllNotes");
        List<Notes> notesList = notesMapper.findAllNotes();
        logger.info("**END** getAllNotes");
        return notesList;
    }

    public int addNewNote(Notes notes) {
        logger.info("**START** addNewNote");
        int noteId = notesMapper.uploadNotes(notes);
        logger.info("**END** addNewNote");
        return noteId;
    }


    public int updateNote(Notes notes) {
        logger.info("**START** updateNote note={}", notes);
        notesMapper.updateNotes(notes);
        logger.info("**END** updateNote");
        return 1;
    }


    public int deleteNote(Integer noteId) {
        logger.info("**START** deleteNote");
        try{
            notesMapper.deleteNotes(noteId);
            return 1;
        }catch (Exception e) {
            logger.error("Error in deleting e={}", e.getMessage());
            return -1;
        }
    }
}
