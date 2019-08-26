package com.hrd.somchbab.service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.repository.user_repository.UserRepository;
import com.hrd.somchbab.service.user_service.UserService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public int add(User user) {
        return userRepository.add(user);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public User findById(@Param("id") int id) {
        return userRepository.findById(id);
    }

    public List<User> findByName(String fullName, String role) {
        return userRepository.findByName(fullName, role);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllByRoleName(String role) {
        return userRepository.findByRoleName(role);
    }

    @Override
    public void addRoleToUser(int userId, int roleId) {
        userRepository.addRoleToUser(userId, roleId);
    }

    @Override
    public int getLastId() {
        return userRepository.getLastId();
    }

    @Override
    public List<User> findStudentsByName(String fullName, Paging paging) {
        return userRepository.findStudentsByName(fullName, paging);
    }

    @Override
    public List<User> findDirectorsByName(String fullName) {
        return userRepository.findDirectorByName(fullName);
    }
    @Override
    public List<User> findAdminsByName(String fullName) {
        return userRepository.findAdminsByName(fullName);
    }

    @Override
    public List<User> findTeachersByName(String fullName, Paging paging) {
        return userRepository.findTeachersByName(fullName);
    }

    @Override
    public List<User> findAllStudents() {
        return userRepository.findAllStudents();
    }

    @Override
    public List<User> findAllTeachers() {
        return userRepository.findAllTeachers();
    }

    @Override
    public List<User> filterStudentsByNameAndClassroomId(String fullName, int id, Paging paging) {
        return userRepository.filterStudentsByNameAndClassroomId(fullName, id, paging);
    }

    @Override
    public List<User> filterStudentsByNameAndCourseId(String fullName, int id, Paging paging) {
        return userRepository.filterStudentsByNameAndCourseId(fullName, id, paging);
    }

    @Override
    public List<User> filterStudentsByNameAndGenerationId(String fullName, int id, Paging paging) {
        return userRepository.filterStudentsByNameAndGenerationId(fullName, id, paging);
    }

    @Override
    public List<User> filterStudentsByNameAndAcademicId(String fullName, int id, Paging paging) {
        return userRepository.filterStudentsByNameAndAcademicId(fullName, id, paging);
    }

    @Override
    public List<User> filterStudentsByClassroomId(int id, Paging paging) {
        return userRepository.filterStudentsByClassroomId(id, paging);
    }

    @Override
    public List<User> filterStudentsByCourseId(int id, Paging paging) {
        return userRepository.filterStudentsByCourseId(id, paging);
    }

    @Override
    public List<User> filterStudentsByGenerationId(int id, Paging paging) {
        return userRepository.filterStudentsByGenerationId(id, paging);
    }

    @Override
    public List<User> filterStudentsByAcademicId(int id, Paging paging) {
        return userRepository.filterStudentsByAcademicId(id, paging);
    }

    @Override
    public List<User> findAllStudentsByPaging(Paging paging) {
        return userRepository.findAllStudentsByPaging(paging);
    }

    @Override
    public List<User> findAllTeachersByPaging(Paging paging) {
        return userRepository.findAllTeachersByPaging(paging);
    }

    @Override
    public List<User> filterTeachersByClassroomId(int id, Paging paging) {
        return userRepository.filterTeachersByClassroomId(id, paging);
    }

    @Override
    public List<User> filterTeachersByCourseId(int id, Paging paging) {
        return userRepository.filterTeachersByCourseId(id, paging);
    }

    @Override
    public List<User> filterTeachersByGenerationId(int id, Paging paging) {
        return userRepository.filterTeachersByGenerationId(id, paging);
    }

    @Override
    public List<User> filterTeachersByAcademicId(int id, Paging paging) {
        return userRepository.filterTeachersByAcademicId(id, paging);
    }

    @Override
    public List<User> filterTeachersByNameAndClassroomId(String fullName, int id, Paging paging) {
        return userRepository.filterTeachersByNameAndClassroomId(fullName,id, paging);
    }

    @Override
    public List<User> filterTeachersByNameAndCourseId(String fullName, int id, Paging paging) {
        return userRepository.filterTeachersByNameAndCourseId(fullName,id, paging);
    }

    @Override
    public List<User> filterTeachersByNameAndGenerationId(String fullName, int id, Paging paging) {
        return userRepository.filterTeachersByNameAndGenerationId(fullName,id, paging);
    }

    @Override
    public List<User> filterTeachersByNameAndAcademicId(String fullName, int id, Paging paging) {
        return userRepository.filterTeachersByNameAndAcademicId(fullName, id, paging);
    }
    @Override
    public List<User> findClassTeacherByClassroomId(int classroomId) {
        return userRepository.findClassTeacherByClassroomId(classroomId);
    }

    @Override
    public List<User> filterTeachersByName(String fullName, Paging paging) {
        return userRepository.filterTeachersByName(fullName, paging);
    }

    @Override
    public List<User> findAdminsByNameAndPaging(String adminName, Paging paging) {
        return userRepository.findAdminsByNameAndPaging( adminName,  paging);
    }

    @Override
    public List<User> findAllAdminByPaging(Paging paging) {
        return userRepository.findAllAdminByPaging(paging);
    }

    @Override
    public List<User> findAllDirectorByPaging(Paging paging) {
        return userRepository.findAllDirectorByPaging(paging);
    }

    @Override
    public List<User> findDirectorsByNameAndPaging(String name, Paging paging) {
        return userRepository.findDirectorsByNameAndPaging( name, paging);
    }

    @Override
    public List<User> findAllDirectors() {
        return userRepository.findAllDirectors();
    }
}
