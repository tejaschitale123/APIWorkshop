package restAssuredAPI;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class UpdateResource extends BaseTest {
    @Test
    public void updateUser() {
        JSONObject requestData = new JSONObject();
        requestData.put("name", "abc");
        requestData.put("job", "test");
        Response response = given().contentType(ContentType.JSON).
                body(requestData).
                when().
                put("/api/users/2");
        System.out.println(response.getBody().prettyPeek());
        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch expected 200 ,Received : " + response.getStatusCode());


    }
}
