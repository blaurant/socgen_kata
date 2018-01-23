package supermarket.domain;

import static framework.Objects.requireNotNull;

public class CartItem {

    private Good good;
    private Quantity quantity;

    public CartItem(Good good, Quantity quantity) {
        this.good = requireNotNull(good, "Null good not allowed");
        this.quantity = requireNotNull(quantity, "Null quantity not allowed");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return good.equals(cartItem.good);
    }

    @Override
    public int hashCode() {
        return good.hashCode();
    }

    public Good getGood() {
        return good;
    }

    public CartItem add(Quantity quantity) {
        return new CartItem(good, this.quantity.add(quantity));
    }

    public Amount getAmount() {
        return good.getPrice().multiply(quantity).round();
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "good=" + good +
                ", quantity=" + quantity +
                '}';
    }
}
