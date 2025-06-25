package utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class ApiUtils {
    public static RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .baseUri("https://api.trello.com/1")
                .queryParam("key", TokenManager.KEY)
                .queryParam("token", TokenManager.TOKEN)
                .header("Content-Type", "application/json");
    }
}
