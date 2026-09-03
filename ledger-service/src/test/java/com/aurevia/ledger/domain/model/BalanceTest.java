package com.aurevia.ledger.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BalanceTest {

    @Test
    void shouldCreateZeroBalance(){

        LedgerAccountId accountId = LedgerAccountId.newId();
        Balance balance = Balance.zero(accountId, "EUR");

        assertThat(balance.booked())
                .isEqualTo(Money.of("0.00", "EUR"));

        assertThat(balance.reserved())
                .isEqualTo(Money.of("0.00", "EUR"));

        assertThat(balance.blocked())
                .isEqualTo(Money.of("0.00", "EUR"));

        assertThat(balance.available())
                .isEqualTo(Money.of("0.00", "EUR"));
    }

    @Test
    void shouldCalculateBalance(){

        Balance balance = new Balance(
                LedgerAccountId.newId(),
                Money.of("1000.00","EUR"),
                Money.of("100.00","EUR"),
                Money.of("200.00","EUR")
        );

        assertThat(balance.available())
                .isEqualTo(Money.of("700.00", "EUR"));
    }

    @Test
    void shouldRejectDifferentCurrencies(){

        assertThatThrownBy(
                () -> new Balance(
                        LedgerAccountId.newId(),
                        Money.of("1000.00","EUR"),
                        Money.of("100.00","GBP"),
                        Money.of("0.00","EUR")
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
