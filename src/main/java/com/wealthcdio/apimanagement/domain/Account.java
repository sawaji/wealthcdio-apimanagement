package com.wealthcdio.apimanagement.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity 
@Table(name = "accounts")
public class Account {

    @jakarta.persistence.Id 
    private String id;

    private BigDecimal balance;

    @Version 
    private Long version;

    public Account() {
    }

    public Account(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void credit(BigDecimal amount)
    {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than or equal to one");
        }
        this.balance = this.balance.add(amount);
    }

    public void debit(BigDecimal amount)
    {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be greater than or equal to one");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Funds Not available");
        }
        this.balance = this.balance.subtract(amount);
    }

    


}
