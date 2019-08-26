package com.hrd.somchbab.repository;

import com.hrd.somchbab.repository.model.Role;
import com.hrd.somchbab.repository.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;
import java.util.Set;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().toString()));
        this.user = user;
    }

    public List<Role> getRoles(){
        return user.getRoles();
    }

    public User getUser() {
        return user;
    }

    public Integer getId() {
        return user.getId();
    }


    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
