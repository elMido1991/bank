package com.bank.pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Compte")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="typeCompte", discriminatorType=DiscriminatorType.STRING)
public class Compte implements Serializable {

	/**
	 * 
	 */

	@Id
	@Column(name="CodeCompte", nullable=false, length=24)
	@NotBlank( message="Le champ ne peut pas être vide")
	@Pattern(regexp="^[_ 0-9]+$", message="Le champs est strictement numérique")
	private String codeCompte;
	
	
	@Column(name="Solde", nullable=false, length=10)
	@DecimalMin(value="0")
	private double solde;
	
	
	
	@Column(name="DateCreation", nullable=true)	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="Le champ ne peut pas être vide")
	private Date dateCreation;
	
	@JsonBackReference
	@ManyToOne(targetEntity=Client.class, fetch=FetchType.LAZY)
	@JoinColumn(name="CodeClient", referencedColumnName="CodeClient", nullable=false,columnDefinition="varchar(255)")	
	private Client client;
	
	@JsonManagedReference
	@OneToMany(mappedBy="compte",fetch=FetchType.LAZY, targetEntity=Operation.class,cascade = CascadeType.ALL)	
	private Set<Operation> operations;
	
	public Compte(String codeCompte, double solde, Date dateCreation) {
		super();
		this.codeCompte = codeCompte;
		this.solde = solde;
		this.dateCreation = dateCreation;
	}
	public Compte() {
		super();
		this.dateCreation = new Date();
		// TODO Auto-generated constructor stub
	}
	public String getCodeCompte() {
		return codeCompte;
	}
	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Set<Operation> getOperations() {
		return operations;
	}
	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}
	
	
	
}
