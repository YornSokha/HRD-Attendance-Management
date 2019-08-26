package com.hrd.somchbab.repository.dashboard_repository;

import com.hrd.somchbab.repository.provider.DashboardProvider;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository {

    @SelectProvider(method = "findRequestingPermission", type = DashboardProvider.class)
    String findRequestingPermission();

    @SelectProvider(method = "findRequestedPermission", type = DashboardProvider.class)
    String findRequestedPermission();

    @SelectProvider(method = "findMostLate", type = DashboardProvider.class)
    @Results({
            @Result(property = "String", column = "fullname")
    })
    String findMostLate();
}
