package com.aurevia.ledger.domain.model;

public enum LedgerAccountCategory {
    CUSTOMER_DEPOSIT(AccountType.LIABILITY),
    SETTLEMENT_ASSET(AccountType.ASSET),
    FEE_REVENUE(AccountType.REVENUE),
    SUSPENSE(AccountType.EXPENSE),
    INTERNAL_CLEARING(AccountType.ASSET);

    private final AccountType expectedType;

    LedgerAccountCategory(AccountType expectedType) {
        this.expectedType = expectedType;
    }

    public AccountType expectedType() {
        return expectedType;
    }
}
