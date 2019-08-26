package com.hrd.somchbab.repository.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.swing.JFileChooser;

@Component
@ConfigurationProperties(prefix = "file")
public class ReportStorageProperties {
    private String uploadDir;

    public ReportStorageProperties() {
        String DEFAULT_DOCUMENT_LOCATION = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        this.uploadDir  = DEFAULT_DOCUMENT_LOCATION + "/SomChbab-Report/";
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}