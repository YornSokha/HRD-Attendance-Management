package com.hrd.somchbab.helper;

import com.hrd.somchbab.repository.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
@PropertySource("classpath:application.properties")
public class UploadImage {
    public  boolean upload(User user, MultipartFile file, String path) {
        String imageName = UUID.randomUUID().toString();
        String extension;
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
            imageName += extension;
            try {
                Files.copy(file.getInputStream(), Paths.get(path + imageName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setPhoto(imageName);
            return true;
        }else {
            imageName = "default-profile.jpg";
            user.setPhoto(imageName);
        }
        return false;
    }
}
