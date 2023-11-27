package com.orange.Crisalis;

import com.orange.Crisalis.repository.*;

import com.orange.Crisalis.security.Entity.RoleEntity;
import com.orange.Crisalis.security.Entity.UserEntity;
import com.orange.Crisalis.security.Enums.RoleName;
import com.orange.Crisalis.security.Repository.IRoleRepository;



import com.orange.Crisalis.security.Repository.IUserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@ComponentScan(basePackages = "com.orange.Crisalis")
public class CrisalisApplication {

	public static void main(String[] args) {
        SpringApplication.run(CrisalisApplication.class, args);
    }

}
