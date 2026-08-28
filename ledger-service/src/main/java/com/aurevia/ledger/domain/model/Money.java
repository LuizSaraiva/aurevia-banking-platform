package com.aurevia.ledger.domain.model;

import com.aurevia.ledger.domain.exception.CurrencyMismatchException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record Money (
    BigDecimal amount,
    Currency currency
){

    public Money {
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(currency,"currency must not be null");

        int fractionDigits = currency.getDefaultFractionDigits();

        if(fractionDigits < 0){
            throw new IllegalArgumentException(
                    "Unsupported currency fraction digits:"
                        + currency.getCurrencyCode()
            );
        }

        amount = amount.setScale(fractionDigits, RoundingMode.UNNECESSARY);
    }

    public static Money of(
            String amount,
            String currencyCode
    ){
        return new Money(
                new BigDecimal(amount),
                Currency.getInstance(currencyCode)
        );
    }

    public static Money zero(String currencyCode){

        Currency currency =
                Currency.getInstance(currencyCode);

        return new Money(BigDecimal.ZERO, currency);
    }

    public Money add(Money other) {

        Objects.requireNonNull(other, "other must not be null");

        ensureSameCurrency(other);

        return new Money(
                amount.add(other.amount),
                currency
        );
    }

    private void ensureSameCurrency(Money other) {

        if(!currency.equals(other.currency)){
            throw new CurrencyMismatchException(
                    currency.getCurrencyCode(),
                    other.currency.getCurrencyCode()
            );
        }
    }

    public Money subtract(Money other) {
        Objects.requireNonNull(other, "other must not be null");

        ensureSameCurrency(other);

        return new Money(
                amount.subtract(other.amount),
                currency);
    }

    public boolean isGreaterThanOrEqualTo(Money other) {
        ensureSameCurrency(other);

        return amount.compareTo(other.amount) >= 0;
    }

}
