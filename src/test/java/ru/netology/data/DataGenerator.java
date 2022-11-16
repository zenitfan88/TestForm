package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import lombok.Value;

import static io.restassured.RestAssured.given;


public class DataGenerator {
    private static final Faker faker = new Faker();

    private static String status;
    private static final String username = faker.name().username();
    private static final String password = faker.internet().password();

    public static class User {
        private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static void registrationUser(String status) {
            given()
                    .spec(requestSpec)
                    .body(new TestUser(username, password, status))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);

        }

        public static String getUserName() {
            return username;
        }

        public static String getPassword() {
            return password;
        }

        private static final Faker faker = new Faker();


        public static String generateUserName() {
            return faker.name().username();
        }

        public static String generatePassword() {
            return faker.internet().password();
        }
    }

    @Value
    public static class TestUser {
        String login;
        String password;
        String status;
    }

}