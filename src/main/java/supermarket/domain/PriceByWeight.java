package supermarket.domain;

import static framework.Objects.requireNotNull;
import static supermarket.domain.Price.Type.BY_WEIGHT;
import static supermarket.domain.Quantity.Type.WEIGHT;

public class PriceByWeight implements Price {

    private Amount amount;
    private WeightQuantity.Unit weightUnit;

    public PriceByWeight(Amount amount, WeightQuantity.Unit weightUnit) {
        this.amount = requireNotNull(amount, "null amount not allowed");
        this.weightUnit = requireNotNull(weightUnit, "null weightUnit not allowed");
    }

    public PriceByWeight(String amount, WeightQuantity.Unit weightUnit) {
        this(new Amount(amount), weightUnit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceByWeight that = (PriceByWeight) o;

        if (!amount.equals(that.amount)) return false;
        return weightUnit == that.weightUnit;

    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + weightUnit.hashCode();
        return result;
    }

    @Override
    public Amount multiply(Quantity quantity) {
        if (quantity.getType().equals(WEIGHT))
            return ((WeightQuantity) quantity).convertTo(this.weightUnit).multiply(amount.value);
        throw new IllegalStateException("PriceByWeigh can multiply only WightQuantity");
    }

    @Override
    public Type getType() {
        return BY_WEIGHT;
    }

    @Override
    public String toString() {
        return "$" + amount + "/" + weightUnit;
    }
}
