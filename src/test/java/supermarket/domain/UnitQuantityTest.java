package supermarket.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UnitQuantityTest {

    @Test(expected = IllegalArgumentException.class)
    public void consistency() {
        new UnitQuantity(-1);
    }

    @Test
    public void add() {
        Assertions.assertThat(new UnitQuantity(1).add(new UnitQuantity(41))).isEqualTo(new UnitQuantity(42));
    }
}
