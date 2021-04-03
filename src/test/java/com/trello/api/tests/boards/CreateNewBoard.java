package com.trello.api.tests.boards;

import com.trello.api.board.Board;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CreateNewBoard {
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://api.trello.com/";
    }

    // To demonstrate how a POJO class can be used to send post request body
    @Test
    public void createPrivateBoard() {
        Board board = new Board("Automation Test", "Board Desc", "private");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Token")
                .body(board)
                .log().all()
        .when()
                .post("/1/boards")
        .then()
                .statusCode(401)
                .assertThat()
                .body(equalTo("invalid key"));
    }
}
