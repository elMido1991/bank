package com.bank.metier;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;

import com.bank.pojos.Client;
import com.bank.pojos.Compte;
import com.bank.pojos.Operation;

public interface IBanqueMetier {

	
	//client managment
	public Client creerClient(Client client);
	public List<Client> consulterClients();
	public Client consulterClient(String codeClient);
	public Client updateClient(String codeClient, Client updatedClient);
	public void deleteClient(String codeClient);
	public Page<Client> getClientWithPagination(String mc, int page,int size);
	
	//comptes managment
	public void creerCompteClient(Compte compte);
	public List<Compte> consulterComptes();
	public Compte consulterCompte(String codeCompte) throws NoSuchElementException;
	public Compte updateCompte(String codeCompte, Compte updatedCompte);
	public void deleteCompte(String codeCompte);
	public Page<Compte> getCompteWithPagination(String mc, int page,int size);
	
	
	//operations managment
	public void verser(String codeCompte, double montant);
	public void retirer(String codeCompte, double montant);
	public void virer(String codeCompte1, String codeCompte2, double montant);
	
	public Page<Operation> getOperationWithPagination(String mc, int page,int size);
}
