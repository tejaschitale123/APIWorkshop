package api.workshop.basic.restassured;

import static com.google.common.truth.Truth.assertWithMessage;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import java.io.File;

import api.workshop.advanced.ConfigHelper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class AlbumJourneyTest {
    private String               accessToken;
    private String               albumHash;
    private String               baseUri;
    private String               imageHash;
    private RequestSpecification request;

    @BeforeSuite
    public void setup () {
        this.baseUri = ConfigHelper.getConfigValue ("base_url");
        this.accessToken = ConfigHelper.getConfigValue ("access_token");
    }

    @BeforeMethod
    public void setupRequest () {
        this.request = RestAssured.given ()
            .log ()
            .all ()
            .baseUri (this.baseUri)
            .basePath ("/3")
            .auth ()
            .preemptive ()
            .oauth2 (this.accessToken);
    }

    @Test
    public void testCreateAlbum () {
        final Response response = this.request.formParam ("title", "Basic Title")
            .formParam ("description", "This is basic album")
            .when ()
            .post ("/album");
        response.then ()
            .log ()
            .all ();

        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (getValue (response).getBoolean ("success"))
            .isTrue ();

        this.albumHash = getValue (response).getString ("data.id");
    }

    @Test
    public void testDeleteAlbum () {
        assertWithMessage ("Album Hash").that (this.albumHash)
            .isNotNull ();

        final Response response = this.request.pathParam ("albumHash", this.albumHash)
            .delete ("/album/{albumHash}");
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
        assertWithMessage ("Image Hash").that (this.imageHash)
            .isNotNull ();

        final Response response = this.request.pathParam ("imageHash", this.imageHash)
            .delete ("/image/{imageHash}");
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
        assertWithMessage ("Album Hash").that (this.albumHash)
            .isNotNull ();

        final Response response = this.request.multiPart ("image",
            new File (format ("{0}/src/test/resources/pic.jpg", getProperty ("user.dir"))))
            .formParam ("album", this.albumHash)
            .formParam ("name", "pic.jpg")
            .formParam ("title", "Basic Pic")
            .formParam ("description", "This is basic pic")
            .when ()
            .post ("/upload");
        response.then ()
            .log ()
            .all ();

        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (getValue (response).getBoolean ("success"))
            .isTrue ();

        this.imageHash = getValue (response).getString ("data.id");
    }

    private JsonPath getValue (final Response response) {
        return JsonPath.from (response.asString ());
    }
}