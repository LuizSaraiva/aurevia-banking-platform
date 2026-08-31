package com.aurevia.ledger.domain.model;

import java.util.Objects;
import java.util.UUID;

public record LedgerAccountId(UUID value) {

    public LedgerAccountId {
        Objects.requireNonNull(value, "ledger value must not be null");
    }

    public static LedgerAccountId newId() {
        return new LedgerAccountId(UUID.randomUUID());
    }

    public static LedgerAccountId of(String value){
        return new LedgerAccountId(UUID.fromString(value));
    }
}
