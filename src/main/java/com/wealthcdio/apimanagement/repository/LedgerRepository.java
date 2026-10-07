package com.wealthcdio.apimanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wealthcdio.apimanagement.domain.LedgerEntry;

@Repository
public interface LedgerRepository extends JpaRepository<LedgerEntry, Long> {
    List<LedgerEntry> findByAccountId(String accountId);
}