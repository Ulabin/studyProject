package com.springboot.studyproject.web.dto;

import lombok.Builder;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UploadResultDto implements Serializable {
    private String fileName;
    private String uuid;

    public String getImgUrl() {
        try {
            return URLEncoder.encode("/"+uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Builder
    public UploadResultDto(String fileName, String uuid){
        this.fileName=fileName;
        this.uuid=uuid;
    }
}
