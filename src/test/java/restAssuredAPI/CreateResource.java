package restAssuredAPI;

import static com.google.common.truth.Truth.assertWithMessage;

import api.workshop.ResponseHelper;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class CreateResource extends BaseTest {
    @Test
    public void createUser () {
        final User user = User.builder ()
            .name ("Tejas")
            .job ("QA")
            .build ();

        final ResponseHelper response = this.apiHelper.basePath ("/api/users")
            .contentType (ContentType.JSON)
            .body (user)
            .post ();

        assertWithMessage ("Status Code").that (response.statusCode ())
            .isEqualTo (201);
        assertWithMessage ("ID").that (response.valueFor ("id")
            .toString ())
            .isNotNull ();
    }
}
