package api.workshop.advanced;

import static com.google.common.truth.Truth.assertWithMessage;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import lombok.SneakyThrows;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class AlbumJourneyTest {
    private String    accessToken;
    private String    albumDeleteHash;
    private String    albumHash;
    private ApiHelper api;
    private String    clientId;
    private String    imageDeleteHash;
    private String    imageHash;

    @BeforeSuite
    public void setup () {
        this.api = ApiHelper.create (ConfigHelper.getConfigValue ("base_url"));
        this.clientId = ConfigHelper.getConfigValue ("client_id");
        this.accessToken = ConfigHelper.getConfigValue ("access_token");
    }

    @Test
    public void testAlbumDelete () {
        final ResponseHelper response = this.api.compose ()
            .header ("Authorization", format ("Bearer {0}", this.accessToken))
            .pathParam ("albumHash", this.albumHash)
            .delete ("/3/album/{albumHash}");
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (response.boolValue ("success"))
            .isTrue ();
    }

    @Test
    public void testCreateAlbum () {
        final ResponseHelper response = this.api.compose ()
            .header ("Authorization", format ("Bearer {0}", this.accessToken))
            .formParam ("title", "My title 1")
            .formParam ("description", "This albums contains a lot of dank memes. Be prepared.")
            .post ("/3/album");
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (response.boolValue ("success"))
            .isTrue ();
        this.albumHash = response.stringValue ("data.id");
        this.albumDeleteHash = response.stringValue ("data.deletehash");
    }

    @Ignore
    @Test
    public void testGenerateToken () {
        final ResponseHelper response = this.api.compose ()
            .formParam ("refresh_token", ConfigHelper.getConfigValue ("refresh_token"))
            .formParam ("client_id", this.clientId)
            .formParam ("client_secret", ConfigHelper.getConfigValue ("client_secret"))
            .formParam ("grant_type", "refresh_token")
            .post ("/oauth2/token");
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (response.boolValue ("success"))
            .isTrue ();
        this.accessToken = response.stringValue ("accessToken");
    }

    @Test
    public void testImageDelete () {
        final ResponseHelper response = this.api.compose ()
            .header ("Authorization", format ("Bearer {0}", this.accessToken))
            .pathParam ("imageHash", this.imageHash)
            .delete ("/3/image/{imageHash}");
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (response.boolValue ("success"))
            .isTrue ();
    }

    @SneakyThrows
    @Test
    public void testImageUpload () {
        final ResponseHelper response = this.api.compose ()
            .auth (this.accessToken)
            .multiPart ("image", format ("{0}/src/test/resources/pic.jpg", getProperty ("user.dir")))
            .formParam ("album", this.albumHash)
            .formParam ("name", "pic.jpg")
            .formParam ("title", "My Pic")
            .formParam ("description", "This is me")
            .post ("/3/upload");
        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (response.boolValue ("success"))
            .isTrue ();
        this.imageHash = response.stringValue ("data.id");
        this.imageDeleteHash = response.stringValue ("data.deletehash");
    }
}