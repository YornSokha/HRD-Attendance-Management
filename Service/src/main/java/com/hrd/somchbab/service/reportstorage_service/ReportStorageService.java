package com.hrd.somchbab.service.reportstorage_service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

public interface ReportStorageService {
    Resource loadFileAsResource(String fileName);
}