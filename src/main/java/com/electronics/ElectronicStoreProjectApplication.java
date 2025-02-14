package com.electronics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.electronics.model.Role;
import com.electronics.repository.RoleRepository;

import jakarta.validation.Valid;

@SpringBootApplication
public class ElectronicStoreProjectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		
		SpringApplication.run(ElectronicStoreProjectApplication.class, args);
	}
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Value("${role.admin}")
	private String role_admin_id;
	
	@Value("${role.consumer}")
	private String role_consumer_id;
	
	@Override
	public void run(String... args) {
		
		Role admin = Role.builder().roleId(role_admin_id).roleName("ADMIN").build();
		Role consumer = Role.builder().roleId(role_consumer_id).roleName("CONSUMER").build();
		roleRepository.save(admin);
		roleRepository.save(consumer);
		
	}

}
