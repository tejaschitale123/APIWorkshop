package api.workshop;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

public class ApiHelper {
    private static final Gson GSON = new GsonBuilder ().setFieldNamingPolicy (
        FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setPrettyPrinting ()
        .create ();

    public static ApiHelper create (final String baseUri) {
        return new ApiHelper (baseUri);
    }

    private final RequestSpecification request;

    private ApiHelper (final String baseUri) {
        this.request = RestAssured.given ()
            .baseUri (baseUri)
            .log ()
            .all ();
    }

    public ApiHelper basePath (final String basePath) {
        this.request.basePath (basePath);
        return this;
    }

    public <T> ApiHelper body (final T body) {
        this.request.body (GSON.toJson (body));
        return this;
    }

    public ApiHelper contentType (final ContentType contentType) {
        this.request.contentType (contentType);
        return this;
    }

    public ResponseHelper get () {
        return get ("");
    }

    public ResponseHelper get (final String path) {
        return new ResponseHelper (this.request.when ()
            .get (path));
    }

    public ApiHelper header (final String key, final String value) {
        this.request.header (new Header (key, value));
        return this;
    }

    public ApiHelper pathParam (final String key, final Object value) {
        this.request.pathParam (key, value);
        return this;
    }

    public ApiHelper port (final int portNo) {
        this.request.port (portNo);
        return this;
    }

    public ResponseHelper post (final String path) {
        return new ResponseHelper (this.request.when ()
            .post (path));
    }

    public ResponseHelper post () {
        return post ("");
    }

    public ApiHelper queryParam (final String key, final String value) {
        this.request.queryParam (key, value);
        return this;
    }
}