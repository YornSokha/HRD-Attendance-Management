package com.hrd.somchbab.service;

import com.hrd.somchbab.repository.classenroll_repository.ClassenrollRepository;
import com.hrd.somchbab.repository.model.Classenroll;
import com.hrd.somchbab.service.classenroll_service.ClassenrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassenrollServiceImp implements ClassenrollService {
    @Autowired
    ClassenrollRepository classenrollRepository;
    @Override
    public Integer findIdByUserIdAndClassroomId(int userId, int classroomId) {
        return classenrollRepository.findIdByUserIdAndClassroomId(userId, classroomId);
    }

    @Override
    public void enrollStudent(Classenroll classenroll) {
        classenrollRepository.enrollStudent(classenroll);
    }
}
