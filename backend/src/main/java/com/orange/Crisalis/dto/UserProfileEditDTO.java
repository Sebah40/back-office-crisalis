package com.orange.Crisalis.dto;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileEditDTO {
    private String name;
    private String email;
    private Date birthdate;
}
