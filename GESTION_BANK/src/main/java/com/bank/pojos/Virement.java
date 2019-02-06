package com.bank.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value="virement")
public class Virement extends Operation implements Serializable {

	@OneToOne(targetEntity=Compte.class, fetch=FetchType.LAZY)
	@JoinColumn(name="CodeCompteVirement", referencedColumnName="CodeCompte", nullable=true,columnDefinition="varchar(24)")
	private Compte compteVirement;
	
	public Virement(double montant, Date dateOperation,Compte compteVirement) {
		super(montant, dateOperation);
		this.compteVirement=compteVirement;
		// TODO Auto-generated constructor stub
	}

	public Virement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Compte getCompteVirement() {
		return compteVirement;
	}

	public void setCompteVirement(Compte compteVirement) {
		this.compteVirement = compteVirement;
	}

	/**
	 * 
	 */
	
	
	

}
