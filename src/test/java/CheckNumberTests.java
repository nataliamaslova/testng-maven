import org.testng.Assert;
import org.testng.annotations.Test;

import static maslova.Main.checkNumberIsSquare;

public class CheckNumberTests {
    @Test(groups = {"positive"})
    void positiveTest() {
        Assert.assertTrue(checkNumberIsSquare(4));
        Assert.assertFalse(checkNumberIsSquare(5));
    }

    @Test(groups = {"negative"}, expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Number should be greater than zero")
    void negativeTest() {
        checkNumberIsSquare(-1);
    }
}
