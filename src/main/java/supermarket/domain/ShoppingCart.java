package supermarket.domain;

import com.google.common.collect.Sets;
import framework.ASetOf;

import java.util.Optional;
import java.util.Set;

import static supermarket.domain.Amount.ZERO;

public class ShoppingCart {

    private CartItems cartItems;

    public ShoppingCart(Set<CartItem> cartItems) {
        this.cartItems = new CartItems(cartItems);
    }

    public ShoppingCart() {
        this(Sets.newHashSet());
    }

    public Amount total() {
        return cartItems.stream().map(CartItem::getAmount).reduce(Amount::add).orElse(ZERO);
    }

    public ShoppingCart add(Good good, Quantity quantity) {
        Optional<CartItem> cartItem = findBy(good);
        if (cartItem.isPresent()) {
            return new ShoppingCart(cartItems.replace(cartItem.get(), cartItem.get().add(quantity)).toSet());
        } else {
            return new ShoppingCart(cartItems.add(new CartItem(good, quantity)).toSet());
        }
    }

    private Optional<CartItem> findBy(Good good) {
        return cartItems.stream().filter(cartItem -> cartItem.getGood().equals(good)).findAny();
    }

    public static class CartItems extends ASetOf<CartItem> {

        public CartItems(Set<CartItem> set) {
            super(set);
        }

        @Override
        public <Sub extends ASetOf<CartItem>> Sub cons(Set<CartItem> newSet) {
            return (Sub) new CartItems(newSet);
        }

        public CartItems replace(CartItem cartItem1, CartItem cartItem2) {
            return remove(cartItem1).add(cartItem2);
        }
    }
}
