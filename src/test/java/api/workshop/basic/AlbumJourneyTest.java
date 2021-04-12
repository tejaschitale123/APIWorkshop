package api.workshop.basic;

import static com.google.common.truth.Truth.assertWithMessage;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import java.io.File;

import api.workshop.advanced.ConfigHelper;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class AlbumJourneyTest {
    private String               accessToken;
    private String               albumDeleteHash;
    private String               albumHash;
    private String               baseUri;
    private String               clientId;
    private String               imageDeleteHash;
    private String               imageHash;
    private RequestSpecification request;

    @BeforeSuite
    public void setup () {
        this.baseUri = ConfigHelper.getConfigValue ("base_url");
        this.clientId = ConfigHelper.getConfigValue ("client_id");
        this.accessToken = ConfigHelper.getConfigValue ("access_token");
    }

    @BeforeMethod
    public void setupRequest () {
        this.request = RestAssured.given ()
            .log ()
            .all ()
            .baseUri (this.baseUri);
    }

    @Test
    public void testCreateAlbum () {
        final Response response = this.request.header (
            new Header ("Authorization", format ("Bearer {0}", this.accessToken)))
            .formParam ("title", "Basic Title")
            .formParam ("description", "This is basic album")
            .when ()
            .post ("/3/album");
        response.then ()
            .log ()
            .all ();
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (getValue (response).getBoolean ("success"))
            .isTrue ();
        this.albumHash = getValue (response).getString ("data.id");
        this.albumDeleteHash = getValue (response).getString ("data.deletehash");
    }

    @Test
    public void testDeleteAlbum () {
        final Response response = this.request.header (
            new Header ("Authorization", format ("Bearer {0}", this.accessToken)))
            .pathParam ("albumHash", this.albumHash)
            .delete ("/3/album/{albumHash}");
        response.then ()
            .log ()
            .all ();
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (getValue (response).getBoolean ("success"))
            .isTrue ();
    }

    @Test
    public void testDeleteImage () {
        final Response response = this.request.header (
            new Header ("Authorization", format ("Bearer {0}", this.accessToken)))
            .pathParam ("imageHash", this.imageHash)
            .delete ("/3/image/{imageHash}");
        response.then ()
            .log ()
            .all ();
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (getValue (response).getBoolean ("success"))
            .isTrue ();
    }

    @Test
    public void testUploadImage () {
        final Response response = this.request.header (
            new Header ("Authorization", format ("Client-ID {0}", this.clientId)))
            .auth ()
            .preemptive ()
            .oauth2 (this.accessToken)
            .multiPart ("image", new File (format ("{0}/src/test/resources/pic.jpg", getProperty ("user.dir"))))
            .formParam ("album", this.albumHash)
            .formParam ("name", "pic.jpg")
            .formParam ("title", "Basic Pic")
            .formParam ("description", "This is basic pic")
            .when ()
            .post ("/3/upload");
        response.then ()
            .log ()
            .all ();
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (getValue (response).getBoolean ("success"))
            .isTrue ();
        this.imageHash = getValue (response).getString ("data.id");
        this.imageDeleteHash = getValue (response).getString ("data.deletehash");
    }

    private JsonPath getValue (final Response response) {
        return JsonPath.from (response.asString ());
    }
}
