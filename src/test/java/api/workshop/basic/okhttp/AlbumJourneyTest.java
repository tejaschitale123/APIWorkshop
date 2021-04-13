package api.workshop.basic.okhttp;

import static com.google.common.truth.Truth.assertWithMessage;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import java.io.File;
import java.util.Objects;

import api.workshop.advanced.ConfigHelper;
import io.restassured.path.json.JsonPath;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class AlbumJourneyTest {
    private String accessToken;
    private String albumHash;
    private String baseUri;
    private String imageHash;

    @BeforeSuite
    public void setup () {
        this.baseUri = ConfigHelper.getConfigValue ("base_url");
        this.accessToken = ConfigHelper.getConfigValue ("access_token");
    }

    @SneakyThrows
    @Test
    public void testCreateAlbum () {
        final OkHttpClient client = new OkHttpClient ().newBuilder ()
            .build ();
        final RequestBody body = new MultipartBody.Builder ().setType (MultipartBody.FORM)
            .addFormDataPart ("title", "My dank meme album")
            .addFormDataPart ("description", "This albums contains a lot of dank memes. Be prepared.")
            .build ();
        final Request request = new Request.Builder ().url (format ("{0}/3/album", this.baseUri))
            .addHeader ("Authorization", format ("Bearer {0}", this.accessToken))
            .post (body)
            .build ();
        final Response response = client.newCall (request)
            .execute ();

        final JsonPath responseBody = getValue (response);
        assertWithMessage ("Status Code").that (response.code ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (responseBody.getBoolean ("success"))
            .isTrue ();

        this.albumHash = responseBody.getString ("data.id");
    }

    @SneakyThrows
    @Test
    public void testDeleteAlbum () {
        assertWithMessage ("Album Hash").that (this.albumHash)
            .isNotNull ();

        final OkHttpClient client = new OkHttpClient ().newBuilder ()
            .build ();
        final Request request = new Request.Builder ().url (format ("{0}/3/album/{1}", this.baseUri, this.albumHash))
            .addHeader ("Authorization", format ("Bearer {0}", this.accessToken))
            .delete ()
            .build ();
        final Response response = client.newCall (request)
            .execute ();

        final JsonPath responseBody = getValue (response);
        assertWithMessage ("Status Code").that (response.code ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (responseBody.getBoolean ("success"))
            .isTrue ();
    }

    @SneakyThrows
    @Test
    public void testImageDelete () {
        assertWithMessage ("Image Hash").that (this.imageHash)
            .isNotNull ();

        final OkHttpClient client = new OkHttpClient ().newBuilder ()
            .build ();
        final Request request = new Request.Builder ().url (format ("{0}/3/image/{1}", this.baseUri, this.imageHash))
            .addHeader ("Authorization", format ("Bearer {0}", this.accessToken))
            .delete ()
            .build ();
        final Response response = client.newCall (request)
            .execute ();

        final JsonPath responseBody = getValue (response);
        assertWithMessage ("Status Code").that (response.code ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (responseBody.getBoolean ("success"))
            .isTrue ();
    }

    @SneakyThrows
    @Test
    public void testImageUpload () {
        assertWithMessage ("Album Hash").that (this.albumHash)
            .isNotNull ();

        final OkHttpClient client = new OkHttpClient ().newBuilder ()
            .build ();
        final RequestBody body = new MultipartBody.Builder ().setType (MultipartBody.FORM)
            .addFormDataPart ("image", "pic.jpg",
                RequestBody.create (new File (format ("{0}/src/test/resources/pic.jpg", getProperty ("user.dir"))),
                    MediaType.parse ("application/octet-stream")))
            .addFormDataPart ("album", this.albumHash)
            .addFormDataPart ("name", "pic.jpg")
            .addFormDataPart ("title", "My Pic")
            .addFormDataPart ("description", "This is me")
            .build ();
        final Request request = new Request.Builder ().url (format ("{0}/3/upload", this.baseUri))
            .post (body)
            .addHeader ("Authorization", format ("Bearer {0}", this.accessToken))
            .build ();
        final Response response = client.newCall (request)
            .execute ();

        final JsonPath responseBody = getValue (response);
        assertWithMessage ("Status Code").that (response.code ())
            .isEqualTo (200);
        assertWithMessage ("Success").that (responseBody.getBoolean ("success"))
            .isTrue ();

        this.imageHash = responseBody.getString ("data.id");
    }

    @SneakyThrows
    private JsonPath getValue (final Response response) {
        return JsonPath.from (Objects.requireNonNull (response.body ())
            .string ());
    }
}