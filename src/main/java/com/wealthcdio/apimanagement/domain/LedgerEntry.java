package com.wealthcdio.apimanagement.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_entries")
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String description;

    public LedgerEntry() {}

    public LedgerEntry(String accountId, TransactionType type, BigDecimal amount, String description) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    public Long getId() { return id; }
    public String getAccountId() { return accountId; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getDescription() { return description; }
}