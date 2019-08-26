package com.hrd.somchbab.repository.course_repository;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Course;
import com.hrd.somchbab.repository.provider.CourseProvider;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository {

    @Select("SELECT * FROM sc_courses WHERE deleted = false")
    List<Course> findAll();

    @Insert("INSERT INTO sc_courses (name,generation_id) VALUES(#{name},#{generation.id})")
    void add(Course course);

//    @Insert("INSERT INTO sc_courses (name) VALUES(#{name})")
//    void addAPI(Course course);

    @Update("UPDATE sc_courses SET name = #{name},status = #{status} WHERE id = #{id}")
    void update(Course course);

    @Select("SELECT cou.*, gen.status generation_status, gen.name AS generation_name FROM sc_courses AS cou INNER JOIN sc_generations AS gen ON cou.generation_id = gen.id WHERE cou.deleted = FALSE and cou.id = #{id}")
    @Results({
            @Result(property = "generation.name", column = "generation_name"),
            @Result(property = "generation.id", column = "generation_id"),
            @Result(property = "generation.status", column = "generation_status")
    })
    Course find(@Param("id") int id);

    @Select("SELECT cou.*, gen.status generation_status, gen.name AS generation_name FROM sc_courses AS cou INNER JOIN sc_generations AS gen ON cou.generation_id = gen.id WHERE gen.id = #{id} AND cou.deleted = FALSE ORDER BY cou.id DESC")
    @Results({
            @Result(property = "generation.name", column = "generation_name"),
            @Result(property = "generation.id", column = "generation_id"),
            @Result(property = "generation.status", column = "generation_status")
    })
    List<Course> findByGenerationId(@Param("id") int id);

    @Delete("UPDATE sc_courses SET deleted = true WHERE id = #{id}")
    void delete(@Param("id") int id);

    @Select("SELECT * FROM sc_courses WHERE name ilike '%'|| #{name} ||'%'")
    List<Course> findByName(@Param("name") String name);

    @SelectProvider(method = "findByStudentId", type = CourseProvider.class)
    List<Course> findByStudentId(@Param("generationId") int generationId, @Param("userId") int userId);

    @Select("SELECT id FROM sc_courses ORDER BY id DESC LIMIT 1")
    int getLastId();

    /////////////////////////////start filter//////////////////////

    @Select("SELECT co.* from sc_courses co INNER JOIN sc_generations g on g.id = co.generation_id WHERE co.name ILIKE '%' || #{name} || '%' AND g.id = #{id} AND co.deleted = FALSE ORDER BY co.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    List<Course> filterCoursesByNameAndGenerationId(@Param("name") String name, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT co.* from sc_courses co INNER JOIN sc_generations g on g.id = co.generation_id INNER JOIN sc_academics a on a.id = g.academic_id WHERE co.name ILIKE '%' || #{name} || '%' AND a.id = #{id} AND co.deleted = FALSE ORDER BY co.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    List<Course> filterCoursesByNameAndAcademicId(@Param("name") String name, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT co.* from sc_courses co INNER JOIN sc_generations g on g.id = co.generation_id WHERE g.id = #{id} AND co.deleted = FALSE ORDER BY co.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    List<Course> filterCoursesByGenerationId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT co.* from sc_courses co INNER JOIN sc_generations g on g.id = co.generation_id INNER JOIN sc_academics a on a.id = g.academic_id WHERE a.id = #{id} AND co.deleted = FALSE ORDER BY co.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    List<Course> filterCoursesByAcademicId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT * FROM sc_courses WHERE deleted = false  ORDER BY id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    List<Course> findAllCoursesByPaging(@Param("paging") Paging paging);

    List<Course> findAllCourses();

    @Select("SELECT co.* from sc_courses co WHERE name ILIKE '%' || #{name} || '%' AND co.deleted = FALSE ORDER BY co.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    List<Course> findCoursesByName(@Param("name") String name, @Param("paging") Paging paging);
}
