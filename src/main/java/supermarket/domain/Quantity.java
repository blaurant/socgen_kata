package supermarket.domain;

import java.math.BigDecimal;

public interface Quantity {

    Type getType();

    Quantity add(Quantity quantity);

    Amount multiply(BigDecimal price);

    enum Type {
        WEIGHT, UNIT
    }
}
