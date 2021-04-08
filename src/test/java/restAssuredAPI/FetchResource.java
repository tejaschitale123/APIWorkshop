package restAssuredAPI;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import io.restassured.response.Response;
import org.testng.annotations.Test;

public class FetchResource extends BaseTest {
    @Test
    public void getListOfResources () {
        final Response response = given ().when ()
            .get ("/api/users?page=2");
        assertEquals (response.statusCode (), 200);
        System.out.println (response.asPrettyString ());
    }

    @Test
    public void getSingleResource () {
        final Response response = given ().when ()
            .get ("/api/users/2");
        assertEquals (response.statusCode (), 200);
        System.out.println (response.asPrettyString ());
    }

}
