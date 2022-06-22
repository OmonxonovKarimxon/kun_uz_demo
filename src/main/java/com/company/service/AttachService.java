package com.company.service;

import com.company.dto.article.ArticleDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.AttachEntity;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;

    private final String attachFolder = "attaches/";

    //create
    public String saveToSystem(MultipartFile file ) {
        try {
            // zari.jpg
            AttachEntity entity = new AttachEntity();

            String pathFolder = getYmDString(); // 2022/06/20

            String extension = getExtension(file.getOriginalFilename()); // jpg

            File folder = new File(attachFolder + pathFolder); // attaches/2022/06/20
            if (!folder.exists()) {
                folder.mkdirs();
            }



            entity.setExtention(extension);
            entity.setOriginName(file.getOriginalFilename());
            entity.setSize(file.getSize());
            entity.setPath(String.valueOf(folder));



            attachRepository.save(entity);

            String fileName = entity.getId() + "." + extension; //  asdas-dasdas-dasdasd-adadsd.jpg

            byte[] bytes = file.getBytes();
            // attaches/2022/06/20/asdas-dasdasd-asdas0asdas.jpg
            Path path = Paths.get(attachFolder + pathFolder + "/" + fileName);
            Files.write(path, bytes);

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public byte[] loadImage(String fileName) {
//        byte[] imageInByte;
//
//        BufferedImage originalImage;
//        try {
//            originalImage = ImageIO.read(new File("attaches/" + fileName));
//        } catch (Exception e) {
//            return new byte[0];
//        }
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(originalImage, "png", baos);
//            baos.flush();
//            imageInByte = baos.toByteArray();
//            baos.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return imageInByte;
//    }

    //read
    public byte[] open_general(String fileName) {
        byte[] data;
        try {
            Optional<AttachEntity> optional = attachRepository.findById(fileName);
            if (optional.isEmpty()) {
                throw new ItemNotFoundEseption("image not found mazgi");
            }
            AttachEntity entity = optional.get();

            // fileName -> zari.jpg
            String path = entity.getPath() + "/" + entity.getId() + "." + entity.getExtention();
            Path file = Paths.get(path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public Resource download(String fileName) {
        try {
            Optional<AttachEntity> optional = attachRepository.findById(fileName);
            if (optional.isEmpty()) {
                throw new ItemNotFoundEseption("we have not this photo mazgi");
            }
            AttachEntity entity = optional.get();
            Path file = Paths.get(entity.getPath() + "/" + entity.getId() + "." + entity.getExtention());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }


    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public void delete(String fileName) {

        Optional<AttachEntity> optional = attachRepository.findById(fileName);
        if (optional.isEmpty()) {
            throw new ItemNotFoundEseption("we have not this photo mazgi");

        }
        AttachEntity entity = optional.get();
        attachRepository.deleteById(fileName);

        try {
            Files.delete(Path.of(entity.getPath() + "/" + entity.getId() + "." + entity.getExtention()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
