package com.gym_membership.storage;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gym_membership.exceptions.FileStorageException;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String uploadProfileImage(MultipartFile file) {

        if (file.isEmpty()) {
        	throw new FileStorageException("Please select a file to upload.");
        }
        
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new FileStorageException("Maximum file size is 5 MB.");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                !(contentType.equals("image/jpeg")
                        || contentType.equals("image/png")
                        || contentType.equals("image/jpg"))) {

        	throw new FileStorageException(
        	        "Only JPG, JPEG and PNG image files are allowed.");
        }
        
        try {

            BufferedImage image = ImageIO.read(file.getInputStream());

            if (image == null) {
                throw new FileStorageException("Invalid image file.");
            }

            if (image.getWidth() > 2000 || image.getHeight() > 2000) {
                throw new FileStorageException(
                        "Image resolution should not exceed 2000 x 2000 pixels.");
            }

        } catch (IOException e) {

            throw new FileStorageException(
                    "Unable to read uploaded image.", e);
        }

        try {

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalName = file.getOriginalFilename();

            String extension = originalName.substring(originalName.lastIndexOf("."));

            String fileName = UUID.randomUUID() + extension;

            Path targetLocation = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException e) {

            throw new FileStorageException("Failed to upload image.");

        }

    }

}
