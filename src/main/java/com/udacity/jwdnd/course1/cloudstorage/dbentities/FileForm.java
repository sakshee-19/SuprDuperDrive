package com.udacity.jwdnd.course1.cloudstorage.dbentities;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {

    private MultipartFile fileUpload;

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    @Override
    public String toString() {
        return "FileForm{" +
                "fileUpload=" + fileUpload +
                '}';
    }
}
