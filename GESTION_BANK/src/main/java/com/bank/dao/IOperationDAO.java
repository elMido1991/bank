package com.bank.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.pojos.Operation;

public interface IOperationDAO extends JpaRepository<Operation, String> {

	@Query("select o From Operation o where o.compte.codeCompte like :x ")
	public Page<Operation> getOperationWithPagination(@Param("x") String mc,Pageable pageRequest);

}
