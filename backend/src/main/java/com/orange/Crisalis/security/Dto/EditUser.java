/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orange.Crisalis.security.Dto;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Sebastián
 */
public class EditUser {
    @NotBlank
    private String username;
    private String name;
    private String password;
    private String email;
    private Set<String> roles = new HashSet<>();


    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
