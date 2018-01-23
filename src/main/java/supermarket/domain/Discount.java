package supermarket.domain;

import framework.Integers;

import java.math.BigDecimal;

public interface Discount {

    Discount NO_DISCOUNT = new NoDiscount();

    static Discount BY_LOT(Integer lotSize, Price price) {
        return new LotDiscount(lotSize, price);
    }

    static Discount ONE_FOR_FREE(Integer size) {
        return new OneForFreeDiscount(size);
    }

    Amount price(BigDecimal price, UnitQuantity quantity);

    class NoDiscount implements Discount {

        @Override
        public Amount price(BigDecimal price, UnitQuantity quantity) {
            return quantity.multiply(price);
        }

        @Override
        public String toString() {
            return "(no discount)";
        }
    }

    class LotDiscount implements Discount {

        private Integer sizeOfLot;
        private Price lotPrice;

        public LotDiscount(Integer sizeOfLot, Price lotPrice) {
            this.sizeOfLot = Integers.requirePositifOrZero(sizeOfLot, "size of lot must be positif or null");
            this.lotPrice = lotPrice;
        }

        @Override
        public Amount price(BigDecimal price, UnitQuantity quantity) {
            return lotPrice.multiply(quantity.divideBy(sizeOfLot))
                    .add(new Amount(price.multiply(new BigDecimal(quantity.modulo(sizeOfLot).value()))));
        }

        @Override
        public String toString() {
            return "(" + sizeOfLot + " for " + lotPrice + ")";
        }
    }

    class OneForFreeDiscount implements Discount {

        private Integer discountQty;

        public OneForFreeDiscount(Integer discountQty) {
            this.discountQty = discountQty;
        }

        @Override
        public Amount price(BigDecimal price, UnitQuantity quantity) {
            return new Amount(price.multiply(new BigDecimal(discountQty)).multiply(new BigDecimal(quantity.divideBy(discountQty + 1).value))
                    .add(price.multiply(new BigDecimal(quantity.modulo(discountQty + 1).value))));
        }

        @Override
        public String toString() {
            return "(buy " + discountQty + " get one free)";
        }
    }
}
