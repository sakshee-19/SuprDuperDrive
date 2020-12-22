package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Files;
import com.udacity.jwdnd.course1.cloudstorage.dbentities.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("SELECT * from FILES")
    public List<Files> findAllFiles();

    @Insert("INSERT into FILES (filename, contenttype, filesize,userid ,filedata) values (#{fileName}, #{contentType}, #{fileSize}, #{userId} ,#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int uploadFiles(Files files);

    @Delete("DELETE FROM FILES where fileId=#{fileId}")
    public void deleteFiles(int fileId);

    @Update("UPDATE FILES SET filename=#{fileName}, contenttype=#{contentType}, filesize=#{fileSize},userid= #{userId} WHERE fileId=#{fileId})")
    public void updateFiles(Files files);

    @Select("SELECT * from FILES WHERE fileid=#{fileId}")
    Files getFileById(String fileId);

    @Select("SELECT * from FILES WHERE filename=#{fileName}")
    Files getFileByName(String fileName);
}
