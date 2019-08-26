package com.hrd.somchbab.repository.user_repository;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Classroom;
import com.hrd.somchbab.repository.model.Role;
import com.hrd.somchbab.repository.model.User;
import com.hrd.somchbab.repository.provider.UserProvider;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    @InsertProvider(method = "add", type = UserProvider.class)
    int add(User user);

    @UpdateProvider(method = "update", type = UserProvider.class)
    void update(User user);

    @DeleteProvider(method = "delete", type = UserProvider.class)
    void delete(@Param("id") int id);

    @SelectProvider(method = "findById", type = UserProvider.class)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "password", column = "password"),
            @Result(property = "status", column = "status"),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "email", column = "email"),
            @Result(property = "dob", column = "dob"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "photo", column = "photo"),
            @Result(property = "pob", column = "pob"),
            @Result(property = "nationality", column = "nationality"),
            @Result(property = "roles", column = "id", many = @Many(select = "findRolesById")),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsUntilAcademicByUserId"))

    })
    User findById(@Param("id") int id);

    @Select("SELECT c.id classroom_id, cn.name classname, cn.id classname_id , co.id course_id, co.name course_name, gen.id generation_id, gen.name generation_name, aca.id academic_id, aca.name academic_name FROM sc_classrooms c INNER JOIN sc_classenrolls en on c.id = en.classroom_id INNER JOIN sc_class_names cn ON cn.ID = C.class_name_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations gen on gen.id = co.generation_id INNER JOIN sc_academics aca on aca.id = gen.academic_id WHERE en.deleted = false and en.user_id = #{id}")
    @Results({
            @Result(property = "id", column = "classroom_id"),
            @Result(property = "className.id", column = "classname_id"),
            @Result(property = "className.name", column = "classname"),
            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.name", column = "course_name"),
            @Result(property = "course.generation.name", column = "generation_name"),
            @Result(property = "course.generation.id",column = "generation_id"),
            @Result(property = "course.generation.academic.id",column = "academic_id"),
            @Result(property = "course.generation.academic.name",column = "academic_name")

    })
    List<Classroom> findClassroomsUntilAcademicByUserId(int id);



    @Select("SELECT * FROM sc_roles r INNER JOIN sc_user_roles u on r.id = u.role_id WHERE u.user_id = #{id}")
    List<Role> findRolesById(int id);

    @SelectProvider(method = "findAll", type = UserProvider.class)
    List<User> findAll();

    //    @SelectProvider(method = "findStudentsByName", type = UserProvider.class)
    @Select("\t\n" +
            "\tSELECT\n" +
            "\tu.* from sc_users u\n" +
            "\tINNER JOIN sc_user_roles ur ON u.ID = ur.user_id\n" +
            "\tINNER JOIN sc_roles r ON r.ID = ur.role_id \n" +
            "WHERE\n" +
            "\tu.fullname ILIKE'%' || #{fullName} || '%' \n" +
            "\tAND r.ROLE = 'STUDENT' \n" +
            "\tAND u.deleted = FALSE \n" +
            "ORDER BY\n" +
            "\tu.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> findStudentsByName(@Param("fullName") String fullName, @Param("paging") Paging paging);

    @Select("\t\n" +
            "\tSELECT\n" +
            "\tu.* from sc_users u\n" +
            "\tINNER JOIN sc_user_roles ur ON u.ID = ur.user_id\n" +
            "\tINNER JOIN sc_roles r ON r.ID = ur.role_id \n" +
            "WHERE\n" +
            "\tu.fullname ILIKE'%' || #{fullName} || '%' \n" +
            "\tAND r.ROLE = 'DIRECTOR' \n" +
            "\tAND u.deleted = FALSE \n" +
            "ORDER BY\n" +
            "\tu.fullname ASC")
    @Results({
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
//            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
//            @Result(property = "classroom.id", column = "classroom_id"),
//            @Result(property = "classroom.className.name", column = "classname"),
//            @Result(property = "classroom.className.id", column = "classname_id")
    })
    List<User> findDirectorByName(@Param("fullName") String fullName);

    @Select("\t\n" +
            "\tSELECT\n" +
            "\tu.* from sc_users u\n" +
            "\tINNER JOIN sc_user_roles ur ON u.ID = ur.user_id\n" +
            "\tINNER JOIN sc_roles r ON r.ID = ur.role_id \n" +
            "WHERE\n" +
            "\tu.fullname ILIKE'%' || #{fullName} || '%' \n" +
            "\tAND r.ROLE = 'ADMIN' \n" +
            "\tAND u.deleted = FALSE \n" +
            "ORDER BY\n" +
            "\tu.fullname ASC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
//            @Result(property = "classroom.id", column = "classroom_id"),
//            @Result(property = "classroom.className.name", column = "classname"),
//            @Result(property = "classroom.className.id", column = "classname_id")
    })
    List<User> findAdminsByName(@Param("fullName") String fullName);

    @Select("\t\n" +
            "\tSELECT\n" +
            "\tu.* from sc_users u\n" +
            "\tINNER JOIN sc_user_roles ur ON u.ID = ur.user_id\n" +
            "\tINNER JOIN sc_roles r ON r.ID = ur.role_id \n" +
            "WHERE\n" +
            "\tu.fullname ILIKE'%' || #{fullName} || '%' \n" +
            "\tAND r.ROLE = 'TEACHER' \n" +
            "\tAND u.deleted = FALSE \n" +
            "ORDER BY\n" +
            "\tu.fullname ASC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
//            @Result(property = "classroom.id", column = "classroom_id"),
//            @Result(property = "classroom.className.name", column = "classname"),
//            @Result(property = "classroom.className.id", column = "classname_id")
    })
    List<User> findTeachersByName(@Param("fullName") String fullName);

    @Select("SELECT c.id classroom_id, cn.name classname, cn.id classname_id FROM sc_classrooms c LEFT JOIN sc_classenrolls en on c.id = en.classroom_id LEFT JOIN sc_class_names cn ON cn.ID = C.class_name_id WHERE en.deleted = false and en.user_id = #{id}")
    @Results({
            @Result(property = "id", column = "classroom_id"),
            @Result(property = "className.id", column = "classname_id"),
            @Result(property = "className.name", column = "classname")
    })
    List<Classroom> findClassroomsBy(int id);


    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_user_roles ur on u.id = ur.user_id INNER JOIN sc_roles r on r.id = ur.role_id where u.fullname ilike '%' || #{fullName} || '%' and r.role = #{role} AND u.deleted = FALSE ORDER BY u.fullname ASC")
    List<User> findByName(@Param("fullName") String fullName, @Param("role") String role);

    @SelectProvider(method = "findAllByRoleName", type = UserProvider.class)
    List<User> findByRoleName(String role);

    @Insert("INSERT INTO sc_user_roles(user_id, role_id) VALUES(#{userId}, #{roleId})")
    void addRoleToUser(@Param("userId") int userId, @Param("roleId") int roleId);

    @Select("SELECT u.id FROM sc_users u where deleted = false ORDER BY id DESC LIMIT 1")
    int getLastId();

    @Select("SELECT\n" +
            "\tu.* from sc_users u\n" +
            "\tINNER JOIN sc_user_roles ur ON u.ID = ur.user_id\n" +
            "\tINNER JOIN sc_roles r ON r.ID = ur.role_id \n" +
            "WHERE r.ROLE = 'STUDENT' \n" +
            "\tAND u.deleted = FALSE \n" +
            "ORDER BY\n" +
            "\tu.fullname ASC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> findAllStudents();

    @Select("SELECT u.* from sc_users u INNER JOIN sc_user_roles ur on u.id = ur.user_id INNER JOIN sc_roles r on r.id = ur.role_id where r.id = 3 and u.deleted = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsTeachBy"))
    })
    List<User> findAllTeachers();

    @Select("SELECT c.id classroom_id, cn.name classname, cn.id classname_id FROM sc_classrooms c INNER JOIN sc_teacher_assignments ta on c.id = ta.classroom_id INNER JOIN sc_class_names cn ON cn.ID = C.class_name_id WHERE ta.user_id = #{id}")
    @Results({
            @Result(property = "id", column = "classroom_id"),
            @Result(property = "className.id", column = "classname_id"),
            @Result(property = "className.name", column = "classname")
    })
    List<Classroom> findClassroomsTeachBy(int id);


    //////////////////////// FILTER BLOCK //////////////////////

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_classenrolls en ON u.ID = en.user_id INNER JOIN sc_classrooms C ON C.ID = en.classroom_id  where c.id = #{id} AND u.fullname ILIKE'%' || #{fullName} || '%' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterStudentsByNameAndClassroomId(@Param("fullName") String fullName,@Param("id") int id, @Param("paging") Paging paging);

    @Select("\tSELECT u.* FROM sc_users u INNER JOIN sc_classenrolls en ON u.ID = en.user_id INNER JOIN sc_classrooms C ON C.ID = en.classroom_id INNER JOIN sc_courses co on co.id = c.course_id where co.id = #{id} AND u.fullname ILIKE'%' || #{fullName} || '%' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterStudentsByNameAndCourseId(@Param("fullName") String fullName,@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_classenrolls en ON u.ID = en.user_id INNER JOIN sc_classrooms C ON C.ID = en.classroom_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g on g.id = co.generation_id where g.id = #{id} AND u.fullname ILIKE'%' || #{fullName} || '%' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterStudentsByNameAndGenerationId(@Param("fullName") String fullName,@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_classenrolls en ON u.ID = en.user_id INNER JOIN sc_classrooms C ON C.ID = en.classroom_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g on g.id = co.generation_id INNER JOIN sc_academics a on a.id = g.academic_id where a.id = #{id} AND u.fullname ILIKE'%' || #{fullName} || '%' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterStudentsByNameAndAcademicId(@Param("fullName") String fullName,@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_classenrolls en ON u.ID = en.user_id INNER JOIN sc_classrooms C ON C.ID = en.classroom_id  where c.id = #{id} AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterStudentsByClassroomId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("\tSELECT u.* FROM sc_users u INNER JOIN sc_classenrolls en ON u.ID = en.user_id INNER JOIN sc_classrooms C ON C.ID = en.classroom_id INNER JOIN sc_courses co on co.id = c.course_id where co.id = #{id} AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterStudentsByCourseId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_classenrolls en ON u.ID = en.user_id INNER JOIN sc_classrooms C ON C.ID = en.classroom_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g on g.id = co.generation_id where g.id = #{id} AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterStudentsByGenerationId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_classenrolls en ON u.ID = en.user_id INNER JOIN sc_classrooms C ON C.ID = en.classroom_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g on g.id = co.generation_id INNER JOIN sc_academics a on a.id = g.academic_id where a.id = #{id} AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterStudentsByAcademicId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* from sc_users u INNER JOIN sc_user_roles ur ON u.ID = ur.user_id INNER JOIN sc_roles r ON r.ID = ur.role_id WHERE r.ROLE = 'STUDENT' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> findAllStudentsByPaging(@Param("paging") Paging paging);

    /////////// FILTER TEACHER///////////////

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta ON u.ID = ta.user_id INNER JOIN sc_classrooms C ON C.ID = ta.classroom_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g on g.id = co.generation_id INNER JOIN sc_academics a on a.id = g.academic_id where a.id = #{id} AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
    })
    List<User> filterTeachersByAcademicId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta ON u.ID = ta.user_id INNER JOIN sc_classrooms C ON C.ID = ta.classroom_id  where c.id = #{id} AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterTeachersByClassroomId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("\tSELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta ON u.ID = ta.user_id INNER JOIN sc_classrooms C ON C.ID = ta.classroom_id INNER JOIN sc_courses co on co.id = c.course_id where co.id = #{id} AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterTeachersByCourseId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta ON u.ID = ta.user_id INNER JOIN sc_classrooms C ON C.ID = ta.classroom_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g on g.id = co.generation_id where g.id = #{id} AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterTeachersByGenerationId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta ON u.ID = ta.user_id INNER JOIN sc_classrooms C ON C.ID = ta.classroom_id  where c.id = #{id} AND u.fullname ILIKE'%' || #{fullName} || '%' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
    })
    List<User> filterTeachersByNameAndClassroomId(@Param("fullName") String fullName, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta ON u.ID = ta.user_id INNER JOIN sc_classrooms C ON C.ID = ta.classroom_id INNER JOIN sc_courses co on co.id = c.course_id where co.id = #{id} AND u.fullname ILIKE'%' || #{fullName} || '%' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterTeachersByNameAndCourseId(@Param("fullName") String fullName, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta ON u.ID = ta.user_id INNER JOIN sc_classrooms C ON C.ID = ta.classroom_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g on g.id = co.generation_id where g.id = #{id} AND u.fullname ILIKE'%' || #{fullName} || '%' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterTeachersByNameAndGenerationId(@Param("fullName") String fullName, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta ON u.ID = ta.user_id INNER JOIN sc_classrooms C ON C.ID = ta.classroom_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g on g.id = co.generation_id INNER JOIN sc_academics a on a.id = g.academic_id where a.id = #{id} AND u.fullname ILIKE'%' || #{fullName} || '%' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterTeachersByNameAndAcademicId(@Param("fullName") String fullName, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT u.* from sc_users u INNER JOIN sc_user_roles ur ON u.ID = ur.user_id INNER JOIN sc_roles r ON r.ID = ur.role_id WHERE r.ROLE = 'TEACHER' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> findAllTeachersByPaging(@Param("paging") Paging paging);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta on u.id = ta.user_id INNER JOIN sc_classrooms c on c.id = ta.classroom_id where u.deleted = false and c.id = #{id}")
    List<User> findClassTeacherByClassroomId(@Param("id") int id);

    @Select("SELECT u.* FROM sc_users u INNER JOIN sc_teacher_assignments ta ON u.ID = ta.user_id INNER JOIN sc_classrooms C ON C.ID = ta.classroom_id  where u.fullname ILIKE '%' || #{fullName} || '%' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> filterTeachersByName(@Param("fullName") String fullName,@Param("paging") Paging paging);

    /////////////////// End FILTER BLOCK //////////////////////


    /////////////////// admin////////////////

    @Select("SELECT u.* from sc_users u INNER JOIN sc_user_roles ur ON u.ID = ur.user_id INNER JOIN sc_roles r ON r.ID = ur.role_id WHERE u.fullname ILIKE '%' || #{name} || '%' AND r.ROLE = 'ADMIN' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> findAdminsByNameAndPaging(@Param("name") String name,@Param("paging") Paging paging);

    @Select("SELECT u.* from sc_users u INNER JOIN sc_user_roles ur ON u.ID = ur.user_id INNER JOIN sc_roles r ON r.ID = ur.role_id WHERE r.ROLE = 'ADMIN' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> findAllAdminByPaging(@Param("paging") Paging paging);

    ////////////////director//////////////
    @Select("SELECT u.* from sc_users u INNER JOIN sc_user_roles ur ON u.ID = ur.user_id INNER JOIN sc_roles r ON r.ID = ur.role_id WHERE r.ROLE = 'DIRECTOR' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> findAllDirectorByPaging(@Param("paging") Paging paging);

    @Select("SELECT u.* from sc_users u INNER JOIN sc_user_roles ur ON u.ID = ur.user_id INNER JOIN sc_roles r ON r.ID = ur.role_id WHERE u.fullname ILIKE '%' || #{name} || '%' AND r.ROLE = 'DIRECTOR' AND u.deleted = FALSE ORDER BY u.fullname ASC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "classrooms", column = "id", many = @Many(select = "findClassroomsBy"))
    })
    List<User> findDirectorsByNameAndPaging(@Param("name") String name,@Param("paging") Paging paging);

    @Select("SELECT\n" +
            "\tu.* from sc_users u\n" +
            "\tINNER JOIN sc_user_roles ur ON u.ID = ur.user_id\n" +
            "\tINNER JOIN sc_roles r ON r.ID = ur.role_id \n" +
            "WHERE r.ROLE = 'DIRECTOR' \n" +
            "\tAND u.deleted = FALSE \n" +
            "ORDER BY\n" +
            "\tu.fullname ASC")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
    })
    List<User> findAllDirectors();
}
