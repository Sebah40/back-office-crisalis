package com.orange.Crisalis.security.Entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.orange.Crisalis.security.Repository.iUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Sebastián
 */
public class MainUser implements UserDetails {
    @Autowired
    iUserRepository iusuarioRepository;

    private String name;
    private String username;
    private String email;
    private String password;
    private boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;

    public MainUser(String name, String username, String email, String password, Collection<? extends GrantedAuthority> authorities, boolean isActive) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    public static MainUser build(UserEntity userEntity) {
        List<GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors
                .toList());
        return new MainUser(userEntity.getNombre(), userEntity.getUsername(), userEntity.getEmail(),
                 userEntity.getPassword(), authorities, userEntity.isActive());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
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
        return true;
    }

}
