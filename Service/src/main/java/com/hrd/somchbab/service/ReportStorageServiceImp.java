package com.hrd.somchbab.service;

import com.hrd.somchbab.repository.reportstorage_repository.ReportStorageRepository;
import com.hrd.somchbab.service.reportstorage_service.ReportStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReportStorageServiceImp implements ReportStorageService {
    @Autowired
    ReportStorageRepository reportStorageRepository;

    @Override
    public Resource loadFileAsResource(String fileName) {
        return reportStorageRepository.loadFileAsResource(fileName);
    }
}
