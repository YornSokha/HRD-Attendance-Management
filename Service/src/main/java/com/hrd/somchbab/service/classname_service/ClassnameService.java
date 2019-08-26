package com.hrd.somchbab.service.classname_service;

import com.hrd.somchbab.repository.model.ClassName;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ClassnameService {
    List<ClassName> findAll();
    void add(ClassName className);
    void update(ClassName className);
    ClassName find(int id);
    void delete(int id);
    List<ClassName> findClassNameByID(int id);
    ClassName findById(int id);
    int getLastId();
}
