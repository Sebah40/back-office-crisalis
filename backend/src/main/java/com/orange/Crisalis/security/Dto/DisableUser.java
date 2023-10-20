/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orange.Crisalis.security.Dto;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Sebastián
 */
public class DisableUser {
    @NotBlank
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
