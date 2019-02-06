package com.bank.pojos;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="Role")
@Table(name = "role")
public class Role implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long role_id;
	private String role;
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy = "roles")
	private Set<Utilisateur> users;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String role, Set<Utilisateur> users) {
		super();
		this.role = role;
		this.users = users;
	}

	public long getRole_id() {
		return role_id;
	}

	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Utilisateur> getUsers() {
		return users;
	}

	public void setUsers(Set<Utilisateur> users) {
		this.users = users;
	}
	
	
	
}
