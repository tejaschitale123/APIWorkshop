package api.workshop.basic.restassured;

import static com.google.common.truth.Truth.assertWithMessage;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RestImgurTest {
    private String               albumId;
    private RequestSpecification request;

    @BeforeMethod
    public void setupRequest () {
        this.request = RestAssured.given ()
            .log ()
            .all ()
            .baseUri ("https://api.imgur.com")
            .basePath ("/3");
    }

    @Test
    public void testCreateAlbum () {
        final Response response = this.request.header (
            new Header ("Authorization", "Bearer 525c2feaedba371ca98dfd4c1f506f6949bad623"))
            .formParam ("title", "Test Album")
            .formParam ("description", "Demo Album")
            .when ()
            .post ("/album");
        response.then ()
            .log ()
            .all ();
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        final JsonPath jsonPath = JsonPath.from (response.asString ());
        assertWithMessage ("Success Flag").that (jsonPath.getBoolean ("success"))
            .isTrue ();
        assertWithMessage ("Album ID").that (jsonPath.getString ("data.id"))
            .isNotNull ();
        this.albumId = jsonPath.getString ("data.id");
    }

    @Test (dependsOnMethods = "testCreateAlbum")
    public void testListImageFromAlbum () {
        final Response response = this.request.header (
            new Header ("Authorization", "Bearer 525c2feaedba371ca98dfd4c1f506f6949bad623"))
            .pathParam ("albumHash", this.albumId)
            .when ()
            .get ("/album/{albumHash}/images");
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
    }
}
