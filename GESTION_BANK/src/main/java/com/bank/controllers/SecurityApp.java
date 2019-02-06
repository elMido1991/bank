package com.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bank.webServices.*;
import com.bank.pojos.Utilisateur;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityApp extends WebSecurityConfigurerAdapter {
	
	
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	 @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService).passwordEncoder(
	    		new PasswordEncoder() {
					
					@Override
					public boolean matches(CharSequence rawPassword, String encodedPassword) {
						// TODO Auto-generated method stub
						return true;
					}
					
					@Override
					public String encode(CharSequence rawPassword) {
						// TODO Auto-generated method stub
						return rawPassword.toString();
					}
				}
	    		);
	}
	 
	 
	 
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user").password("password").roles("USER");
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("admin").password("password").roles("ADMIN");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/comptes","/operations","/clients").authenticated()
		.anyRequest().permitAll()
		.and().
		formLogin().permitAll();
	}
}  
