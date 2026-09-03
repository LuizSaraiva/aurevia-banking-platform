CREATE TABLE ledger_account
(
    id UUID NOT NULL,
    account_type VARCHAR(32) NOT NULL,
    category VARCHAR(64) NOT NULL,
    currency CHAR(3) NOT NULL,
    status VARCHAR(32) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_ledger_account
        PRIMARY KEY (id),

    CONSTRAINT chk_ledger_account_type
    CHECK (
        account_type IN (
            'ASSET',
            'LIABILITY',
            'EQUITY',
            'REVENUE'
            'EXPENSE'
            )
        ),

    CONSTRAINT chk_ledger_account_category
    CHECK (
        category in (
            'CUSTOMER_DEPOSIT',
            'SETTLEMENT_ASSET',
            'FEE_REVENUE',
            'SUSPENSE',
            'INTERNAL_CLEARING'
            )
        ),

    CONSTRAINT chk_ledger_account_status
    CHECK (
        status in (
            'OPEN',
            'BLOCKED',
            'CLOSED'
            )
        )
);

CREATE TABLE account_balance
(
    ledger_account_id UUID NOT NULL,

    booked_balance NUMERIC(19, 4) NOT NULL DEFAULT 0,
    reserved_balance NUMERIC(19, 4) NOT NULL DEFAULT 0,
    blocked_balance NUMERIC(19, 4) NOT NULL DEFAULT 0,

    version BIGINT NOT NULL DEFAULT 0,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_account_balance
        PRIMARY KEY (ledger_account_id),

    CONSTRAINT fk_account_balance_ledger_account
        FOREIGN KEY (ledger_account_id)
            REFERENCES ledger_account (id),

    CONSTRAINT chk_reserved_balance_non_negative
    CHECK (reserved_balance >= 0),

    CONSTRAINT chk_blocked_balance_non_negative
    CHECK (blocked_balance >= 0)
);