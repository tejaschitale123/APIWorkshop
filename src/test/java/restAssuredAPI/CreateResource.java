package restAssuredAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateResource extends BaseTest {

    @Test
    public void createUser() {
        JSONObject requestData = new JSONObject();
        requestData.put("name", "tejas");
        requestData.put("job", "Quality Analyst");
        Response response = given().contentType(ContentType.JSON).
                body(requestData).
                when().
                post("/api/users");
        Assert.assertEquals(response.getStatusCode(), 201, "Status code mismatch expected 200 ,Received : " + response.getStatusCode());
    }
}
