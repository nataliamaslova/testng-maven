import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleTests {
    @Test
    void firstTest() {
        String a = "Test";
        String b = "Example";
        Assert.assertEquals(a + b, "TestExample");
    }

}
