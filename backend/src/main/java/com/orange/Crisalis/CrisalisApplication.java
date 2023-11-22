package com.orange.Crisalis;

import com.orange.Crisalis.repository.*;

import com.orange.Crisalis.security.Entity.RoleEntity;
import com.orange.Crisalis.security.Enums.RoleName;
import com.orange.Crisalis.security.Repository.IRoleRepository;



import com.orange.Crisalis.security.Repository.IUserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrisalisApplication {

	public static void main(String[] args) {
        SpringApplication.run(CrisalisApplication.class, args);
    }

}
