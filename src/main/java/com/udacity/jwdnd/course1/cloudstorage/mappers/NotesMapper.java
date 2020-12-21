package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * from NOTES")
    public List<Notes> findAllNotes();

    @Insert("INSERT into NOTES (notetitle, notedescription, userid) values (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public int uploadNotes(Notes notes);

    @Delete("DELETE FROM NOTES where noteid=#{noteId}")
    public void deleteNotes(int noteId);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription}, userid= #{userId} WHERE noteid=#{noteId}")
    public void updateNotes(Notes notes);
}
