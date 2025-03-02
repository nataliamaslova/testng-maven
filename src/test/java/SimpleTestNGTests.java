import dto.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SimpleTestNGTests {
    @Test(description = "Этот тест проверяет сумму двух чисел")
    public void simpleTestNGTest() {
        int actualSum = 2 + 2;
        int expectedSum = 4;
        Assert.assertEquals(actualSum, expectedSum);
    }

    @Test(enabled = false) // Тест не будет запущен, в отчет попадет как ignored
    public void disabledTest() {
        int actualSum = 2 + 2;
        int expectedSum = 4;
        Assert.assertEquals(actualSum, expectedSum);
        System.out.println("Test");
    }


    @Test(timeOut = 2000) // Аналог Timeout в JUnit
    public void timeoutTest() throws InterruptedException {
        Thread.sleep(1000);
        int actualSum = 2 + 2;
        int expectedSum = 4;
        Assert.assertEquals(actualSum, expectedSum);
    }

    @Test(invocationCount = 3) // Аналог RepeatedTest
    public void repeatedTest() {
        int actualSum = 2 + 2;
        int expectedSum = 4;
        Assert.assertEquals(actualSum, expectedSum);
    }

    @Test(invocationCount = 5, invocationTimeOut = 3000)
    public void repeatedTestWithTimeout() {
        System.out.println("Повторяемый тест с ограничением по времени");
    }

    @Test
    public void assertTrueFalseTest() {
        int actualSum = 2 + 2;
        int expectedSum = 4;
        Assert.assertTrue(expectedSum == actualSum);
        Assert.assertFalse(expectedSum != actualSum);
    }

    @Test
    public void exceptionTest() {
        String test = null;
        try {
            test.length();
            Assert.fail("Ожидалось исключение NullPointerException");
        } catch (NullPointerException e) {
            Assert.assertEquals(e.getMessage(), "Cannot invoke \"String.length()\" because \"test\" is null");
        }
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = ".*Cannot invoke .* because .* is null.*")
    public void expectedExceptionWithMessageTest() {
        String test = null;
        test.length();
    }

    @Test
    public void assertsAllTest() {
        SoftAssert softAssert = new SoftAssert();

        User user = new User("John", "Doe", 30);

        softAssert.assertEquals(user.getFirstName(), "John", "Неправильное имя");
        softAssert.assertEquals(user.getLastName(), "Doe", "Неправильная фамилия");
        softAssert.assertEquals(user.getAge(), 30, "Неправильный возраст");
        softAssert.assertAll();
    }

    @Test(groups = {"smoke", "regression"})
    public void testGroups() {
        System.out.println("Тест из групп smoke и regression");
    }

    @Test(dependsOnGroups = {"smoke"})
    public void dependsOnGroupTest() {
        System.out.println("Этот тест выполняется после группы 'smoke'");
    }

    @Test(dependsOnMethods = {"baseMethod"})
    public void dependentTest() {
        System.out.println("Этот тест выполняется после baseMethod");
    }

    @Test
    public void baseMethod() {
        System.out.println("Базовый метод");
    }
}
