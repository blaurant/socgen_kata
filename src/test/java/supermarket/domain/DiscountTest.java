package supermarket.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;

public class DiscountTest {

    @Test
    public void noDiscount() {
        Assertions.assertThat(Discount.NO_DISCOUNT.price(new BigDecimal("42"), new UnitQuantity(2)).value.toString())
                .isEqualTo("84");
    }

    @Test
    public void byLotDiscount() {
        Assertions.assertThat(Discount.BY_LOT(3, new PriceByUnit("100")).price(new BigDecimal("42"), new UnitQuantity(2)).value.toString())
                .isEqualTo("84");
        Assertions.assertThat(Discount.BY_LOT(3, new PriceByUnit("100")).price(new BigDecimal("42"), new UnitQuantity(3)).value.toString())
                .isEqualTo("100");
        Assertions.assertThat(Discount.BY_LOT(3, new PriceByUnit("100")).price(new BigDecimal("42"), new UnitQuantity(4)).value.toString())
                .isEqualTo("142");
    }

    @Test
    public void oneForFreeDiscount() {
        Assertions.assertThat(Discount.ONE_FOR_FREE(2).price(new BigDecimal("1"), new UnitQuantity(1)).value.toString())
                .isEqualTo("1");
        Assertions.assertThat(Discount.ONE_FOR_FREE(2).price(new BigDecimal("1"), new UnitQuantity(2)).value.toString())
                .isEqualTo("2");
        Assertions.assertThat(Discount.ONE_FOR_FREE(2).price(new BigDecimal("1"), new UnitQuantity(3)).value.toString())
                .isEqualTo("2");
        Assertions.assertThat(Discount.ONE_FOR_FREE(2).price(new BigDecimal("1"), new UnitQuantity(4)).value.toString())
                .isEqualTo("3");
    }

}
