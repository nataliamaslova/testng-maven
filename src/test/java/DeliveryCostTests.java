import maslova.DeliveryCost;
import maslova.ItemDimension;
import maslova.Workload;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static maslova.ItemDimension.*;
import static maslova.Workload.*;

/**
 *  distance,       dimension,      fragility,      workload
 *  50: 0 .. 2      SMALL:  100      true:   300     NORMAL      1.0
 * 100: 2 .. 10     LARGE:  200      false:    0     INCREASED   1.2
 * 200: 10 ..30                                      HIGH        1.4
 * 300: > 30                                         TOO_HIGH    1.6
 *
 * Уточнить верхнюю границу по расстоянию сейчас 50, иначе сервис разорится;)
 *
 * Стоимость доставки не может быть меньше 400
 *
 * Доставка не осуществляется, если fragility = true и distance > 30
 *
 * Pairwise Pict Online:
 * https://pairwise.yuuniworks.com/
 * https://pairwise.teremokgames.com/
 *
 *
 * Positive cases - with pairwise: 4 * 2 * 2 * 4 = 64 -> 16 cases
 *
 * N 	distance	dimension	isFragile	workload
 * 1	0 .. 2	    SMALL	    false	NORMAL
 * 2	0 .. 2	    LARGE	    true	INCREASED
 * 3	0 .. 2	    SMALL	    false	HIGH
 * 4	0 .. 2	    LARGE	    true	TOO_HIGH
 * 5	2..10	    LARGE	    false	TOO_HIGH
 * 6	2..10	    SMALL	    true	NORMAL
 * 7	2..10	    LARGE	    false	INCREASED
 * 8	2..10	    SMALL	    true	HIGH
 * 9	10..30	    SMALL	    false	HIGH
 * 10	10..30	    LARGE	    true	TOO_HIGH
 * 11	10..30	    SMALL	    false	NORMAL
 * 12	10..30	    LARGE	    true	INCREASED
 * 13	30..	    LARGE	    false	INCREASED
 * 14	30..	    SMALL	    true	HIGH
 * 15	30..	    LARGE	    false	TOO_HIGH
 * 16	30..	    SMALL	    true	NORMAL
 *
 * Negative cases:
 * 1   35, LARGE, false, NORMAL    -> Impossible delivery: fragile, distance > 30 km
 * 2   -1, SMALL, false, HIGH      -> Impossible delivery: distance should not be negative
 * 3 50.1, SMALL, false, INCREASED -> Impossible delivery: too long distance
 */
public class DeliveryCostTests {

    @Test(groups = {"positive"}, description = "Delivery calculation",
            dataProvider = "positiveTestData")
    void calculateDeliveryCostPositiveTest(double distance, ItemDimension dimension, boolean isFragile, Workload workload, double expectedCost) {
        DeliveryCost delivery = new DeliveryCost(distance, dimension, isFragile, workload);
        double actualPrice = delivery.calculateDeliveryCost();

        double expectedPrice = expectedCost;

        Assert.assertEquals(expectedPrice, actualPrice, 0.001);
    }

    @DataProvider
    public Object[][] positiveTestData() {
        return new Object[][] {
                {0, SMALL, false, NORMAL, 400},
                {0.1, LARGE, true, INCREASED, 660},
                {1.9, SMALL, false, HIGH, 400},
                {2, LARGE, true, TOO_HIGH, 880},
                {2.1, LARGE, false, TOO_HIGH, 480},
                {5, SMALL, true, NORMAL, 500},
                {9.9, LARGE, false, INCREASED, 400},
                {10, SMALL, true, HIGH, 700},
                {10.1, SMALL, false, HIGH, 420},
                {20, LARGE, true, TOO_HIGH, 1120},
                {29.9, SMALL, false, NORMAL, 400},
                {30, LARGE, true, INCREASED, 840},
                {30.1, LARGE, false, INCREASED, 600},
                {35, SMALL, false, HIGH, 560},
                {40, LARGE, false, TOO_HIGH, 800},
                {50, SMALL, false, NORMAL, 400}
        };
    }

    @Test(groups = {"negative"}, description = "Impossible delivery: fragile, distance > 30 km")
    void exceptionTest() {
        DeliveryCost delivery = new DeliveryCost(35, ItemDimension.LARGE, true, NORMAL);

        Assert.assertThrows("Fragile item cannot be delivered for the distance > 30 km",
                UnsupportedOperationException.class, () -> delivery.calculateDeliveryCost());
    }

    @Test(groups = {"negative"}, description = "Impossible delivery: distance should not be negative")
    void negativeDistanceTest() {
        DeliveryCost delivery = new DeliveryCost(-1, SMALL, false, Workload.HIGH);

        Assert.assertThrows("Distance should not be negative",
                IllegalArgumentException.class, () -> delivery.calculateDeliveryCost());
    }

    @Test(groups = {"negative"}, description = "Impossible delivery: too long distance")
    void tooLongDistanceTest() {
        DeliveryCost delivery = new DeliveryCost(50.1, SMALL, false, Workload.INCREASED);

        Assert.assertThrows("Too long distance, please discuss maximal value with Product Owner",
                IllegalArgumentException.class, () -> delivery.calculateDeliveryCost());
    }
}

