package restAssuredAPI;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteResource extends BaseTest{


    @Test
    public void deleteUser()  {
        Response response = given().
                when().
                delete("/api/users/2");
        Assert.assertEquals(response.getStatusCode(), 204, "Status code mismatch expected 204 ,Received : " + response.getStatusCode());
    }
}
