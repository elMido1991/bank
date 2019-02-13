package com.bank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class Security extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void config(AuthenticationManagerBuilder auth) {
		try {
			auth.inMemoryAuthentication().withUser("admin").password("{noop}123").roles("admin");
			auth.inMemoryAuthentication().withUser("user").password("{noop}123").roles("user");
			auth.inMemoryAuthentication().withUser("manager").password("{noop}123").roles("manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//chaque requete doit etre authentifié
		http
		.csrf()
			.disable()
				.authorizeRequests()
					.anyRequest()
						.authenticated()
		.and()
			.formLogin()
				.loginPage("/login")
					.permitAll()
						.defaultSuccessUrl("/comptes",true)
							.failureUrl("/error")
		.and()
			.logout()
				.invalidateHttpSession(true)
					.logoutUrl("/login")
						.permitAll();
	}

}
