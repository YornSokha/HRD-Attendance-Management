package com.hrd.somchbab.service;

import com.hrd.somchbab.repository.dashboard_repository.DashboardRepository;
import com.hrd.somchbab.service.dashboard_service.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImp implements DashboardService {

    @Autowired
    DashboardRepository dashboardRepository;

    @Override
    public String findRequestingPermission() {
        return dashboardRepository.findRequestingPermission();
    }

    @Override
    public String findRequestedPermission() {
        return dashboardRepository.findRequestedPermission();
    }

    @Override
    public String findMostLate() {
        return dashboardRepository.findMostLate();
    }
}
