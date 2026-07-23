package com.sundaymvp.invoice_api.service;

import com.sundaymvp.invoice_api.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;
import java.util.Set;

@SuppressWarnings("null")
@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path uploadPath;
    
    /**
 * Allowed image content types.
 */
private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
        "image/jpeg",
        "image/png",
        "image/webp"
);

/**
 * Allowed file extensions.
 */
private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
        ".jpg",
        ".jpeg",
        ".png",
        ".webp"
);

/**
 * Maximum file size (10 MB).
 */
private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    @PostConstruct
    public void init() {

        try {

            uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            Files.createDirectories(uploadPath);

            Files.createDirectories(uploadPath.resolve("company-logos"));
            Files.createDirectories(uploadPath.resolve("product-images"));
            Files.createDirectories(uploadPath.resolve("profile-images"));

        } catch (IOException ex) {

            throw new RuntimeException("Could not create upload directories.", ex);
        }
    }

    /**
     * Upload file to a specific folder.
     */
    public String uploadFile(MultipartFile file, String folder) {

        if (file.isEmpty()) {
            throw new  FileStorageException("Cannot upload an empty file.");
        }

        // Validate file size
if (file.getSize() > MAX_FILE_SIZE) {
    throw new FileStorageException(
            "Maximum upload size is 10MB.");
}

// Validate content type
String contentType = file.getContentType();

if (contentType == null
        || !ALLOWED_CONTENT_TYPES.contains(contentType)) {

    throw new FileStorageException (
            "Only JPG, JPEG, PNG and WEBP images are allowed.");
}

        String originalFilename =
                StringUtils.cleanPath(file.getOriginalFilename());

        String extension = "";

        int index = originalFilename.lastIndexOf('.');

        if (index > 0) {
            extension = originalFilename.substring(index);
        }

        // Validate extension
if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {

    throw new FileStorageException(
            "Invalid file extension. Only JPG, JPEG, PNG and WEBP images are allowed.");
}

        String filename =
                UUID.randomUUID() + extension;

        try {

            Path targetLocation =
                    uploadPath.resolve(folder).resolve(filename);

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING);

            return filename;

        } catch (IOException ex) {

            throw new RuntimeException("Could not store file.", ex);
        }
    }

    /**
     * Load a file.
     */
    public Resource loadFile(String folder, String filename) {

        try {

            Path filePath =
                    uploadPath.resolve(folder).resolve(filename);

            Resource resource =
                    new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            }

            throw new  FileStorageException("File not found.");

        } catch (MalformedURLException ex) {

            throw new RuntimeException("File not found.", ex);
        }
    }

    /**
     * Delete a file.
     */
    public void deleteFile(String folder, String filename) {

        try {

            Path filePath =
                    uploadPath.resolve(folder).resolve(filename);

            Files.deleteIfExists(filePath);

        } catch (IOException ex) {

            throw new RuntimeException("Could not delete file.", ex);
        }
    }

}