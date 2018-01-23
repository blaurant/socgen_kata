package supermarket.domain;

import framework.Entity;
import framework.EntityId;
import framework.Strings;

import java.util.UUID;

import static framework.Objects.requireNotNull;

// let's says it's an Entity (aka DDD's Entity)
public class Good extends Entity<Good.GoodId> {

    private String name;
    private Price price;

    private Good(GoodId goodId, String name, Price price) {
        super(goodId);
        this.name = Strings.requireNotEmpty(name, "Name is null");
        this.price = requireNotNull(price, "Price is null");
    }

    public Good(String name, Price price) {
        this(GoodId.generate(), name, price);
    }

    public void applyDiscount(Discount discount) {
        checkPriceByUnit();
        price = new DiscountedPriceByUnit((PriceByUnit) price, discount);
    }

    private void checkPriceByUnit() {
        if (!price.getType().equals(Price.Type.BY_UNIT))
            throw new IllegalArgumentException("discounts are only for PriceByUnit");
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " at price " + price;
    }

    static class GoodId extends EntityId<UUID> {
        public GoodId(UUID id) {
            super(id);
        }

        public static GoodId generate() {
            return new GoodId(UUID.randomUUID());
        }
    }
}



