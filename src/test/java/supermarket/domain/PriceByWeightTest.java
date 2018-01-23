package supermarket.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static supermarket.domain.WeightQuantity.Unit.OUNCE;
import static supermarket.domain.WeightQuantity.Unit.POUND;

public class PriceByWeightTest {


    @Test(expected = IllegalArgumentException.class)
    public void consistencyTest() {
        new PriceByWeight("-1", OUNCE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void consistencyTest2() {
        new PriceByWeight("1", null);
    }

    @Test
    public void multiply() {
        Assertions.assertThat(new PriceByWeight("1", POUND).multiply(new WeightQuantity("3", POUND)).value.toString())
                .isEqualTo("3");
    }
}