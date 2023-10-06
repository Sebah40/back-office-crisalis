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
public class DisableUsuario {
    @NotBlank
    private String nombreUsuario;


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

}
