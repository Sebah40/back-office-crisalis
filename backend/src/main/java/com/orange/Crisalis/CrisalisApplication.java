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
	@Bean
	CommandLineRunner commandLineRunner(
			OrderDetailRepository orderDetailRepository,

			IUserRepository iusuarioRepository,
			IRoleRepository  roleRepo,

			IClientRepository iClientRepository,
			IEnterpriseRepository iEnterpriseRepository,
			IPersonRepository iPersonRepository



	) {
		return args -> {

			RoleEntity admin = roleRepo.save(new RoleEntity(RoleName.ROLE_ADMIN));
			RoleEntity user = roleRepo.save(new RoleEntity(RoleName.ROLE_USER));

/*
			// hardcodeo una empresa y una persona sólo de prueba
			// Empresa
			EnterpriseEntity enterprise = new EnterpriseEntity(true, "987564", "789456", LocalDate.now(), true, "Pepe", "Perez", "789456123");
			iEnterpriseRepository.save(enterprise);

			System.out.println(enterprise.getId() + "     " + enterprise.getBusinessName() + "        " + enterprise.getLastNameResponsible() + "       " + enterprise.getDate());

			// Persona
			PersonEntity person = new PersonEntity(true, "Dominguez", "Lucas", "38.123.165", true);
			iPersonRepository.save(person);
			System.out.println(person.getId() + "     " + person.getFirstName() + "   " + person.getLastName() + "    " + person.getDni() );
*/
		};
	}
}
