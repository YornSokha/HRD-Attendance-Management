package com.hrd.somchbab.service.currentuser;


import com.hrd.somchbab.repository.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Integer userId);
    boolean adminAccess(CurrentUser currentUser);
    boolean studentAccess(CurrentUser currentUser);
    boolean adminAndDirectorAccess(CurrentUser currentUser);
}
