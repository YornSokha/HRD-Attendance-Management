package com.hrd.somchbab.repository;

import com.hrd.somchbab.repository.model.Role;
import com.hrd.somchbab.repository.model.User;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepository {

    @Select("SELECT * FROM sc_users where email = #{email}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "fullname"),
            @Result(property = "createdBy", column = "created_by"),
            @Result(property = "roles", column = "id", many = @Many(select = "findRolesByUserId"))
    })
    User loadUserByEmail(String email);

    @Select("SELECT * FROM sc_roles r INNER JOIN sc_user_roles u on r.id = u.role_id WHERE u.user_id = #{id}")
    List<Role> findRolesByUserId(int id);
}
