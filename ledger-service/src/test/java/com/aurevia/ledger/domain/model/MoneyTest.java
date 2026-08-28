package com.aurevia.ledger.domain.model;

import com.aurevia.ledger.domain.exception.CurrencyMismatchException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {

    @Test
    void shouldCreateMoney(){
        Money money = Money.of("100.00", "EUR");

        assertThat(money.amount())
                .isEqualByComparingTo("100.00");

        assertThat(money.currency().getCurrencyCode())
                .isEqualTo("EUR");
    }

    @Test
    void shouldAddMoneyWithSameCurrency(){

        Money first = Money.of("100.00", "EUR");
        Money second = Money.of("50.00", "EUR");

        Money result = first.add(second);

        assertThat(result)
                .isEqualTo(Money.of("150.00", "EUR"));

    }

    @Test
    void shouldRejectAdditionWithDiferentCurrency(){

        Money euros = Money.of("100.00", "EUR");
        Money pounds = Money.of("50.00", "GBP");

        assertThatThrownBy(
                () -> euros.add(pounds)
        )
                .isInstanceOf(CurrencyMismatchException.class)
                .hasMessageContaining("EUR")
                .hasMessageContaining("GBP");
    }


    @Test
    void shouldSubtractMoneyWithSameCurrency(){

        Money first = Money.of("100.00", "EUR");
        Money second = Money.of("30.00", "EUR");

        Money result = first.subtract(second);

        assertThat(result)
                .isEqualTo(Money.of("70.00", "EUR"));
    }

    @Test
    void shouldCompareMoneyWithSameCurrency(){
        Money balance = Money.of("100.00", "EUR");
        Money payment = Money.of("80.00", "EUR");

        assertThat(balance.isGreaterThanOrEqualTo(payment)).isTrue();
    }

    @Test
    void shouldNormalizeCurrencyScale(){
        Money money = Money.of("100", "EUR");

        assertThat(money.amount())
                .isEqualTo("100.00");
    }

    @Test
    void shouldRejectAmountWithUnsupportedFraction(){

        assertThatThrownBy(
                () -> Money.of("10.123","EUR")
        )
                .isInstanceOf(ArithmeticException.class);
    }

    @Test
    void shouldRespectCurrencyFractionDigits(){
        Money yen = Money.of("100", "JPY");

        assertThat(yen.amount())
                .isEqualTo(new BigDecimal("100"));
    }

}
