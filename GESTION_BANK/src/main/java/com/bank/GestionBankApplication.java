package com.bank;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bank.security.IRoleDAO;
import com.bank.security.IUserDAO;
import com.bank.security.Role;
import com.bank.security.User;


@SpringBootApplication
public class GestionBankApplication implements CommandLineRunner {

	
	public static void main(String[] args) {
		SpringApplication.run(GestionBankApplication.class, args);
	}

	@Autowired
	IRoleDAO roledao;
	@Autowired
	IUserDAO userdao;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		User user1 = new User("m.azoui", "123", null, true);
		User user2 = new User("m.hamda", "123", null, true);
		
		Role role1 = new Role("admin", "admin role description");
		Role role2 = new Role("user" , "user role description");
		Set<Role> roles = new HashSet<>();
		roles.add(role1);
		user1.setRoles(roles);
		
		roles = null;
		roles = new HashSet<>();
		roles.add(role2);
		user2.setRoles(roles);
		
		
		roledao.save(role1);
		roledao.save(role2);
		
		userdao.save(user1);
		userdao.save(user2);
	}

	
}
