package com.wealthcdio.apimanagement.service;

import com.wealthcdio.apimanagement.domain.Account;
import com.wealthcdio.apimanagement.domain.LedgerEntry;
import com.wealthcdio.apimanagement.domain.TransactionType;
import com.wealthcdio.apimanagement.repository.AccountRepository;
import com.wealthcdio.apimanagement.repository.LedgerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BankService {

    private final AccountRepository accountRepository;
    private final LedgerRepository ledgerRepository;

    public BankService(AccountRepository accountRepository, LedgerRepository ledgerRepository) {
        this.accountRepository = accountRepository;
        this.ledgerRepository = ledgerRepository;
    }

    @Transactional(readOnly = true)
    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account not found: " + accountId));
    }

    @Transactional(readOnly = true)
    public List<LedgerEntry> getTransactionHistory(String accountId) {
        getAccount(accountId);
        return ledgerRepository.findByAccountId(accountId);
    }

    @Transactional
    public void deposit(String accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.credit(amount);
        accountRepository.save(account);
        ledgerRepository.save(new LedgerEntry(accountId, TransactionType.DEPOSIT, amount, "Deposit settled."));
    }

    @Transactional
    public void withdraw(String accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.debit(amount);
        accountRepository.save(account);
        ledgerRepository.save(new LedgerEntry(accountId, TransactionType.WITHDRAWAL, amount, "Withdrawal processed."));
    }

    @Transactional
    public void transfer(String sourceAccountId, String targetAccountId, BigDecimal amount) {
        if (sourceAccountId.equals(targetAccountId)) {
            throw new IllegalArgumentException("Cannot transfer money to the same account.");
        }
        Account sourceAccount = getAccount(sourceAccountId);
        Account targetAccount = getAccount(targetAccountId);

        sourceAccount.debit(amount);
        targetAccount.credit(amount);

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        ledgerRepository.save(new LedgerEntry(sourceAccountId, TransactionType.TRANSFER_OUT, amount, "Transfer out to " + targetAccountId));
        ledgerRepository.save(new LedgerEntry(targetAccountId, TransactionType.TRANSFER_IN, amount, "Transfer inbound from " + sourceAccountId));
    }
}
