package restAssuredAPI;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteResource extends BaseTest {
    @Test
    public void deleteUser () {
        final Response response = given ().
            when ()
            .
                delete ("/api/users/2");
        Assert.assertEquals (response.getStatusCode (), 204,
            "Status code mismatch expected 204 ,Received : " + response.getStatusCode ());
    }
}
