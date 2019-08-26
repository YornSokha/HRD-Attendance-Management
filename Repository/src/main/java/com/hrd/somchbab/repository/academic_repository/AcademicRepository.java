package com.hrd.somchbab.repository.academic_repository;

import com.hrd.somchbab.helper.Paging;
import com.hrd.somchbab.repository.model.Academic;
import com.hrd.somchbab.repository.provider.AcademicProvider;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicRepository {

    @Select("SELECT * FROM sc_academics WHERE deleted = false")
    List<Academic> findAll();

    @Insert("INSERT INTO sc_academics(name) VALUES(#{name})")
    void add(Academic academic);

    @Update("UPDATE sc_academics SET name=#{name}, status=#{status} WHERE id=#{id}")
    void update(Academic academic);

    @Select("SELECT * FROM sc_academics WHERE deleted = false and id=#{id}")
    Academic find(int id);

    @Delete("UPDATE sc_academics SET deleted = true WHERE id=#{id}")
    void delete(int id);

    @Select("SELECT * FROM sc_academics WHERE deleted = false and name ilike '%'|| #{name} ||'%' ORDER BY id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    List<Academic> findByName(@Param("name")String name,@Param("paging")Paging paging);

    @SelectProvider(method = "findAllByStudentID", type = AcademicProvider.class)
    List<Academic> findAllByStudentID(@Param("id") int id);

    @Select("SELECT * FROM sc_academics where deleted = false ORDER BY id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}")
    List<Academic> findAllAcademicByPanging(@Param("paging")Paging paging);
}
