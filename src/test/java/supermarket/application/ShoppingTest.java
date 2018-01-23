package supermarket.application;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import supermarket.domain.*;

import java.math.BigDecimal;

import static supermarket.domain.WeightQuantity.Unit.OUNCE;
import static supermarket.domain.WeightQuantity.Unit.POUND;

public class ShoppingTest extends ScenarioTest<GivenSomeState, WhenSomeAction, ThenSomeOutcome> {

    static final Good CAN_OF_BEANS = new Good("can of beans", new PriceByUnit("0.65"));
    static final Good CHOCOLATE = new Good("chocolate", new PriceByUnit("1"));
    static final Good PUDDING = new Good("pudding", new PriceByWeight("1.99", POUND));

    static final Discount DISCOUNT_BY_LOT_3 = Discount.BY_LOT(3, new PriceByUnit("1"));
    static final Discount DISCOUNT_BUY_2_GET_1 = Discount.ONE_FOR_FREE(2);

    @Test
    public void empty_cart() {
        given().an_empty_cart();
        when().calculate_total();
        then().result_is("0");
    }

    @Test
    public void total_for_one_good() {
        given().an_empty_cart();
        when().add(1, CAN_OF_BEANS)
                .and().calculate_total();
        then().result_is("0.65");
    }


    @Test
    public void total_for_two_time_the_same_good() {
        given().an_empty_cart();
        when().add(1, CAN_OF_BEANS)
                .and().add(2, CAN_OF_BEANS)
                .and().calculate_total();
        then().result_is("1.95");
    }

    @Test
    public void total_for_multiple_goods() {
        given().an_empty_cart();
        when().add(1, CAN_OF_BEANS)
                .and().add(1, CHOCOLATE);
        when().calculate_total();
        then().result_is("1.65");
    }


    @Test
    public void total_for_goods_with_discount_by_lot() {
        Good good = new Good("can of beans", new PriceByUnit("0.65"));

        given().an_empty_cart().and().a_discount_on_good(good, DISCOUNT_BY_LOT_3);
        when().add(3, good).and().calculate_total();
        then().result_is("1.00");
        when().add(1, good).and().calculate_total();
        then().result_is("1.65");
        when().add(1, good).and().calculate_total();
        then().result_is("2.30");
    }

    @Test
    public void total_for_goods_with_discount_one_for_free() {
        Good good = new Good("can of beans", new PriceByUnit("1"));

        given().an_empty_cart().and().a_discount_on_good(good, DISCOUNT_BUY_2_GET_1);
        when().add(1, good).and().calculate_total();
        then().result_is("1.00");
        when().add(1, good).and().calculate_total();
        then().result_is("2.00");
        when().add(1, good).and().calculate_total();
        then().result_is("2.00");
        when().add(1, good).and().calculate_total();
        then().result_is("3.00");
        when().add(1, good).and().calculate_total();
        then().result_is("4.00");
        when().add(1, good).and().calculate_total();
        then().result_is("4.00");
    }

    @Test
    public void total_for_goods_by_weight() {
        given().an_empty_cart();
        when().add(new WeightQuantity("4", POUND), PUDDING)
                .calculate_total();
        then().result_is("7.96");
    }

    @Test
    public void total_for_goods_by_weight_with_unit_conversion() {
        given().an_empty_cart();
        when().add(new WeightQuantity("4", OUNCE), PUDDING)
                .calculate_total();
        then().result_is("0.50");
    }

    @Test
    public void total_for_goods_by_weight_and_by_unit() {
        Good chocolate = new Good("chocolate", new PriceByUnit("2"));
        Good pudding = new Good("pudding", new PriceByWeight("10", POUND));

        given().an_empty_cart();
        when().add(new WeightQuantity("1", POUND), pudding)
                .and().add(new WeightQuantity("1", POUND), pudding)
                .and().add(2, chocolate)
                .and().add(2, chocolate)
                .calculate_total();
        then().result_is("28.00");
    }

}

class GivenSomeState extends Stage<GivenSomeState> {

    @ProvidedScenarioState
    ShoppingCart shoppingCart;


    public GivenSomeState an_empty_cart() {
        this.shoppingCart = new ShoppingCart();
        return this;
    }

    public void a_discount_on_good(Good good, Discount discount) {
        good.applyDiscount(discount);
    }
}

class WhenSomeAction extends Stage<WhenSomeAction> {

    @ExpectedScenarioState
    ShoppingCart shoppingCart;

    @ProvidedScenarioState
    Amount total;

    public WhenSomeAction calculate_total() {
        this.total = shoppingCart.total();
        return this;
    }

    public WhenSomeAction add(int number, Good good) {
        shoppingCart = shoppingCart.add(good, new UnitQuantity(number));
        return this;
    }

    public WhenSomeAction add(WeightQuantity weightQuantity, Good good) {
        shoppingCart = shoppingCart.add(good, weightQuantity);
        return this;
    }
}

class ThenSomeOutcome extends Stage<ThenSomeOutcome> {

    @ProvidedScenarioState
    Amount total;

    public ThenSomeOutcome result_is(String number) {
        Assertions.assertThat(total.value.toString()).isEqualTo(number);
        return this;
    }
}
