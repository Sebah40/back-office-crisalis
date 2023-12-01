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
    @Bean
    CommandLineRunner commandLineRunner(
            IUserRepository iUserRepository,
            IRoleRepository  roleRepo,
            PasswordEncoder passwordEncoder



    ) {
        return args -> {
            RoleEntity admin = new RoleEntity(RoleName.ROLE_ADMIN);
            RoleEntity user = new RoleEntity(RoleName.ROLE_USER);
            if(roleRepo.findAll().isEmpty()){

                roleRepo.save(admin);

                roleRepo.save(user);
                UserEntity adminUser = new UserEntity();
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setUsername("admin");
                adminUser.setName("admin");
                adminUser.setEmail("admin@admin.com");
                adminUser.setActive(true);
                Set<RoleEntity> roles = new HashSet<>();
                roles.add(admin);
                roles.add(user);
                adminUser.setRoles(roles);

                iUserRepository.save(adminUser);

            }








        };
    }
}
