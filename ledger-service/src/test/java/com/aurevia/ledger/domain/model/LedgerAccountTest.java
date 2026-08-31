package com.aurevia.ledger.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LedgerAccountTest {

    @Test
    void shouldCreateOpenCustomerDepositAccount(){

        LedgerAccount account =
                LedgerAccount.open(
                        AccountType.LIABILITY,
                        LedgerAccountCategory.CUSTOMER_DEPOSIT,
                        Currency.getInstance("EUR")
                );

        assertThat(account.status())
                .isEqualTo(LedgerAccountStatus.OPEN);

        assertThat(account.type())
                .isEqualTo(AccountType.LIABILITY);

        assertThat(account.category())
                .isEqualTo(LedgerAccountCategory.CUSTOMER_DEPOSIT);

        assertThat(account.currency())
                .isEqualTo(Currency.getInstance("EUR"));
        }

    @Test
    void shouldRejectInvalidTypeForCategory(){

        assertThatThrownBy(
                () -> LedgerAccount.open(
                        AccountType.ASSET,
                        LedgerAccountCategory.CUSTOMER_DEPOSIT,
                        Currency.getInstance("EUR")
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldBlockOpenAccount(){

        LedgerAccount account =
                LedgerAccount.open(
                        AccountType.LIABILITY,
                        LedgerAccountCategory.CUSTOMER_DEPOSIT,
                        Currency.getInstance("EUR")
                );

        account.block();

        assertThat(account.status())
                .isEqualTo(LedgerAccountStatus.BLOCKED);
    }

    @Test
    void shouldNotBlockClosedAccount(){

        LedgerAccount account =
                LedgerAccount.open(
                        AccountType.LIABILITY,
                        LedgerAccountCategory.CUSTOMER_DEPOSIT,
                        Currency.getInstance("EUR")
                );

        account.close();

        assertThatThrownBy(account::block)
                .isInstanceOf(IllegalStateException.class);
    }

}
