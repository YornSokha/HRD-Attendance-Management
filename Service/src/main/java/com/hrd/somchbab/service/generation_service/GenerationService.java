package com.hrd.somchbab.service.generation_service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Course;
import com.hrd.somchbab.repository.model.Generation;

import org.apache.ibatis.annotations.Param;

import java.security.KeyStore;
import java.util.List;

public interface GenerationService {
    List<Generation> findAll();
    void add(Generation generation);
    void addAPI(Generation generation);
    void update(Generation generation);
    Generation findById(int id);
    void delete(int id);
    Generation findByClassroomId(@Param("id") int id);
    List<Generation> getGenerationInITE();
    List<Generation> findByName(String name);
    List<Generation> findByAcademicId(int id);
    List<Generation> findByStudentId(int academicId, int userId);
    int getLastId();

    /////////////////////start filter////////////////////

    List<Generation> filterGenerationsByNameAndAcademicId(String fullName, int id, Paging paging);

    List<Generation> filterGenerationsByAcademicId(int id, Paging paging);

    List<Generation> findAllGenerationsByPaging(Paging paging);

    List<Generation> findGenerationsByName(String fullName, Paging paging);
}
