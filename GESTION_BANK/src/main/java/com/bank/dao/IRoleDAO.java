package com.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.pojos.Role;

public interface IRoleDAO extends JpaRepository<Role, Long> {

}
