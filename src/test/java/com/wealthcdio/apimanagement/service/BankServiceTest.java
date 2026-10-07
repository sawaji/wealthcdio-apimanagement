package com.wealthcdio.apimanagement.service;

import com.wealthcdio.apimanagement.domain.Account;
import com.wealthcdio.apimanagement.repository.AccountRepository;
import com.wealthcdio.apimanagement.repository.LedgerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankServiceTest {

    private AccountRepository accountRepository;
    private LedgerRepository ledgerRepository;
    private BankService bankingService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        ledgerRepository = mock(LedgerRepository.class);
        bankingService = new BankService(accountRepository, ledgerRepository);
    }

    @Test
    @DisplayName("Should process valid deposit and save ledger record")
    void testDeposit_Success() {
        Account account = new Account("ACC01", new BigDecimal("100.00"));
        when(accountRepository.findById("ACC01")).thenReturn(Optional.of(account));

        bankingService.deposit("ACC01", new BigDecimal("50.00"));

        assertEquals(new BigDecimal("150.00"), account.getBalance());
        verify(accountRepository, times(1)).save(account);
        verify(ledgerRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw exception when withdrawal amount causes an overdraft")
    void testWithdraw_InsufficientFunds() {
        Account account = new Account("ACC01", new BigDecimal("30.00"));
        when(accountRepository.findById("ACC01")).thenReturn(Optional.of(account));

        assertThrows(IllegalStateException.class, () ->
                bankingService.withdraw("ACC01", new BigDecimal("50.00"))
        );
        verify(accountRepository, never()).save(any());
    }
}
