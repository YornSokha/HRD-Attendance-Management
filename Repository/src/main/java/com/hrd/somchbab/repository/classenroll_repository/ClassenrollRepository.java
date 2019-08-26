package com.hrd.somchbab.repository.classenroll_repository;

import com.hrd.somchbab.repository.model.Classenroll;
import com.hrd.somchbab.repository.provider.ClassenrollProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassenrollRepository {
    @SelectProvider(method = "findIdByUserIdAndClassroomId", type = ClassenrollProvider.class)
    Integer findIdByUserIdAndClassroomId(@Param("userId") int userId, @Param("classroomID") int classroomId);

    @Insert("INSERT INTO sc_classenrolls(user_id, classroom_id) VALUES(#{user.id}, #{classroom.id})")
    void enrollStudent(Classenroll classenroll);
}
