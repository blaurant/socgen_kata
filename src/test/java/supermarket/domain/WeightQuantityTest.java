package supermarket.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static supermarket.domain.WeightQuantity.Unit.OUNCE;
import static supermarket.domain.WeightQuantity.Unit.POUND;

public class WeightQuantityTest {

    @Test(expected = IllegalArgumentException.class)
    public void negativeWeightConsistencyTest() {
        new WeightQuantity("-2", OUNCE);
    }

    @Test
    public void conversion_pound_to_ounce() {
        Assertions.assertThat(new WeightQuantity("1", POUND).convertTo(OUNCE).getWeight().toString())
                .isEqualTo("16");
    }

    @Test
    public void conversion_ounce_to_pound() {
        Assertions.assertThat(new WeightQuantity("1", OUNCE).convertTo(POUND).getWeight().toString())
                .isEqualTo("0.0625");
    }

    @Test
    public void conversion3() {
        Assertions.assertThat(new WeightQuantity("4", OUNCE).convertTo(POUND).getWeight().toString())
                .isEqualTo("0.2500");
    }
}