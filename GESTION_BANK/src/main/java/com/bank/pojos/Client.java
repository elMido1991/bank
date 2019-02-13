package com.bank.pojos;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Client")

public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Column(name="CodeClient", nullable=false,length=255)	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="Nom", nullable=true, length=255)
	@NotBlank( message="Le champ ne peut pas être vide")
	@Pattern(regexp="^[_ A-Za-z]+$", message="Le champs est strictement alphabétique")
	private String nom;
	
	
	
	@Column(name="Prenom", nullable=true, length=255)	
	@NotBlank( message="Le champ ne peut pas être vide")
	@Pattern(regexp="^[_ A-Za-z]+$", message="Le champs est strictement alphabétique")
	private String prenom;
	
	@JsonManagedReference
	@OneToMany(mappedBy="client", targetEntity=Compte.class,fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Compte> comptes;
	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Set<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(Set<Compte> comptes) {
		if(this.comptes!=null) {
			this.comptes.clear();
			this.comptes.addAll(comptes);
		}
		
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNom()+" "+getPrenom();
	}
	
}
