package com.hrd.somchbab.service;

import com.hrd.somchbab.repository.classname_repository.ClassnameRepository;
import com.hrd.somchbab.repository.model.ClassName;
import com.hrd.somchbab.service.classname_service.ClassnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassnameServiceImp implements ClassnameService {
    @Autowired
    ClassnameRepository classnameRepository;
    @Override
    public List<ClassName> findAll() {return classnameRepository.findAll(); }

    @Override
    public void add(ClassName className) {classnameRepository.add(className);    }

    @Override
    public void update(ClassName className) {classnameRepository.update(className);}

    @Override
    public ClassName find(int id) { return classnameRepository.find(id); }

    @Override
    public void delete(int id) { classnameRepository.delete(id); }

    @Override
    public List<ClassName> findClassNameByID(int id) {
        return classnameRepository.findClassNameByID(id);
    }

    @Override
    public ClassName findById(int id) {
        return classnameRepository.findById(id);
    }

    @Override
    public int getLastId() {
        return classnameRepository.getLastId();
    }
}
