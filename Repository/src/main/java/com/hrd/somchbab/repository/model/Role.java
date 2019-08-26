package com.hrd.somchbab.repository.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
    private int id;
    private String role;
    public Role() {
    }
    @Override
    public String getAuthority() {
        return "ROLE_" + role;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
