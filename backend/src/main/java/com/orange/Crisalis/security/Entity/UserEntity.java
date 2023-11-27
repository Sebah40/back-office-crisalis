package com.orange.Crisalis.security.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sebastián
 */
@Data
@Getter
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setBirthdate(Date birthdate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthdate);
        Date updatedDate = calendar.getTime();
        calendar.add(Calendar.HOUR, 3);
        this.birthdate = calendar.getTime();
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    @NotNull
    @JsonIgnore
    private String password;

    private boolean isActive;

    private String photo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
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
