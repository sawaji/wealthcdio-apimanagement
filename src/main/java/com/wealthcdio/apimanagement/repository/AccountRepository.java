package com.wealthcdio.apimanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wealthcdio.apimanagement.domain.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}