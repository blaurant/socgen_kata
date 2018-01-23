package supermarket.domain;

import framework.SimpleValueObject;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Amount extends SimpleValueObject<BigDecimal> {

    public static Amount ZERO = new Amount("0");

    public Amount(String value) {
        this(new BigDecimal(value));
    }

    public Amount(BigDecimal value) {
        super(value);
        if (value.compareTo(BigDecimal.ZERO) == -1)
            throw new IllegalArgumentException("Negative value not allowed for Amount");
    }

    public Amount add(Amount amount) {
        return new Amount(value.add(amount.value));
    }

    public Amount round() {
        return new Amount(value.setScale(2, HALF_UP));
    }

    public Amount multiply(BigDecimal bigDecimal) {
        return new Amount(value.multiply(bigDecimal));
    }
}