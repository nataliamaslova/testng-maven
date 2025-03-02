import dto.User;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class ParametrizedTests {
    @Test(dataProvider = "dataMethod")
    public void dataProviderTest(int value) {
        System.out.println("Тест с параметром: " + value);
    }

    @Test(dataProvider = "customName")
    public void dataProviderComplexTest(List<Integer> list, String test, Double number, Boolean value, User user) {
        System.out.printf("Тест с параметрами: %s, %s, %s, %s, %s", list, test, number, value, user);
    }

    @DataProvider
    public Object[][] dataMethod() {
        return new Object[][]{{1}, {2}, {3}};
    }

    @DataProvider(name = "customName")
    public Object[][] complexDataMethod() {
        return new Object[][]{
                {List.of(1), "Test", 1.0, true, new User("name", "lastname", 10)},
                {List.of(2), "Test3", 2.0, false, new User("name2", "lastname2", 20)}
        };
    }
}
