package com.sundaymvp.invoice_api.controller;

import com.sundaymvp.invoice_api.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Upload file
     */
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(

            @RequestParam("file") MultipartFile file,

            @RequestParam("folder") String folder) {

        String filename =
                fileStorageService.uploadFile(file, folder);

        return ResponseEntity.ok(filename);
    }

    /**
     * Download/View file
     */
    @GetMapping("/{folder}/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(

            @PathVariable String folder,

            @PathVariable String filename) {

        Resource resource =
                fileStorageService.loadFile(folder, filename);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Delete file
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{folder}/{filename:.+}")
    public ResponseEntity<String> deleteFile(

            @PathVariable String folder,

            @PathVariable String filename) {

        fileStorageService.deleteFile(folder, filename);

        return ResponseEntity.ok("File deleted successfully.");
    }
}