package com.wealthcdio.apimanagement.controller;

import com.wealthcdio.apimanagement.domain.Account;
import com.wealthcdio.apimanagement.domain.LedgerEntry;
import com.wealthcdio.apimanagement.dto.TransactionRequest;
import com.wealthcdio.apimanagement.dto.TransferRequest;
import com.wealthcdio.apimanagement.service.BankService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankController {

    private final BankService bankingService;

    public BankController(BankService bankingService) {
        this.bankingService = bankingService;
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Account> getBalance(@PathVariable String accountId) {
        return ResponseEntity.ok(bankingService.getAccount(accountId));
    }

    @GetMapping("/{accountId}/history")
    public ResponseEntity<List<LedgerEntry>> getHistory(@PathVariable String accountId) {
        return ResponseEntity.ok(bankingService.getTransactionHistory(accountId));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountId, @Valid @RequestBody TransactionRequest request) {
        bankingService.deposit(accountId, request.amount());
        return ResponseEntity.ok("Deposit successful Done!!!");
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountId, @Valid @RequestBody TransactionRequest request) {
        bankingService.withdraw(accountId, request.amount());
        return ResponseEntity.ok("Withdrawal successful Done!!!");
    }

    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<String> transfer(@PathVariable String accountId, @Valid @RequestBody TransferRequest request) {
        bankingService.transfer(accountId, request.targetAccountId(), request.amount());
        return ResponseEntity.ok("Transfer successful Done!!!");
    }
}
