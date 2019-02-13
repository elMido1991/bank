package com.bank.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDAO extends JpaRepository<User, String> {

}
