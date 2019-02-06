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
@DiscriminatorValue(value="epargne")
public class CompteEpargne extends Compte implements Serializable {

	/**
	 * 
	 */
	@Column(name="Taux", nullable=true, length=10)	
	private double taux;

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}

	public CompteEpargne(String codeCompte, double solde, Date dateCreation, double taux) {
		super(codeCompte, solde, dateCreation);
		this.taux = taux;
	}

	public CompteEpargne() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
