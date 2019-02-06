package com.bank.webServices;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bank.dao.IUtilisateurDAO;
import com.bank.pojos.CustomUserDetails;
import com.bank.pojos.Utilisateur;

public class CutsomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUtilisateurDAO userDAO;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Utilisateur> optionalUser = userDAO.findByUserName(username);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Nom d'utilisateur nom existant"));
		return optionalUser.map(CustomUserDetails::new).get();
	}

}
