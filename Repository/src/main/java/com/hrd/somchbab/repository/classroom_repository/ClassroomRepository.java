package com.hrd.somchbab.repository.classroom_repository;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Classroom;
import com.hrd.somchbab.repository.provider.ClassroomProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClassroomRepository {
//    @SelectProvider(method = "findAll", type = ClassroomProvider.class)
    @Select("SELECT clr.*, cln.id class_id,cln.name class_name FROM sc_academics aca INNER JOIN sc_generations gen ON aca.id = gen.academic_id INNER JOIN sc_courses cou on gen.id = cou.generation_id INNER JOIN sc_classrooms clr on cou.id = clr.course_id INNER JOIN sc_class_names cln on cln.id = clr.class_name_id where clr.deleted='f'")
    @Results({
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> findAll();
//    @SelectProvider(method = "findAllByCourseId", type = ClassroomProvider.class)
    @Select("SELECT clr.*,cln.id class_id,cln.name class_name FROM sc_academics aca INNER JOIN sc_generations gen ON aca.id = gen.academic_id INNER JOIN sc_courses cou on gen.id = cou.generation_id INNER JOIN sc_classrooms clr on cou.id = clr.course_id INNER JOIN sc_class_names cln on cln.id = clr.class_name_id WHERE clr.course_id=#{id} and  clr.deleted='f'")
    @Results({
            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.name", column = "course_name"),
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> findAllByCourseId(@Param("id") int id);

//    @InsertProvider(method = "add", type = ClassroomProvider.class)
    @Insert("INSERT INTO sc_classrooms (course_id,class_name_id) VALUES( #{course.id},#{className.id})")
    void add(Classroom classroom);

//    @UpdateProvider(method = "update", type = ClassroomProvider.class)
    @Update("UPDATE sc_classrooms SET status = #{status} WHERE id = #{id}")
    void update(Classroom classroom);

//    @SelectProvider(method = "findByID", type = ClassroomProvider.class)
    @Select("SELECT clr.*,aca.name as academic_name,gen.name as generation_name,cou.name as course_name,cln.name as classname_name,clr.status from sc_classrooms as clr INNER JOIN sc_class_names cln on cln.id=clr.class_name_id INNER JOIN sc_courses as cou on cou.id=clr.course_id INNER JOIN sc_generations as gen on gen.id=cou.generation_id INNER JOIN sc_academics as aca on aca.id=gen.academic_id WHERE clr.id=#{id} and clr.deleted='f'")
    @Results({
            @Result(property = "course.id", column = "course_id"),
            @Result(property = "course.name", column = "course_name"),
            @Result(property = "className.id", column = "class_name_id"),
            @Result(property = "className.name", column = "classname_name"),
            @Result(property = "course.generation.id", column = "generation_id"),
            @Result(property = "course.generation.name", column = "generation_name"),
            @Result(property = "course.generation.academic.id", column = "academic_id"),
            @Result(property = "course.generation.academic.name", column = "academic_name"),
    })
    Classroom findByID(@Param("id") int id);

//    @UpdateProvider(method = "delete", type = ClassroomProvider.class)
    @Update("UPDATE sc_classrooms SET deleted = 't' WHERE id = #{id}")
    void delete(@Param("id") int id);


//    @SelectProvider(method = "findByName", type = ClassroomProvider.class)

    @Select("SELECT clr.*,cln.id,cln.name FROM sc_classrooms as clr INNER JOIN sc_class_names as cln on cln.id=clr.class_name_id WHERE cln.name ilike '%'|| #{name} ||'%' and clr.deleted='f' ORDER BY clr.id DESC")
    @Results({
            @Result(property = "className.id",column = "id"),
            @Result(property = "className.name",column = "name")
    })
    List<Classroom> findByName(@Param("name") String name);

    @SelectProvider(method = "findByUserId", type = ClassroomProvider.class)
    @Results({
            @Result(property = "className.name", column = "classname"),
            @Result(property = "className.id", column = "class_name_id")
    })
    List<Classroom> findByUserId(@Param("id") int id);
    @Select("SELECT C.*,cn.NAME FROM sc_courses co INNER JOIN sc_classrooms C ON co.ID = C.course_id INNER JOIN sc_class_names cn ON cn.ID = C.class_name_id WHERE co.id = #{id} AND c.status != FALSE and c.deleted=false")
    @Results({
            @Result(property = "className.name", column = "name"),
            @Result(property = "className.id", column = "class_name_id")
    })
    List<Classroom> findClassroomWithNameByCourseId(@Param("id") int id);

    @Select("SELECT co.id course_id from sc_classrooms c INNER JOIN sc_courses co on co.id = c.course_id WHERE c.deleted= false and c.id = #{id}")
    int findCourseIdByClassroomId(Integer id);


    //////////////////////// FILTER BLOCK //////////////////////

    @Select("SELECT c.*, cn.ID class_id, cn.NAME class_name FROM sc_classrooms c INNER JOIN sc_class_names cn ON cn.ID = c.class_name_id INNER JOIN sc_courses co on co.id = c.course_id WHERE co.id = #{id} AND c.deleted = FALSE ORDER BY c.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> filterClassroomsByCourseId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT c.*, cn.ID class_id, cn.NAME class_name FROM sc_classrooms c INNER JOIN sc_class_names cn ON cn.ID = c.class_name_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g ON g.id = co.generation_id WHERE g.id = #{id} AND c.deleted = FALSE ORDER BY c.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> filterClassroomsByGenerationId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT c.*, cn.ID class_id, cn.NAME class_name FROM sc_classrooms c INNER JOIN sc_class_names cn ON cn.ID = c.class_name_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g ON g.id = co.generation_id INNER JOIN sc_academics a ON a.id = g.academic_id WHERE a.id = #{id} AND c.deleted = FALSE ORDER BY c.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> filterClassroomsByAcademicId(@Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT c.*, cn.ID class_id, cn.NAME class_name FROM sc_classrooms c INNER JOIN sc_class_names cn ON cn.ID = c.class_name_id INNER JOIN sc_courses co on co.id = c.course_id WHERE cn.name ILIKE '%' || #{name} || '%' AND co.id = #{id} AND c.deleted = FALSE ORDER BY c.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> filterClassroomsByNameAndCourseId(@Param("name") String name, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT c.*, cn.ID class_id, cn.NAME class_name FROM sc_classrooms c INNER JOIN sc_class_names cn ON cn.ID = c.class_name_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g ON g.id = co.generation_id WHERE cn.name ILIKE '%' || #{name} || '%' AND g.id = #{id} AND c.deleted = FALSE ORDER BY c.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> filterClassroomsByNameAndGenerationId(@Param("name") String name, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT c.*, cn.ID class_id, cn.NAME class_name FROM sc_classrooms c INNER JOIN sc_class_names cn ON cn.ID = c.class_name_id INNER JOIN sc_courses co on co.id = c.course_id INNER JOIN sc_generations g ON g.id = co.generation_id INNER JOIN sc_academics a ON a.id = g.academic_id WHERE cn.name ILIKE '%' || #{name} || '%' AND a.id = #{id} AND c.deleted = FALSE ORDER BY c.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> filterClassroomsByNameAndAcademicId(@Param("name") String name, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT * FROM sc_classrooms c WHERE deleted = FALSE")
    List<Classroom> findAllClassrooms();

    @Select("SELECT c.*, cn.ID class_id, cn.NAME class_name FROM sc_classrooms c INNER JOIN sc_class_names cn ON cn.ID = c.class_name_id WHERE c.deleted = FALSE ORDER BY c.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> findAllClassroomsByPaging(@Param("paging") Paging paging);

    @Select("SELECT c.*, cn.ID class_id, cn.NAME class_name FROM sc_classrooms c INNER JOIN sc_class_names cn ON cn.ID = c.class_name_id WHERE cn.name ILIKE '%' || #{name} || '%' AND c.deleted = FALSE ORDER BY c.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "className.id",column = "class_id"),
            @Result(property = "className.name",column = "class_name")
    })
    List<Classroom> findClassroomsByName(@Param("name") String name, @Param("paging") Paging paging);
}
