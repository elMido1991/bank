package com.bank.metier;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bank.dao.IClientDAO;
import com.bank.dao.ICompteDAO;
import com.bank.dao.IOperationDAO;
import com.bank.pojos.Client;
import com.bank.pojos.Compte;
import com.bank.pojos.CompteCourant;
import com.bank.pojos.CompteEpargne;
import com.bank.pojos.Operation;
import com.bank.pojos.Retrait;
import com.bank.pojos.Versement;
import com.bank.pojos.Virement;

@Service
public class BanqueService implements IBanqueMetier {

	@Autowired
	private IClientDAO clientdao;
	@Autowired
	private ICompteDAO comptedao;
	@Autowired
	private IOperationDAO operationdao;
	
	@Override
	@Transactional
	public Client creerClient(Client client) {
		return clientdao.save(client);

	}

	@Override
	@Transactional
	public void creerCompteClient(Compte compte) {
		// TODO Auto-generated method stub
		comptedao.save(compte);
	}

	@Override
	@Transactional
	public Compte consulterCompte(String codeCompte) {
		// TODO Auto-generated method stub
			return comptedao.findById(codeCompte).isPresent() ? comptedao.findById(codeCompte).get() : null;
		
	}

	@Override
	@Transactional
	public List<Compte> consulterComptes() {
		// TODO Auto-generated method stub
		return comptedao.findAll();
	}

	@Override
	@Transactional
	public void verser(String codeCompte, double montant) {
		// TODO Auto-generated method stub
		Compte compte = consulterCompte(codeCompte);
		Versement versement = new Versement(montant, new Date());
		versement.setCompte(compte);
		operationdao.save(versement);
		compte.setSolde(montant+compte.getSolde());
		comptedao.save(compte);
	}

	@Override
	@Transactional
	public void retirer(String codeCompte, double montant) {
		// TODO Auto-generated method stub
		Compte compte = consulterCompte(codeCompte);
		double facilite = 0;
		if(compte instanceof CompteEpargne) {
			facilite = 0;
		}
		else {
			facilite = ((CompteCourant)compte).getFacilite();
		}
		if(montant<=compte.getSolde()+facilite) {
			Retrait retrait = new Retrait(montant, new Date());
			retrait.setCompte(compte);
			operationdao.save(retrait);
			compte.setSolde(compte.getSolde()-montant);
			comptedao.save(compte);
		}
		else {
			throw new RuntimeException("Solde insuffisant");
		}
	}


	@Transactional
	public Compte retirerWithoutSaveOperation(String codeCompte, double montant) {
		// TODO Auto-generated method stub
		Compte compte = consulterCompte(codeCompte);
		double facilite = 0;
		if(compte instanceof CompteEpargne) {
			facilite = 0;
		}
		else {
			facilite = ((CompteCourant)compte).getFacilite();
		}
		if(montant<=compte.getSolde()+facilite) {
			Retrait retrait = new Retrait(montant, new Date());
			retrait.setCompte(compte);
			//operationdao.save(retrait);
			compte.setSolde(compte.getSolde()-montant);
			return comptedao.save(compte);
		}
		else {
			throw new RuntimeException("Solde insuffisant");
		}
	}
	
	
	@Transactional
	public Compte verserWithoutSaveOperation(String codeCompte, double montant) {
		// TODO Auto-generated method stub
		Compte compte = consulterCompte(codeCompte);
		Versement versement = new Versement(montant, new Date());
		versement.setCompte(compte);
		//operationdao.save(versement);
		compte.setSolde(montant+compte.getSolde());
		return comptedao.save(compte);
	}

	@Override
	@Transactional
	public void virer(String codeCompte1, String codeCompte2, double montant) {
		// TODO Auto-generated method stub
		
		Compte compteQuiVire = retirerWithoutSaveOperation(codeCompte1, montant);
		Compte compteVirement = verserWithoutSaveOperation(codeCompte2, montant);
		Virement virement = new Virement(montant, new Date(),compteVirement);
		virement.setCompte(compteQuiVire);
		operationdao.save(virement);
	}

	@Override
	@Transactional
	public List<Client> consulterClients() {
		// TODO Auto-generated method stub
		return clientdao.findAll();
	}

	@Override
	public Client consulterClient(String codeClient){
		// TODO Auto-generated method stub
		return clientdao.findById(codeClient).get() ;
	}

	@Override
	public Client updateClient(String codeClient, Client updatedClient) {
		// TODO Auto-generated method stub
		updatedClient.setId(codeClient);
		return clientdao.save(updatedClient);
	}

	@Override
	public void deleteClient(String codeClient) {
		// TODO Auto-generated method stub
		clientdao.deleteById(codeClient);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Page<Client> getClientWithPagination(String mc, int page, int size) {
		// TODO Auto-generated method stub
		return clientdao.getClientsWithPagination("%"+mc+"%", new PageRequest(page, size));
	}

	@Override
	public Compte updateCompte(String codeCompte, Compte updatedCompte) {
		// TODO Auto-generated method stub
		updatedCompte.setCodeCompte(codeCompte);
		comptedao.save(updatedCompte);
		return comptedao.save(updatedCompte);
	}

	@Override
	public void deleteCompte(String codeCompte) {
		// TODO Auto-generated method stub
		comptedao.deleteById(codeCompte);
	}

	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public Page<Compte> getCompteWithPagination(String mc, int page, int size) {
		// TODO Auto-generated method stub
		return comptedao.getCompteWithPagination("%"+mc+"%", new PageRequest(page, size));//findAll(new PageRequest(page, size));
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public Page<Operation> getOperationWithPagination(String mc, int page, int size) {
		// TODO Auto-generated method stub
		return operationdao.getOperationWithPagination("%"+mc+"%", new PageRequest(page, size));//findAll(new PageRequest(page, size));
	}

	

}
