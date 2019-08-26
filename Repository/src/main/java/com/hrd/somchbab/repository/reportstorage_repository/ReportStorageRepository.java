package com.hrd.somchbab.repository.reportstorage_repository;

import com.hrd.somchbab.repository.model.ReportStorageProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class ReportStorageRepository {
    private final Path reportStorageLocation;

    @Autowired
    public ReportStorageRepository(ReportStorageProperties reportStorageProperties) {
        this.reportStorageLocation = Paths.get(reportStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.reportStorageLocation);
        }
        catch (Exception ex) {
            throw new ReportStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.reportStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
            else {
                throw new ReportFileNotFoundException("File not found " + fileName);
            }
        }
        catch (MalformedURLException ex) {
            throw new ReportFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
