package supermarket.domain;

import framework.Integers;
import framework.SimpleValueObject;

import java.math.BigDecimal;

import static supermarket.domain.Quantity.Type.UNIT;

public class UnitQuantity extends SimpleValueObject<Integer> implements Quantity {

    public UnitQuantity(Integer value) {
        super(value);
        Integers.requirePositifOrZero(value, "Negative quantity is not allowed");
    }

    @Override
    public Type getType() {
        return UNIT;
    }

    @Override
    public Quantity add(Quantity quantity) {
        if (quantity.getType().equals(UNIT))
            return new UnitQuantity(value + ((UnitQuantity) quantity).value);
        throw new IllegalStateException("can't add no UnitQuantity");
    }

    @Override
    public Amount multiply(BigDecimal price) {
        return new Amount(new BigDecimal(super.value).multiply(price));
    }

    public UnitQuantity divideBy(Integer i) {
        return new UnitQuantity(value / i);
    }

    public UnitQuantity modulo(Integer i) {
        return new UnitQuantity(value % i);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
