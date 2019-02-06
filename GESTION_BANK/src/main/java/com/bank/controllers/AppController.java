package com.bank.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.metier.IBanqueMetier;
import com.bank.pojos.Client;
import com.bank.pojos.Compte;
import com.bank.pojos.CompteCourant;
import com.bank.pojos.CompteEpargne;
import com.bank.pojos.Operation;
import com.bank.pojos.Versement;


@Controller
public class AppController {

	@Autowired
	private IBanqueMetier iBanqueMetier;
	
	//client managment
	
	
	@RequestMapping(value="/clients",method=RequestMethod.GET)
	public String clients(Model model,
			@RequestParam(name="page",defaultValue="0") int p,
			@RequestParam(name="size",defaultValue="10") int s,
			@RequestParam(name="mc",defaultValue="") String mc){
		Page<Client> clients = iBanqueMetier.getClientWithPagination(mc, p, s);
		model.addAttribute("listClients", clients.getContent());
		
		int[] pages = new int[clients.getTotalPages()]; 
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("mc", mc);
		return "clients";
	}
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.GET)
	public String deleteUser(Model model,
			@RequestParam(name="id") String id,
			@RequestParam(name="page",defaultValue="0") int p,
			@RequestParam(name="mc",defaultValue="") String mc) {
		iBanqueMetier.deleteClient(id);
		return "redirect:/clients?page="+p+"&mc="+mc;
	}
	
	@RequestMapping(value="/saveClient",method=RequestMethod.GET)
	public String clientForm(Model model) {
		model.addAttribute("client", new Client());
		return "addClient";
	}
	
	@RequestMapping(value="/saveClient",method=RequestMethod.POST)
	public String saveClient(Model model,@Valid Client client,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "addClient";
		}
		iBanqueMetier.creerClient(client);
		return "confirmationClient";
	}
	
	@RequestMapping(value="/editClient",method=RequestMethod.GET)
	public String editClient(Model model,@RequestParam(name="id") String id) {
		Client client = iBanqueMetier.consulterClient(id);
		model.addAttribute("client", client);
		return "editClient";
	}
	
	@RequestMapping(value="/editClient",method=RequestMethod.POST)
	public String editClient(Model model,@RequestParam(name="id") String id,@Valid Client client,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "editClient";
		}
		iBanqueMetier.updateClient(id, client);
		return "confirmationClient";
	}


	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    //The date format to parse or output your dates
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    //Create a new CustomDateEditor
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    //Register it as custom editor for the Date type
	    binder.registerCustomEditor(Date.class, editor);
	}
	
		
	//comptes managment
	@RequestMapping(value="/comptes",method=RequestMethod.GET)
	public String comptes(Model model,
			@RequestParam(name="page",defaultValue="0") int p,
			@RequestParam(name="size",defaultValue="10") int s,
			@RequestParam(name="mc",defaultValue="") String mc){
		Page<Compte> comptes = iBanqueMetier.getCompteWithPagination(mc, p, s );
		model.addAttribute("listComptes", comptes.getContent());
		
		int[] pages = new int[comptes.getTotalPages()]; 
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("mc", mc);
		return "comptesClients";
	}
	
	@RequestMapping(value="/addCompte",method=RequestMethod.GET)
	public String addCompte(Model model) {
		
		model.addAttribute("client",new Client());
		model.addAttribute("compte",new Compte());
		List<Client> allClients = iBanqueMetier.consulterClients();
		List<String> typesCompte = new ArrayList<>();
		typesCompte.add("Courant");
		typesCompte.add("Épargne");
		model.addAttribute("listClients",allClients);
		model.addAttribute("dtype",typesCompte);
		model.addAttribute("taux",0);
		model.addAttribute("facilite",0);
		model.addAttribute("error","");
		
		return "addCompte";
	}
	

	
	@RequestMapping(value="/saveCompte",method=RequestMethod.POST)
	public String saveCompte(Model model,
			@RequestParam(name="dtype") String dtype,
			@RequestParam(name="taux") String taux,
			@RequestParam(name="facilite")  String facilite,
			@Valid Compte compte,
			BindingResult bindingResult1,
			@Valid Client client,
			BindingResult bindingResult2) {
			
		try {
			
			
				if(bindingResult1.hasErrors()||bindingResult2.hasErrors()) {
					System.out.println("has errors");
					return "addCompte";
				}
				
				
				Compte newCompte = iBanqueMetier.consulterCompte(compte.getCodeCompte());
				
				if(newCompte!=null) {
					throw new RuntimeException("Ce compte existe déjà");
				}
				
				if(dtype.equals("courant")) {
					double nfacilite = Double.parseDouble(facilite==null?"0":facilite);
					newCompte = new CompteCourant(compte.getCodeCompte(),compte.getSolde(),compte.getDateCreation(),nfacilite);
				}
				else {
					double ntaux = Double.parseDouble(taux==null?"0":taux);
					newCompte = new CompteEpargne(compte.getCodeCompte(),compte.getSolde(),compte.getDateCreation(),ntaux);
				}
				iBanqueMetier.creerClient(client);
				newCompte.setClient(client);
				iBanqueMetier.creerCompteClient(newCompte);
				
				model.addAttribute("compte",newCompte);
				return "confirmationCompte";
				
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "addCompte";
		}
		
	}

	@RequestMapping(value = "/doOperation", method = RequestMethod.GET)
	public String doOperation(Model model, @RequestParam(name = "codeCompte") String codeCompte) {
		try {
			Compte compte = iBanqueMetier.consulterCompte(codeCompte);

			model.addAttribute("compte", compte);

			return "doOperation";

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "doOperation";
		}
	}
	
	@RequestMapping(value="/doOperation",method=RequestMethod.POST)
	public String doOperation(Model model,
			@RequestParam(name="codeCompte") String codeCompte,
			@RequestParam(name="montant") String smontant,
			@RequestParam(name="typeOperation") String typeOperation,
			@RequestParam(name="compteVirement") String compteVirement) {
			
			try {
						Double montant = Double.parseDouble("0"+smontant);
						
						
							
						if(typeOperation.equals("Versement")) {
							iBanqueMetier.verser(codeCompte, montant);
						}
						else if(typeOperation.equals("Retrait")){
							iBanqueMetier.retirer(codeCompte, montant);
						}
						else {
							
							Compte c = iBanqueMetier.consulterCompte(compteVirement);
							if(c==null)
								throw new RuntimeException("Compte inexistant");
							else
								iBanqueMetier.virer(codeCompte, compteVirement, montant);
						}
						
						Compte compte = iBanqueMetier.consulterCompte(codeCompte);
						model.addAttribute("listOperations",compte.getOperations());
						model.addAttribute("mc",codeCompte);
						return "redirect:/operations?mc="+codeCompte;
			
			}catch(Exception e) {
				model.addAttribute("error", e.getMessage());
				return "redirect:/doOperation?mc="+codeCompte+"&codeCompte="+codeCompte+"&error="+e.getMessage();
			}
			
		
		
	}
	
	@RequestMapping(value="/operations",method=RequestMethod.GET)
	public String operations(Model model,
			@RequestParam(name="page",defaultValue="0") int p,
			@RequestParam(name="size",defaultValue="10") int s,
			@RequestParam(name="mc",defaultValue="") String mc){
		Page<Operation> operations = iBanqueMetier.getOperationWithPagination(mc, p, s);
		model.addAttribute("listOperations", operations.getContent());
		
		int[] pages = new int[operations.getTotalPages()]; 
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("mc", mc);
		return "operations";
	}
	
}
