package com.bank.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name="Utilisateur")
public class User implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String username;
	private String password;
	private boolean actived;
	@ManyToMany(cascade=CascadeType.ALL)  
	@JoinTable(name="Utilisateur_Role")
	private Set<Role> roles=new HashSet<>();
	
	public boolean isActived() {
		return actived;
	}


	public void setActived(boolean actived) {
		this.actived = actived;
	}


	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public User(String username, String password, Set<Role> roles, boolean actived) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.actived = actived;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	
	
}
