package com.hrd.somchbab.repository.reportstorage_repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportFileNotFoundException extends RuntimeException {
    public ReportFileNotFoundException(String message) {
        super(message);
    }

    public ReportFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
