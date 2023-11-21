package com.orange.Crisalis.security.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sebastián
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String email;
    @NotNull
    @JsonIgnore
    private String password;

    private boolean isActive;

    private String photo;
    private Date birthdate;




    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name ="user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();


    @PrePersist
    public void prePersist(){
        this.isActive = true;
        this.photo = "http://localhost:3000/user/perfil.png";
    }

//    public List getUser(){
//        if(this.getRoles().toArray().length == 2)                                               // Esto va a dar problemas si decidimos crear más roles en un futuro
//            return List.of(new String[]{this.getName(),this.getUsername(), this.getEmail(), "Administrador"});
//        return List.of(new String[]{this.getName(),this.getUsername(), this.getEmail(), "Usuario"});
//
//    }
}
