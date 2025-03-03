import maslova.Fraction;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FractionTests {
    @Test(groups = {"smoke"}, description = "Sum of two positive fraction numbers")
    void simpleFractionTest() {
        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(2, 3);
        Fraction expectedSum = new Fraction(7, 6);
        Assert.assertEquals(expectedSum, Fraction.sum(first, second));
    }

    @Test(groups = {"smoke"}, description = "Sum of negative fraction numbers")
    void negativeFractionTest() {
        Fraction first = new Fraction(-1, 2);
        Fraction second = new Fraction(1, 3);
        Fraction expectedSum = new Fraction(-1, 6);
        Assert.assertEquals(expectedSum, Fraction.sum(first, second));
    }

    @Test(groups = {"negative"}, description = "Zero nominator")
    void zeroNumFractionTest() {
        Fraction first = new Fraction(-1, 2);
        Fraction second = new Fraction(2, 4);
        Fraction expectedSum = new Fraction(0, 8);
        Assert.assertEquals(expectedSum, Fraction.sum(first, second));
    }

    @Test(groups = {"negative"}, description = "Zero denominator",
            expectedExceptions = ArithmeticException.class,
            expectedExceptionsMessageRegExp = "Cannot divide by zero")
    void zeroDenFractionTest() {
        new Fraction(1, 0);
    }

    @Test(groups = {"negative"}, description = "Nominator and denominator are not defined",
    expectedExceptions = NullPointerException.class)
    void nullFractionTest() {
        new Fraction(null, null);
    }

    @Test(groups = {"negative"}, description = "Overflow",
    expectedExceptions = ArithmeticException.class)
    void longFractionTest() {
        new Fraction(1, 0);
    }
}
