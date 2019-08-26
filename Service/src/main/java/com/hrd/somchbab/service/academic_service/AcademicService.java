package com.hrd.somchbab.service.academic_service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Academic;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AcademicService {
    List<Academic> findAll();
    void add(Academic academic);
    void update(Academic academic);
    Academic findById(int id);
    void delete(int id);
    List<Academic> findByName(@Param("name")String name,@Param("paging")Paging paging);
    List<Academic> findAllByStudentID(@Param("id") int id);

    List<Academic> findAllAcademicByPanging(@Param("paging")Paging paging);
}
