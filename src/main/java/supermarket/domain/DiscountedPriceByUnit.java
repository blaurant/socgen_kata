package supermarket.domain;

import framework.Objects;

import java.math.BigDecimal;

import static supermarket.domain.Quantity.Type.UNIT;

public class DiscountedPriceByUnit extends PriceByUnit {

    private Discount discount;

    public DiscountedPriceByUnit(BigDecimal value, Discount discount) {
        super(value);
        this.discount = Objects.requireNotNull(discount, "discount is null");
    }

    public DiscountedPriceByUnit(PriceByUnit price, Discount discount) {
        this(price.value, discount);
    }

    @Override
    public Amount multiply(Quantity quantity) {
        if (quantity.getType().equals(UNIT))
            return discount.price(value, (UnitQuantity) quantity);
        throw new IllegalStateException("only UnitQuantity is allowed");
    }
}
