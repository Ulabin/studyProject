package com.springboot.studyproject.web;

import com.springboot.studyproject.web.dto.UploadResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {
    @Value("${com.springboot.studyproject.upload.path}")
    private String uploadPath;

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(String fileName){
        ResponseEntity<byte[]> result = null;

        try{
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");
            log.info(srcFileName);
            File file = new File(uploadPath+File.separator+srcFileName);
            log.info(file.getPath());
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result= new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/files")
    public ResponseEntity<UploadResultDto> uploadFile(MultipartFile uploadFile){
        String originalFilename = uploadFile.getOriginalFilename();
        String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\")+1);

        String uuid = UUID.randomUUID().toString();

        String saveName = uploadPath + File.separator + uuid + "_" + fileName;

        Path savePath = Paths.get(saveName);
        try{
            uploadFile.transferTo(savePath);
        }catch(IOException e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(UploadResultDto.builder().uuid(uuid).fileName(fileName).build(), HttpStatus.OK);
    }
}
