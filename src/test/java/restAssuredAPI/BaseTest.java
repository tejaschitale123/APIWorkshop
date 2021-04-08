package restAssuredAPI;

import api.workshop.ApiHelper;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    private static final String BASE_URI = "https://reqres.in";

    protected ApiHelper apiHelper;

    @BeforeTest
    public void setup () {
        this.apiHelper = ApiHelper.create (BASE_URI);
    }
}
