package com.hrd.somchbab.repository.provider;

import com.hrd.somchbab.repository.model.User;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    public String add(User user) {
        return new SQL() {{
            INSERT_INTO("sc_users");
            VALUES("fullname", "#{fullName}");
            VALUES("gender", "#{gender}");
            VALUES("nationality", "#{nationality}");
            VALUES("phone", "#{phone}");
            VALUES("email", "#{email}");
            VALUES("pob", "#{pob}");
            VALUES("dob", "#{dob}");
            VALUES("photo", "#{photo}");
            VALUES("password", "#{password}");
            VALUES("created_by", "#{createdBy}");
        }}.toString();
    }

    public String update(User user) {
        return new SQL() {{
            UPDATE("sc_users");
            SET("fullname = #{fullName}");
            SET("gender = #{gender}");
            SET("phone = #{phone}");
            SET("email = #{email}");
            SET("dob = #{dob}");
            SET("created_by = #{createdBy}");
            SET("photo = #{photo}");
            SET("nationality = #{nationality}");
            SET("pob = #{pob}");
            SET("password = #{password}");
            WHERE("id = #{id}");
            SET("status = #{status}");
        //  SET("roles = #{roles}");
        //  SET("deleted = #{deleted}");


        }}.toString();
    }

    public String delete(@Param("id") int id) {
        return new SQL() {{
            UPDATE("sc_users");
            SET("deleted = true");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String findById(@Param("id") int id) {
        return new SQL() {{
            SELECT("* from sc_users");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String findAll() {
        return new SQL() {{
            SELECT("* from sc_users");
        }}.toString();
    }

    public String findAllByRoleName(String role) {
        return new SQL() {{
            SELECT("u.* from sc_users u");
            INNER_JOIN("sc_user_roles ur ON u.id = ur.user_id");
            INNER_JOIN("sc_roles r ON r.id = ur.role_id");
            WHERE("r.role=#{role} AND u.deleted=FALSE");
        }}.toString();
    }

    public String findStudentsByName(String fullName){
        return new SQL(){{
            SELECT("SELECT *");
        }}.toString();
    }

}
