package com.hrd.somchbab.repository.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class User implements UserDetails {
    int id;
    private String fullName;
    String password;
    String gender;
    boolean status;
    boolean deleted;
    String phone;
    String email;
    String dob;
    int createdBy;
    String photo;
    String nationality;
    String pob;
    List<Classroom> classrooms;
    List<Role> roles;
    public User() {
    }

    public User(int id, String fullName, String password, String gender, boolean status, boolean deleted, String phone, String email, String dob, int createdBy, String photo, String nationality, String pob, List<Classroom> classrooms, List<Role> roles) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.gender = gender;
        this.status = status;
        this.deleted = deleted;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
        this.createdBy = createdBy;
        this.photo = photo;
        this.nationality = nationality;
        this.pob = pob;
        this.classrooms = classrooms;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return fullName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", status=" + status +
                ", deleted=" + deleted +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", createdBy=" + createdBy +
                ", photo='" + photo + '\'' +
                ", nationality='" + nationality + '\'' +
                ", pob='" + pob + '\'' +
                ", classrooms=" + classrooms +
                ", roles=" + roles +
                '}';
    }
}
