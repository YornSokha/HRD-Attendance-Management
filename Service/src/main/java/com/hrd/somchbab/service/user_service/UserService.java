package com.hrd.somchbab.service.user_service;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.User;
import jdk.nashorn.internal.runtime.ScriptObject;

import java.util.List;

public interface UserService {
    int add(User user);
    void update(User user);
    void delete(int id);
    User findById(int id);
    List<User> findAll();
    List<User> findAllByRoleName(String role);
    List<User> findByName(String fullName, String role);

    void addRoleToUser(int userId, int roleId);
    int getLastId();

    List<User> findStudentsByName(String fullName, Paging paging);
    List<User> findDirectorsByName(String fullName);
    List<User> findAdminsByName(String fullName);
    List<User> findTeachersByName(String fullName, Paging paging);
    List<User> findAllStudents();

    List<User> findAllTeachers();

    List<User> filterStudentsByNameAndClassroomId(String fullName, int id, Paging paging);

    List<User> filterStudentsByNameAndCourseId(String fullName, int id, Paging paging);

    List<User> filterStudentsByNameAndGenerationId(String fullName, int id, Paging paging);

    List<User> filterStudentsByNameAndAcademicId(String fullName, int id, Paging paging);

    List<User> filterStudentsByClassroomId(int id, Paging paging);

    List<User> filterStudentsByCourseId(int id, Paging paging);

    List<User> filterStudentsByGenerationId(int id, Paging paging);

    List<User> filterTeachersByClassroomId(int parseInt, Paging paging);

    List<User> filterTeachersByCourseId(int parseInt, Paging paging);

    List<User> filterTeachersByGenerationId(int parseInt, Paging paging);

    List<User> filterTeachersByAcademicId(int parseInt, Paging paging);

    List<User> filterTeachersByNameAndClassroomId(String fullName, int parseInt, Paging paging);

    List<User> filterTeachersByNameAndCourseId(String fullName, int parseInt, Paging paging);

    List<User> filterTeachersByNameAndGenerationId(String fullName, int parseInt, Paging paging);

    List<User> filterTeachersByNameAndAcademicId(String fullName, int parseInt, Paging paging);

    List<User> filterStudentsByAcademicId(int id, Paging paging);

    List<User> findAllStudentsByPaging(Paging paging);

    List<User> findAllTeachersByPaging(Paging paging);

    List<User> findClassTeacherByClassroomId(int classroomId);

    List<User> filterTeachersByName(String fullName, Paging paging);

    List<User> findAdminsByNameAndPaging(String adminName, Paging paging);

    List<User> findAllAdminByPaging(Paging paging);

    List<User> findAllDirectorByPaging(Paging paging);

    List<User> findDirectorsByNameAndPaging(String adminName, Paging paging);

    List<User> findAllDirectors();
}
