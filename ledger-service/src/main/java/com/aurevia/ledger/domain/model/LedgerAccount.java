package com.aurevia.ledger.domain.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public final class LedgerAccount {

    private final LedgerAccountId id;
    private final AccountType type;
    private final LedgerAccountCategory  category;
    private final Currency currency;

    private LedgerAccountStatus status;

    public LedgerAccount(
            LedgerAccountId id,
            AccountType type,
            LedgerAccountCategory category,
            Currency currency,
            LedgerAccountStatus status) {

        this.id = Objects.requireNonNull(id,"id must not be null");
        this.type = Objects.requireNonNull(type,"type must not be null");

        if(category.expectedType() != type){
            throw new IllegalArgumentException("Invalid account type %s for category %s"
                    .formatted(type,category));
        }
        this.category = Objects.requireNonNull(category,"category must not be null");

        this.currency = Objects.requireNonNull(currency,"currency must not be null");
        this.status = Objects.requireNonNull(status,"status must not be null");
    }

    public static LedgerAccount open(
            AccountType type,
            LedgerAccountCategory category,
            Currency currency
    ){
        return new LedgerAccount(
                LedgerAccountId.newId(),
                type,
                category,
                currency,
                LedgerAccountStatus.OPEN);
    }

    public void block(){
        if(status == LedgerAccountStatus.CLOSED){
            throw new IllegalStateException("Closed ledger account cannot be blocked");
        }
        status = LedgerAccountStatus.BLOCKED;
    }

    public void close(){
        if(status == LedgerAccountStatus.CLOSED){
            return;
        }

        status = LedgerAccountStatus.CLOSED;
    }

    public LedgerAccountId id(){
        return this.id;
    }

    public AccountType type(){
        return this.type;
    }

    public LedgerAccountCategory category(){
        return this.category;
    }

    public Currency currency(){
        return this.currency;
    }

    public LedgerAccountStatus status(){
        return this.status;
    }
}
