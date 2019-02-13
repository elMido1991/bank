package com.bank.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value="retrait")
public class Retrait extends Operation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Retrait(double montant, Date dateOperation) {
		super(montant, dateOperation);
		// TODO Auto-generated constructor stub
	}

	public Retrait() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */

}
