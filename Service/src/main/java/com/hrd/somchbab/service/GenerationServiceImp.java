package com.hrd.somchbab.service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.generation_repository.GenerationRepository;
import com.hrd.somchbab.repository.model.Generation;
import com.hrd.somchbab.service.generation_service.GenerationService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerationServiceImp implements GenerationService {
    @Autowired
    GenerationRepository generationRepository;

    @Override
    public List<Generation> findAll() {
        return generationRepository.findAll();
    }

    @Override
    public void add(Generation generation) {
        if (generation != null)
            generationRepository.add(generation);
    }

    @Override
    public void addAPI(Generation generation) {
        generationRepository.addAPI(generation);
    }

    @Override
    public void update(Generation generation) {
        if (generation != null)
            generationRepository.update(generation);
    }

    @Override
    public Generation findById(int id) {
        return generationRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        generationRepository.delete(id);
    }

    @Override
    public Generation findByClassroomId(@Param("id") int id) {
        return generationRepository.findByClassroomId(id);
    }

    @Override
    public List<Generation> findByName(String name) {
        return generationRepository.findByName(name);
    }
    @Override
    public List<Generation> getGenerationInITE() {
        return generationRepository.getGenerationInITE();
    }
    @Override
    public List<Generation> findByAcademicId(int id) {
        return generationRepository.findByAcademicId(id);
    }

    @Override
    public List<Generation> findByStudentId(int academicId, int userId) {
        return generationRepository.findByStudentId(academicId, userId);
    }

    @Override
    public int getLastId() {
        return generationRepository.getLastId();
    }

//    @Override
//    public List<Generation> findAllGenerations() {
//        return generationRepository.findAllGenerations();
//    }

    @Override
    public List<Generation> filterGenerationsByNameAndAcademicId(String fullName, int id, Paging paging) {
        return generationRepository.filterGenerationsByNameAndAcademicId(fullName,id,paging);
    }

    @Override
    public List<Generation> filterGenerationsByAcademicId(int id, Paging paging) {
        return generationRepository.filterGenerationsByAcademicId(id,paging);
    }

    @Override
    public List<Generation> findAllGenerationsByPaging(Paging paging) {
        return generationRepository.findAllGenerationsByPaging(paging);
    }

    @Override
    public List<Generation> findGenerationsByName(String fullName, Paging paging) {
        return generationRepository.findGenerationsByName(fullName,paging);
    }
}
