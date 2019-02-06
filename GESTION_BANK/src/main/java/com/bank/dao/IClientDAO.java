package com.bank.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.pojos.Client;

public interface IClientDAO extends JpaRepository<Client, String> {

	@Query("select c From Client c where c.nom like :x or c.prenom like :x")
	public Page<Client> getClientsWithPagination(@Param("x") String nom,Pageable pageRequest);
}
