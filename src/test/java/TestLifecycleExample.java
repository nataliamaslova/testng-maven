import org.testng.annotations.*;

public class TestLifecycleExample {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("⚡ @BeforeSuite: Выполняется перед всей сьютой тестов");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("🔻 @AfterSuite: Выполняется после всей сьюты тестов");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("🚀 @BeforeClass: Выполняется перед тестами в этом классе");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("🏁 @AfterClass: Выполняется после тестов в этом классе");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("🔄 @BeforeMethod: Выполняется перед каждым тестом");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("✅ @AfterMethod: Выполняется после каждого теста");
    }

    @Test
    public void test1() {
        System.out.println("🟢 test1 выполняется");
    }

    @Test
    public void test2() {
        System.out.println("🟢 test2 выполняется");
    }
}
