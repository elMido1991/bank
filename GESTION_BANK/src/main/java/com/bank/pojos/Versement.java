package com.bank.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
@DiscriminatorValue(value="versement")
public class Versement extends Operation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public Versement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Versement(double montant, Date dateOperation) {
		super(montant, dateOperation);
		// TODO Auto-generated constructor stub
	}

}
