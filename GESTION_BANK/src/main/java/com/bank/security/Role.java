package com.bank.security;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Role")
public class Role implements Serializable{
	
	
	@Id
	private String libelle;
	private String description;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(String libelle, String description) {
		super();
		this.libelle = libelle;
		this.description = description;
	}
	
	

}
