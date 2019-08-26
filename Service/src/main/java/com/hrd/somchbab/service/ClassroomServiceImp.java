package com.hrd.somchbab.service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.classroom_repository.ClassroomRepository;
import com.hrd.somchbab.repository.model.Classroom;
import com.hrd.somchbab.service.classroom_service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassroomServiceImp implements ClassroomService {
    @Autowired
    ClassroomRepository classroomRepository;

    @Override
    public List<Classroom> findAllByCourseId(int id) {
        return classroomRepository.findAllByCourseId(id);
    }

    @Override
    public List<Classroom> findAll() {return classroomRepository.findAll(); }

    @Override
    public void add(Classroom classroom) { classroomRepository.add(classroom);}

    @Override
    public void update(Classroom classroom) {classroomRepository.update(classroom);    }

    @Override
    public void delete(int id) { classroomRepository.delete(id); }

    @Override
    public Classroom findByID(int id) {
        return classroomRepository.findByID(id);
    }

    @Override
    public List<Classroom> findByName(String name) {
        return classroomRepository.findByName(name);
    }

    @Override
    public List<Classroom> findByUserId(int id) {
        return classroomRepository.findByUserId(id);
    }

    @Override
    public List<Classroom> findClassroomWithNameByCourseId(int id) {
        return classroomRepository.findClassroomWithNameByCourseId(id);
    }

    @Override
    public int findCourseIdByClassroomId(Integer x) {
        return classroomRepository.findCourseIdByClassroomId(x);
    }

    @Override
    public List<Classroom> filterClassroomsByCourseId(int i, Paging paging) {
        return classroomRepository.filterClassroomsByCourseId(i,paging);
    }

    @Override
    public List<Classroom> filterClassroomsByGenerationId(int i, Paging paging) {
        return classroomRepository.filterClassroomsByGenerationId(i,paging);
    }

    @Override
    public List<Classroom> filterClassroomsByAcademicId(int i, Paging paging) {
        return classroomRepository.filterClassroomsByAcademicId(i,paging);
    }

    @Override
    public List<Classroom> filterClassroomsByNameAndCourseId(String fullName, int id, Paging paging) {
        return classroomRepository.filterClassroomsByNameAndCourseId(fullName,id,paging);
    }

    @Override
    public List<Classroom> filterClassroomsByNameAndGenerationId(String fullName, int id, Paging paging) {
        return classroomRepository.filterClassroomsByNameAndGenerationId(fullName,id,paging);
    }

    @Override
    public List<Classroom> filterClassroomsByNameAndAcademicId(String fullName, int id, Paging paging) {
        return classroomRepository.filterClassroomsByNameAndAcademicId(fullName,id,paging);
    }

    @Override
    public List<Classroom> findAllClassroomsByPaging(Paging paging) {
        return classroomRepository.findAllClassroomsByPaging(paging);
    }

    @Override
    public List<Classroom> findAllClassrooms() {
        return classroomRepository.findAllClassrooms();
    }

    @Override
    public List<Classroom> findClassroomsByName(String fullName, Paging paging) {
        return classroomRepository.findClassroomsByName(fullName,paging);
    }


}
