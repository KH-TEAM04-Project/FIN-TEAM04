package com.kh.team4.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResDTO {

    String fileName;
    String url;

    public FileUploadResDTO(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }
}