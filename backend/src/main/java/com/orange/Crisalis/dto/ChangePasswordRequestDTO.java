package com.orange.Crisalis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public class ChangePasswordRequestDTO {
    private String oldPassword;
    private String newPassword;
}
