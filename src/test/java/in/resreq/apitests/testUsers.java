package in.resreq.apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

public class testUsers {
    final String BASE_PATH = "/api/users";
    final String BASE_URI = "https://reqres.in";

    @BeforeClass
    public void setup(){

        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = BASE_PATH;
    }

    @Test
    public void listUsersReturnsAllTheUsers(){
        when().
                get().
        then().
                body("total", equalTo(13));
    }

    @Test
    public void list_users_returns_an_array_of_users(){
        when()
                .get()
        .then()
                .body("data", instanceOf(ArrayList.class));
    }

    @Test
    public void create_user_returns_201_status_code(){
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "morpheus");
        requestBody.put("job", "leader");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post()
        .then()
                .assertThat()
                .statusCode(equalTo(201));

    }
}
