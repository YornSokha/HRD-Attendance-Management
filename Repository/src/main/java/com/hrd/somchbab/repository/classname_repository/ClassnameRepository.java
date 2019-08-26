package com.hrd.somchbab.repository.classname_repository;

import com.hrd.somchbab.repository.model.Academic;
import com.hrd.somchbab.repository.model.ClassName;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClassnameRepository {
    @Select("SELECT * FROM sc_class_names where deleted = 'f'")
    List<ClassName> findAll();

    @Insert("INSERT INTO sc_class_names(name) VALUES(#{name})")
    void add(ClassName className);

    @Update("UPDATE sc_class_names SET name=#{name}, status=#{status},deleted=#{deleted} WHERE id=#{id}")
    void update(ClassName className);

    @Select("SELECT * FROM sc_class_names WHERE deleted = 'f' and id=#{id}")
    ClassName find(int id);

    @Delete("DELETE FROM sc_class_names WHERE id =#{id}")
    void delete(int id);

    @Select("SELECT cln.*, cou.status course_status, cou.name AS course_name FROM sc_class_names AS cln INNER JOIN sc_classrooms AS clr ON cln.id = clr.class_name_id INNER JOIN sc_courses AS cou ON cou.id = clr.course_id WHERE cln.deleted = 'f' and cou.id = #{course.id}")
    @Results({
            @Result(property = "classroom.course.name", column = "course_name"),
            @Result(property = "classroom.course.id", column = "course_id"),
            @Result(property = "classroom.course.status", column = "course_status")
    })
    List<ClassName> findClassNameByID(int id);

    ClassName findById(int id);

    @Select("SELECT id FROM sc_class_names where deleted = 'f' ORDER BY id DESC LIMIT 1")
    int getLastId();
}
