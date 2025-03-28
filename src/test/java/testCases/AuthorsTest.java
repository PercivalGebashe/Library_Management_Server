package testCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthorsTest {

    private static RequestSpecification request;
    private static Response response;

    @Test(priority = 0)
    public void getAuthorsTest() {
        request = RestAssured.given().baseUri("http://localhost:8082/api/v1/authors");
        response = request.when()
                .get()
                .then()
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 200);
        System.out.println(response.getBody().asString());
    }

    @Test(priority = 1)
    public void getAuthorsNegativeTest() {
        request = RestAssured.given().baseUri("http://localhost:8082/api/v1/author");
        response = request.when()
                .get()
                .then()
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 404);
        System.out.println(response.getBody().asString());
    }
}
