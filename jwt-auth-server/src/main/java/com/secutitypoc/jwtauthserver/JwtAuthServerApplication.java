package com.secutitypoc.jwtauthserver;

import com.secutitypoc.jwtauthserver.models.Role;
import com.secutitypoc.jwtauthserver.models.RoleName;
import com.secutitypoc.jwtauthserver.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.List;


@SpringBootApplication
@EnableDiscoveryClient
public class JwtAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthServerApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	//@Bean
	public ApplicationRunner setupRoles(){
		return args -> {
			roleRepository.saveAll(List.of(
					new Role(RoleName.ROLE_USER),
					new Role(RoleName.ROLE_ADMIN)
			));
		};
	}

}
