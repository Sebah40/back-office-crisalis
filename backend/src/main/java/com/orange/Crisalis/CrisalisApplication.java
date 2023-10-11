package com.orange.Crisalis;

import com.orange.Crisalis.model.Item;
import com.orange.Crisalis.model.OrderDetail;
import com.orange.Crisalis.repository.ItemRepository;
import com.orange.Crisalis.repository.OrderDetailRepository;
import com.orange.Crisalis.security.Repository.iUserRepository;
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
			iUserRepository iusuarioRepository
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
							item));
			System.out.println(orderDetail.toString());

		};

	}


}
