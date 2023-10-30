package com.orange.Crisalis.security.Dto;

import com.orange.Crisalis.security.Entity.UserEntity;

import java.io.Serializable;

public class GetUserDTO implements Serializable {
    private String username;
    private String name;
    private String email;
    private String role;


    public GetUserDTO(UserEntity user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRoles().toArray().length == 2 ? "admin" : "user";
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
