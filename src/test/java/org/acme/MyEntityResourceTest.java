package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.UUID;

@QuarkusTest
public class MyEntityResourceTest {

    @Test
    public void testCreateWithLock() {
        final String name = UUID.randomUUID().toString();
        given()
                .when().post("/myentity/withlock/{name}", name)
                .then()
                .statusCode(200)
                .body("name", is(name));
    }

    @Test
    public void testCreateWithoutLock() {
        final String name = UUID.randomUUID().toString();
        given()
                .when().post("/myentity/withoutlock/{name}", name)
                .then()
                .statusCode(200)
                .body("name", is(name));
    }
}