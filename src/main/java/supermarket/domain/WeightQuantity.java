package supermarket.domain;

import java.math.BigDecimal;

import static framework.Objects.requireNotNull;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static supermarket.domain.Quantity.Type.WEIGHT;

public class WeightQuantity implements Quantity {

    private BigDecimal weight;
    private Unit unit;

    public WeightQuantity(BigDecimal weight, Unit unit) {
        this.weight = requireNotNull(weight, "weight is null");
        if (weight.compareTo(ZERO) == -1)
            throw new IllegalArgumentException("Negative value not allowed for WeightQuantity");
        this.unit = requireNotNull(unit, "unit is null");
    }

    public WeightQuantity(String weight, Unit unit) {
        this(new BigDecimal(weight), unit);
    }

    @Override
    public Type getType() {
        return WEIGHT;
    }

    public WeightQuantity convertTo(Unit weightUnit) {
        return new WeightQuantity(weight.multiply(unit.convertFactor(weightUnit)), weightUnit);
    }

    @Override
    public String toString() {
        return weight + " " + unit;
    }

    @Override
    public Quantity add(Quantity quantity) {
        if (quantity.getType().equals(WEIGHT))
            return new WeightQuantity(weight.add(((WeightQuantity) quantity).convertTo(unit).weight), unit);
        throw new IllegalStateException("can't add no WeightQuantity");
    }

    public BigDecimal getWeight() {
        return weight;
    }

    @Override
    public Amount multiply(BigDecimal price) {
        return new Amount(weight.multiply(price));
    }

    public enum Unit {
        POUND {
            @Override
            BigDecimal convertFactor(Unit unit) {
                return unit.equals(OUNCE) ? new BigDecimal("16") : ONE;
            }
        },
        OUNCE {
            @Override
            BigDecimal convertFactor(Unit unit) {
                return unit.equals(POUND) ? ONE.divide(POUND.convertFactor(OUNCE)) : ONE;
            }
        };

        abstract BigDecimal convertFactor(Unit unit);
    }
}
