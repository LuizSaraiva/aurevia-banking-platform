package com.aurevia.ledger.domain.exception;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyMismatchException extends RuntimeException{

    public CurrencyMismatchException(
            String sourceCurrency,
            String targetCurrency
    ){
        super(
                "Currency mismatch: %s and %s"
                        .formatted(sourceCurrency, targetCurrency)
        );
    }

}
