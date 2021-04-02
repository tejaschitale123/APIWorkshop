package restAssuredAPI;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    final String BASE_URI = "https://reqres.in";

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }
}
