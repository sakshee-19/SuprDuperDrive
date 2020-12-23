package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dbentities.Files;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FilesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    FilesMapper filesMapper;

    public List<Files> getAllFiles(Integer userId) {
        logger.info("**START** getAllFiles");
        List<Files> FilesList = filesMapper.findAllFiles(userId);
        logger.info("**END** getAllFiles");
        return FilesList;
    }

    public int addNewFile(Files files) {
        logger.info("**START** addNewFile");
        int noteId = filesMapper.uploadFiles(files);
        logger.info("**END** addNewFile");
        return noteId;
    }


    public int updateFile(Files files) {
        logger.info("**START** updateFile note={}", files);
        filesMapper.updateFiles(files);
        logger.info("**END** updateFile");
        return 1;
    }


    public int deleteFile(Integer fileId) {
        logger.info("**START** deleteFile");
        try{
            filesMapper.deleteFiles(fileId);
            return 1;
        }catch (Exception e) {
            logger.error("Error in deleting e={}", e.getMessage());
            return -1;
        }
    }

    public Files findFileById(String fileId) {
        return filesMapper.getFileById(fileId);
    }

    public Files findFileByName(String fileName) {
        return filesMapper.getFileByName(fileName);
    }
}
