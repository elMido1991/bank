package com.bank.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.pojos.Compte;

public interface ICompteDAO extends JpaRepository<Compte, String> {
	
	@Query("select cp From Compte cp where cp.codeCompte like :x ")
	public Page<Compte> getCompteWithPagination(@Param("x") String mc,Pageable pageRequest);
	
}
