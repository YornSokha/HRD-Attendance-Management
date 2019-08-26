package com.hrd.somchbab.service.currentuser;

import com.hrd.somchbab.repository.CurrentUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {


    @Override
    public boolean canAccessUser(CurrentUser currentUser, Integer userId) {
//        ArrayList<String> roles = (ArrayList<String>) currentUser.getRoles().stream()
//                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return (getUserRoles(currentUser).contains("ROLE_ADMIN") || currentUser.getId().equals(userId));
    }

    @Override
    public boolean adminAccess(CurrentUser currentUser) {
        return (getUserRoles(currentUser).contains("ROLE_ADMIN"));
    }

    private ArrayList<String> getUserRoles(CurrentUser currentUser){
        return  (ArrayList<String>) currentUser.getRoles().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    @Override
    public boolean studentAccess(CurrentUser currentUser) {
        return (getUserRoles(currentUser).contains("ROLE_STUDENT"));
    }

    @Override
    public boolean adminAndDirectorAccess(CurrentUser currentUser) {
        return (getUserRoles(currentUser).contains("ROLE_ADMIN") || getUserRoles(currentUser).contains("ROLE_DIRECTOR"));
    }
}
