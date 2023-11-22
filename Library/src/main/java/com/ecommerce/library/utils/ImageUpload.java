package com.ecommerce.library.utils;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImageUpload {
    private final String IMAGE_UPLOAD_FOLDER ="/home/ubuntu/giftose/Images-product";


    public @NotNull List<String> uploadToLocalAndReadyImages(List<MultipartFile> multipartFiles) {

        File uploadDir = new File(IMAGE_UPLOAD_FOLDER);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        List<String> readyImages = new ArrayList<>();

        try{
            for(MultipartFile file : multipartFiles){


                String originalFileName = file.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));

                String uniqueFilename = UUID.randomUUID().toString()+fileExtension;

                Path destination = Path.of(IMAGE_UPLOAD_FOLDER, uniqueFilename);

                while ((Files.exists(destination))){
                    uniqueFilename = UUID.randomUUID().toString()+fileExtension;
                    destination = Path.of(IMAGE_UPLOAD_FOLDER, uniqueFilename);
                }

                Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
                readyImages.add(uniqueFilename);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return readyImages;
    }


}
