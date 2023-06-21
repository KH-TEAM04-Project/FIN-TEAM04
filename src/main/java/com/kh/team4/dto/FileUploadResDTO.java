package com.kh.team4.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResDTO {

    private String fileName;
    private String url;
    private String ProfilePhoto;

    public FileUploadResDTO(String fileName, String url, String ProfilePhoto) {
        this.fileName = fileName;
        this.url = url;
        this.ProfilePhoto = ProfilePhoto;
    }
}