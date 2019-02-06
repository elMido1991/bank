package com.bank.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("courant")
public class CompteCourant extends Compte implements Serializable {

	/**
	 * 
	 */
	@Column(name="Facilite", nullable=true, length=10)
	private double facilite;
	
	public CompteCourant(String codeCompte, double solde, Date dateCreation, double facilite) {
		super(codeCompte, solde, dateCreation);
		this.facilite = facilite;
	}

	
	


	public CompteCourant() {
		super();
		// TODO Auto-generated constructor stub
	}





	public double getFacilite() {
		return facilite;
	}

	public void setFacilite(double facilite) {
		this.facilite = facilite;
	}
	
	
}
