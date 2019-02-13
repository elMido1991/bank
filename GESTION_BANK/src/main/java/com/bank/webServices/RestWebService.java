package com.bank.webServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bank.metier.IBanqueMetier;
import com.bank.pojos.Client;

@Controller
@RestController
@RequestMapping("/bank")
public class RestWebService {

	@Autowired
	private IBanqueMetier banqueserv;
	
	
	@RequestMapping(value="/clients",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public  List<Client> getAllClients() {
		return banqueserv.consulterClients();
	}
	
	@RequestMapping(value="/client/{id}",method=RequestMethod.GET)
	public Client getClient(@PathVariable(value="id") String id) {
		return banqueserv.consulterClient(id);
	}
	
	@RequestMapping(value="/client",method=RequestMethod.POST)
	public Client addClient(@RequestBody Client client ) {
		return banqueserv.creerClient(client);
	}
	
	
	@RequestMapping(value="/client/{codeClient}",method=RequestMethod.PUT)
	public Client updateClient(@PathVariable(value="codeClient") String codeClient,@RequestBody Client updatedClient) {
		return banqueserv.updateClient(codeClient, updatedClient);
	}
	
	@RequestMapping(value="/client/{codeClient}",method=RequestMethod.DELETE)
	public void deleteClient(@PathVariable(value="codeClient") String codeClient) {
		banqueserv.deleteClient(codeClient);
	}
	
	@RequestMapping(value="/clients/{nom}/{page}/{size}",method=RequestMethod.GET)
	public Page<Client> getClientWithPagination(@PathVariable(value="nom") String nom,@PathVariable(value="page") int page,@PathVariable(value="size") int size){
		return banqueserv.getClientWithPagination("%"+nom+"%", page, size);
	}
}
