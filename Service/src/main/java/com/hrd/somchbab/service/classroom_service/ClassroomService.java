package com.hrd.somchbab.service.classroom_service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.ClassName;
import com.hrd.somchbab.repository.model.Classroom;
import com.hrd.somchbab.repository.model.User;
import org.springframework.stereotype.Service;

import java.security.KeyStore;
import java.util.List;
@Service
public interface ClassroomService {
    List<Classroom> findAllByCourseId(int id);
    List<Classroom> findAll();
    void add(Classroom classroom);
    void update(Classroom classroom);
    void delete(int id);
    Classroom findByID(int id);
    List<Classroom> findByName(String name);
    List<Classroom> findByUserId(int id);
    List<Classroom> findClassroomWithNameByCourseId(int id);

    int findCourseIdByClassroomId(Integer x);

    List<Classroom> filterClassroomsByCourseId(int i, Paging paging);

    List<Classroom> filterClassroomsByGenerationId(int i, Paging paging);

    List<Classroom> filterClassroomsByAcademicId(int i, Paging paging);

    List<Classroom> filterClassroomsByNameAndCourseId(String fullName, int id, Paging paging);

    List<Classroom> filterClassroomsByNameAndGenerationId(String fullName, int id, Paging paging);

    List<Classroom> filterClassroomsByNameAndAcademicId(String fullName, int id, Paging paging);

    List<Classroom> findAllClassrooms();

    List<Classroom> findAllClassroomsByPaging(Paging paging);

    List<Classroom> findClassroomsByName(String fullName, Paging paging);

}
