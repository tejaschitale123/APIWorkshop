package restAssuredAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class FetchResource extends BaseTest {

    @Test
    public void getSingleResource() {
        Response response = given().when().get("/api/users/2");
        assertEquals(response.statusCode(), 200);
        System.out.println(response.asPrettyString());
    }

    @Test
    public void getListOfResources() {
        Response response = given().when().get("/api/users?page=2");
        assertEquals(response.statusCode(), 200);
        System.out.println(response.asPrettyString());
    }


}
