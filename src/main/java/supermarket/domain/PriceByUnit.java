package supermarket.domain;

import framework.SimpleValueObject;

import java.math.BigDecimal;

import static supermarket.domain.Price.Type.BY_UNIT;

public class PriceByUnit extends SimpleValueObject<BigDecimal> implements Price {

    public PriceByUnit(BigDecimal value) {
        super(value);
        if (value.compareTo(BigDecimal.ZERO) == -1)
            throw new IllegalArgumentException("Negative value not allowed for Price");
    }

    public PriceByUnit(String value) {
        this(new BigDecimal(value));
    }

    @Override
    public Amount multiply(Quantity quantity) {
        return quantity.multiply(value);
    }

    @Override
    public Type getType() {
        return BY_UNIT;
    }

    @Override
    public String toString() {
        return "$" + value + "/unit";
    }
}
