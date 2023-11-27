package com.orange.Crisalis.dto;

import com.orange.Crisalis.security.Entity.UserEntity;
import lombok.*;

import java.util.Date;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String name;
    private String username;
    private String email;
    private String photo;
    private Date birthdate;

    public UserProfileDTO(UserEntity userEntity){
        this.name = userEntity.getName();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.photo = userEntity.getPhoto();
        this.birthdate = userEntity.getBirthdate();
    }
}
