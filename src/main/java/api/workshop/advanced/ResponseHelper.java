package api.workshop.advanced;

import java.util.Objects;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;

@Getter
public class ResponseHelper {
    private final Response response;

    public ResponseHelper (final Response response) {
        this.response = Objects.requireNonNull (response);
        this.response.then ()
            .log ()
            .all ();
    }

    public boolean boolValue (final String expression) {
        return boolValue (null, expression);
    }

    public boolean boolValue (final String root, final String expression) {
        final JsonPath jsonPath = getJsonPath (root);
        return jsonPath.getBoolean (expression);
    }

    public int intValue (final String root, final String expression) {
        final JsonPath jsonPath = getJsonPath (root);
        return jsonPath.getInt (expression);
    }

    public int statusCode () {
        return this.response.statusCode ();
    }

    public String statusMessage () {
        return this.response.statusLine ();
    }

    public String stringValue (final String root, final String expression) {
        final JsonPath jsonPath = getJsonPath (root);
        return jsonPath.getString (expression);
    }

    public String stringValue (final String expression) {
        return stringValue (null, expression);
    }

    private JsonPath getJsonPath (final String root) {
        if (root != null) {
            this.response.then ()
                .rootPath (root);
        }
        return JsonPath.from (this.response.asString ());
    }
}