package com.orange.Crisalis;

import com.orange.Crisalis.model.Item;
import com.orange.Crisalis.model.OrderDetail;
import com.orange.Crisalis.repository.ItemRepository;
import com.orange.Crisalis.repository.OrderDetailRepository;

import com.orange.Crisalis.security.Entity.RoleEntity;
import com.orange.Crisalis.security.Enums.RoleName;
import com.orange.Crisalis.security.Repository.IRoleRepository;


import com.orange.Crisalis.security.Service.RoleService;

import com.orange.Crisalis.security.Repository.IUserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class CrisalisApplication {

	public static void main(String[] args) {
        SpringApplication.run(CrisalisApplication.class, args);
    }
	@Bean
	CommandLineRunner commandLineRunner(
			OrderDetailRepository orderDetailRepository,
			ItemRepository itemRepository,

			IUserRepository iusuarioRepository,
			IRoleRepository  roleRepo


	) {
		return args -> {
			Item item = itemRepository
					.save(new Item(null,
							"Celular",
							BigDecimal.valueOf(5000)
					));
			OrderDetail orderDetail = orderDetailRepository
					.save(new OrderDetail(
							null,
							item.getPrice(),
							4.0,
							item)
					);
			System.out.println(orderDetail.toString());

			//RoleEntity admin = roleRepo.save(new RoleEntity(RoleName.ROLE_ADMIN));
			//RoleEntity user = roleRepo.save(new RoleEntity(RoleName.ROLE_USER));
		};

	}


}
