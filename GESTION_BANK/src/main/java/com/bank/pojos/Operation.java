package com.bank.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Operation")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name="typeOperation", discriminatorType=DiscriminatorType.STRING)
public class Operation implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(generator="system-uuid") 
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="CodeOperation", nullable=false,length=255)	
	private String codeOperation;
	
	@Column(name="Montant", nullable=false, length=10)
	@DecimalMin(value="100",message="le montant ne peut pas être inférieur à 100 Dhs")
	private double montant=0;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="DateOperation", nullable=true)
	private Date dateOperation;
	
	@ManyToOne(targetEntity=Compte.class, fetch=FetchType.LAZY)	
	@JoinColumn(name="CodeCompte", referencedColumnName="CodeCompte", nullable=false,columnDefinition="varchar(24)")
	@JsonBackReference
	private Compte compte;
	
	
	public Compte getCompte() {
		return compte;
	}
	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	public String getCodeOperation() {
		return codeOperation;
	}
	public void setCodeOperation(String codeOperation) {
		this.codeOperation = codeOperation;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Date getDateOperation() {
		return dateOperation;
	}
	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}
	public Operation(double montant, Date dateOperation) {
		super();
		this.montant = montant;
		this.dateOperation = dateOperation;
	}
	public Operation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
