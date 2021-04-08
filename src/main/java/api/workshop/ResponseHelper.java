package api.workshop;

import java.util.Objects;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResponseHelper {
    private final Response response;

    public ResponseHelper (final Response response) {
        this.response = Objects.requireNonNull (response);
        this.response.then ()
            .log ()
            .all ();
    }

    public int statusCode () {
        return this.response.statusCode ();
    }

    public String statusMessage () {
        return this.response.statusLine ();
    }

    public Object valueFor (final String expression) {
        return valueFor (null, expression);
    }

    public Object valueFor (final String root, final String expression) {
        if (root != null) {
            this.response.then ()
                .rootPath (root);
        }
        final JsonPath jsonPath = JsonPath.from (this.response.asString ());
        return jsonPath.get (expression);
    }
}