package com.hrd.somchbab.service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.academic_repository.AcademicRepository;
import com.hrd.somchbab.repository.model.Academic;
import com.hrd.somchbab.service.academic_service.AcademicService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicServiceImp implements AcademicService {

    @Autowired
    AcademicRepository academicRepository;

    @Override
    public List<Academic> findAll() {
        return academicRepository.findAll();
    }

    @Override
    public void add(Academic academic) {
        academicRepository.add(academic);
    }

    @Override
    public void update(Academic academic) {
        academicRepository.update(academic);
    }

    @Override
    public Academic findById(int id) {
        return academicRepository.find(id);
    }

    @Override
    public void delete(int id) {
        academicRepository.delete(id);
    }

    @Override
    public List<Academic> findByName(@Param("name")String name,@Param("paging")Paging paging) {
        return academicRepository.findByName(name,paging);
    }
    @Override
    public List<Academic> findAllByStudentID(@Param("id") int id) {

        return academicRepository.findAllByStudentID(id);
    }

    @Override
    public List<Academic> findAllAcademicByPanging(@Param("paging")Paging paging) {
        return academicRepository.findAllAcademicByPanging(paging);
    }
}
