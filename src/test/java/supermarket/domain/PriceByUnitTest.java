package supermarket.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PriceByUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void consistencyTest() {
        new PriceByUnit("-2");
    }

    @Test
    public void multiply() {
        Assertions.assertThat(new PriceByUnit("3.23").multiply(new UnitQuantity(3)).value.toString())
                .isEqualTo("9.69");
    }

}
