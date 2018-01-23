package supermarket.domain;

public interface Price {

    Amount multiply(Quantity quantity);

    Type getType();

    enum Type {
        BY_UNIT, BY_WEIGHT
    }
}
