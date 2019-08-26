package com.hrd.somchbab.repository.provider;

import com.hrd.somchbab.repository.model.Classroom;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ClassroomProvider {

    public String findAllByCourseId(int id) {
        return new SQL(){{

        }}.toString();
    }

    public String findAll() {
        return new SQL(){{

        }}.toString();
    }

    public String add(Classroom classroom) {
        return new SQL(){{

        }}.toString();
    }

    public String update(Classroom classroom) {
        return new SQL(){{

        }}.toString();
    }

    public String delete(int id) {
        return new SQL(){{

        }}.toString();
    }

    public String findByID(int id) {
        return new SQL(){{

        }}.toString();
    }

    public String findByName(String name) {
        return new SQL(){{

        }}.toString();
    }

    public String findByUserId(@Param("id") int id) {
        System.out.println(id);
        return new SQL(){{
            SELECT("cn.NAME classname, c.*");
            FROM("sc_classrooms C");
            INNER_JOIN("sc_class_names cn ON C.class_name_id = cn.ID ");
            WHERE("C.ID IN(SELECT en.classroom_id FROM sc_classenrolls en INNER JOIN sc_users u ON u.ID = en.user_id WHERE u.ID = " + id + ")");
        }}.toString();
    }
}
