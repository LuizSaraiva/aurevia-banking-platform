package com.aurevia.ledger.domain.model;

import java.util.Objects;

public final class Balance {

    private final LedgerAccountId ledgerAccountId;

    private Money booked;
    private Money reserved;
    private Money blocked;

    public Balance(
            LedgerAccountId ledgerAccountId,
            Money booked,
            Money reserved,
            Money blocked
    ){
        this.ledgerAccountId = Objects.requireNonNull(ledgerAccountId, "ledgerAccountId must not be null");
        this.booked = Objects.requireNonNull(booked, "booked must not be null");
        this.reserved = Objects.requireNonNull(reserved, "reserved must not be null");
        this.blocked = Objects.requireNonNull(blocked, "blocked must not be null");

        ensureSameCurrency();
        ensureNonNegativeReservedAndBlocked();
    }

    public LedgerAccountId ledgerAccountId() {
        return this.ledgerAccountId;
    }

    public Money booked() {
        return this.booked;
    }

    public Money reserved() {
        return this.reserved;
    }

    public Money blocked() {
        return this.blocked;
    }

    public Money available() {
        return booked
                .subtract(reserved)
                .subtract(blocked);
    }

    public void ensureSameCurrency(){

        String bookedCurrency =
                booked.currency().getCurrencyCode();

        String reservedCurrency =
                reserved.currency().getCurrencyCode();

        String blockedCurrency =
                blocked.currency().getCurrencyCode();


        if(!bookedCurrency.equals(reservedCurrency)
            || !blockedCurrency.equals(bookedCurrency)){

            throw new IllegalArgumentException("Balance components must use the same currency");
        }
    }

    public void ensureNonNegativeReservedAndBlocked(){

        if(reserved.amount().signum() < 0){
            throw new IllegalArgumentException("reserved balance must not be negative");
        }

        if(blocked.amount().signum() < 0){
            throw new IllegalArgumentException("blocked balance must not be negative");
        }
    }


    public static Balance zero(
            LedgerAccountId ledgerAccountId,
            String currencyCode
    ){
        Money zero = Money.zero(currencyCode);

        return new Balance(
                ledgerAccountId,
                zero,
                zero,
                zero
        );
    }
}
