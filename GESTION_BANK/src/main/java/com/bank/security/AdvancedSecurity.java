package com.bank.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class AdvancedSecurity extends WebSecurityConfigurerAdapter {
	
	/*
	 * with in memory user password managment
	 * @Autowired
	public void config(AuthenticationManagerBuilder auth) {
		try {
			auth.inMemoryAuthentication().withUser("admin").password("{noop}123").roles("admin");
			auth.inMemoryAuthentication().withUser("user").password("{noop}123").roles("user");
			auth.inMemoryAuthentication().withUser("manager").password("{noop}123").roles("manager");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
//	with database password user managment
	@Autowired
	public void globalConfig(AuthenticationManagerBuilder auth, DataSource dataSource) {
		// TODO Auto-generated method stub
		try {
			auth.jdbcAuthentication()
				.dataSource(dataSource)
					.usersByUsernameQuery("select username as principal, password as credential , actived from utilisateur where username = ?")
						.authoritiesByUsernameQuery("select user_username as principal , roles_libelle as role from utilisateur_role where user_username = ?")
							.passwordEncoder(NoOpPasswordEncoder.getInstance())
							  .rolePrefix("ROLE_");
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
					.antMatchers("/css/**","/js/**")
						.permitAll()
							.anyRequest()
								.authenticated()
		.and()
			.formLogin()
				.loginPage("/login")
					.permitAll()
						.defaultSuccessUrl("/comptes",true)
							.failureUrl("/error");
	}
	

}
