package com.hrd.somchbab.repository.generation_repository;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Generation;
import com.hrd.somchbab.repository.provider.GenerationProvider;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerationRepository {
    @Select("SELECT * from sc_generations WHERE deleted = false")
    List<Generation> findAll();

    @Select("SELECT * from sc_generations where deleted = false and academic_id=1 ORDER BY id desc")
    List<Generation> getGenerationInITE();


    @Insert("INSERT INTO sc_generations (name, academic_id) VALUES(#{name}, #{academic.id})")
    void add(Generation generation);

    @Insert("INSERT INTO sc_generations (name, academic_id) VALUES(#{name}, 1)")
    void addAPI(Generation generation);

    @Update("UPDATE sc_generations SET name = #{name},status = #{status} WHERE id = #{id}")
    void update(Generation generation);

    @Select("SELECT gen.*, aca.status academic_status, aca.name academic_name FROM sc_generations gen INNER JOIN sc_academics aca ON gen.academic_id=aca.id WHERE gen.deleted = false and gen.id = #{id}")
    @Results({
            @Result(property = "academic.name", column = "academic_name"),
            @Result(property = "academic.id", column = "academic_id"),
            @Result(property = "academic.status", column = "academic_status")
    })
    Generation findById(int id);

    @Select("SELECT gen.*, aca.status academic_status, aca.name AS academic_name FROM sc_generations AS gen INNER JOIN sc_academics AS aca ON gen.academic_id=aca.id WHERE aca.id = #{id} AND gen.deleted = FALSE ORDER BY gen.id")
    @Results({
            @Result(property = "academic.name", column = "academic_name"),
            @Result(property = "academic.id", column = "academic_id"),
            @Result(property = "academic.status", column = "academic_status")
    })
    List<Generation> findByAcademicId(int id);

    @Delete("UPDATE sc_generations SET deleted = true WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    void delete(int id);

    @Select("SELECT * FROM sc_generations where deleted = false and name ilike '%' || #{name} || '%'")
    List<Generation> findByName(@Param("name") String name);

    @SelectProvider(method = "findByStudentId", type = GenerationProvider.class)
    List<Generation> findByStudentId(@Param("academicId") int academicId, @Param("userId") int userId);

    @SelectProvider(method = "findByClassroomId", type = GenerationProvider.class)
    Generation findByClassroomId(@Param("id") int id);

    @Select("SELECT id FROM sc_generations where deleted = false ORDER BY id DESC LIMIT 1")
    int getLastId();

    @Select("SELECT gen.* FROM sc_generations gen INNER JOIN sc_academics aca ON aca.id = gen.academic_id where aca.id = #{id} AND gen.name ILIKE'%' || #{name} || '%' AND gen.deleted = 'f' ORDER BY gen.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    List<Generation> filterGenerationsByNameAndAcademicId(@Param("name") String name, @Param("id") int id, @Param("paging") Paging paging);

    @Select("SELECT gen.* FROM sc_generations gen INNER JOIN sc_academics aca ON aca.id = gen.academic_id where aca.id = #{id} AND gen.deleted = 'f' ORDER BY gen.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    List<Generation> filterGenerationsByAcademicId(@Param("id") int id,@Param("paging") Paging paging);

    @Select("SELECT gen.*, aca.status academic_status, aca.name AS academic_name FROM sc_generations AS gen INNER JOIN sc_academics AS aca ON gen.academic_id=aca.id where gen.deleted = 'f' ORDER BY id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
//            "SELECT * from sc_generations where status = 't' AND deleted = 'f' ORDER BY id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")

    @Results({
            @Result(property = "academic.name", column = "academic_name"),
            @Result(property = "academic.id", column = "academic_id")
    })
    List<Generation> findAllGenerationsByPaging(@Param("paging") Paging paging);

    @Select("SELECT * from sc_generations gen where gen.name ILIKE '%' || #{name} || '%' AND gen.deleted = 'f' ORDER BY gen.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    List<Generation> findGenerationsByName(@Param("name") String name,@Param("paging") Paging paging);
}
