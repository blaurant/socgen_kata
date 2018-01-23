package supermarket.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GoodTest {

    private static final String CAN_OF_BEANS = "can of beans";
    private static final Price A_PRICE = new PriceByUnit("0.65");

    @Test(expected = IllegalArgumentException.class)
    public void nullNameConsistencyTest() {
        new Good(null, A_PRICE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameConsistencyTest() {
        new Good("", A_PRICE);
    }

    @Test
    public void nameConsistencyTest() {
        assertThat(new Good(CAN_OF_BEANS, A_PRICE).getName()).isEqualTo(CAN_OF_BEANS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPriceConsistencyTest() {
        new Good(CAN_OF_BEANS, null);
    }

    @Test
    public void priceConsistencyTest() {
        assertThat(new Good(CAN_OF_BEANS, A_PRICE).getPrice())
                .isEqualTo(A_PRICE);
    }
}
