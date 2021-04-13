package api.workshop.advanced;

import static com.google.common.truth.Truth.assertWithMessage;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import lombok.SneakyThrows;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class AlbumJourneyTest {
    private String    accessToken;
    private String    albumHash;
    private ApiHelper api;
    private String    imageHash;

    @BeforeSuite
    public void setup () {
        this.api = ApiHelper.create (ConfigHelper.getConfigValue ("base_url"));
        this.accessToken = ConfigHelper.getConfigValue ("access_token");
    }

    @BeforeMethod
    public void setupRequest () {
        this.api.compose ()
            .basePath ("/3")
            .auth (this.accessToken);
    }

    @Test
    public void testAlbumDelete () {
        assertWithMessage ("Album Hash").that (this.albumHash)
            .isNotNull ();

        final ResponseHelper response = this.api.pathParam ("albumHash", this.albumHash)
            .delete ("/album/{albumHash}");

        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (response.boolValue ("success"))
            .isTrue ();
    }

    @Test
    public void testCreateAlbum () {
        final ResponseHelper response = this.api.formParam ("title", "My title 1")
            .formParam ("description", "This albums contains a lot of dank memes. Be prepared.")
            .post ("/album");

        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (response.boolValue ("success"))
            .isTrue ();

        this.albumHash = response.stringValue ("data.id");
    }

    @Test
    public void testImageDelete () {
        assertWithMessage ("Image Hash").that (this.imageHash)
            .isNotNull ();

        final ResponseHelper response = this.api.pathParam ("imageHash", this.imageHash)
            .delete ("/image/{imageHash}");

        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (response.boolValue ("success"))
            .isTrue ();
    }

    @SneakyThrows
    @Test
    public void testImageUpload () {
        assertWithMessage ("Album Hash").that (this.albumHash)
            .isNotNull ();

        final ResponseHelper response = this.api.multiPart ("image",
            format ("{0}/src/test/resources/pic.jpg", getProperty ("user.dir")))
            .formParam ("album", this.albumHash)
            .formParam ("name", "pic.jpg")
            .formParam ("title", "My Pic")
            .formParam ("description", "This is me")
            .post ("/upload");

        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (response.boolValue ("success"))
            .isTrue ();

        this.imageHash = response.stringValue ("data.id");
    }
}