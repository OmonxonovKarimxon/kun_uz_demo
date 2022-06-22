package com.company.controller;

import com.company.dto.article.ArticleDTO;
import com.company.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String fileName = attachService.saveToSystem(file);
        return ResponseEntity.ok().body(fileName);
    }
//
//    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
//    public byte[] open(@PathVariable("fileName") String fileName) {
//        if (fileName != null && fileName.length() > 0) {
//            try {
//                return this.attachService.loadImage(fileName);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new byte[0];
//            }
//        }
//        return null;
//    }

    @GetMapping(value = "/open_general/{idWithUid}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("idWithUid") String fileName) {
        return attachService.open_general(fileName);
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) {
        Resource file = attachService.download(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<?> delete(@PathVariable("fileName") String fileName){
        attachService.delete(fileName);
        return ResponseEntity.ok("delted");
    }

}
