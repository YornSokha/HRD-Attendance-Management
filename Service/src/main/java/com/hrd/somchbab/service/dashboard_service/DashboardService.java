package com.hrd.somchbab.service.dashboard_service;

import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    String findRequestingPermission();
    String findRequestedPermission();
    String findMostLate();
}
