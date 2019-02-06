package com.bank.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.pojos.Utilisateur;

public interface IUtilisateurDAO extends JpaRepository<Utilisateur, Long> {

	@Query("select u from Utilisateur u where u.username=:x")
	public Optional<Utilisateur> findByUserName(@Param("x") String username);

	

}
