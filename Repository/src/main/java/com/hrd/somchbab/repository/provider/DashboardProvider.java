package com.hrd.somchbab.repository.provider;

import org.apache.ibatis.jdbc.SQL;

public class DashboardProvider {

    public String findRequestingPermission() {
        return new SQL(){{
            SELECT("COUNT(sc_attendances.id)");
            FROM("sc_attendances");
            WHERE("sc_attendances.status = 'p'");
            AND();
            WHERE("sc_attendances.deleted = 'f'");
            AND();
            WHERE("DATE ( sc_attendances.created_at ) = DATE (NOW())");
        }}.toString();
    }

    public String findRequestedPermission() {
        return new SQL(){{
            SELECT("COUNT(sc_attendances.ID)");
            FROM("sc_attendances");
            WHERE("created_at BETWEEN NOW() :: DATE - EXTRACT (DOW FROM NOW()) :: INTEGER - 7 AND NOW() :: DATE - EXTRACT (DOW FROM NOW()) :: INTEGER");
        }}.toString();
    }

    public String findMostLate() {
        return new SQL(){{
            SELECT("MAX(sc_users.fullname) as fullname");
            FROM("sc_classenrolls");
            INNER_JOIN("sc_attendances ON sc_attendances.classenroll_id = sc_classenrolls.id");
            INNER_JOIN("sc_users ON sc_classenrolls.user_id = sc_users.id");
            WHERE("sc_attendances.leave_status = 'l'");
            AND();
            WHERE("sc_attendances.created_at BETWEEN NOW()::DATE-EXTRACT(DOW FROM NOW())::INTEGER-30 AND NOW()::DATE-EXTRACT(DOW from NOW())::INTEGER");
            GROUP_BY("sc_users.fullname LIMIT 1");
        }}.toString();
    }
}
