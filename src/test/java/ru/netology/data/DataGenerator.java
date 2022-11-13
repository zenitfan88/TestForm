package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.Value;

import static io.restassured.RestAssured.given;

import lombok.RequiredArgsConstructor;


@Data

public class DataGenerator {
    private static final Faker faker = new Faker();
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

        public static void activeRegistrationUser() {
            given()
                    .spec(requestSpec)
                    .body(new TestUser(username, password, "active"))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);

        }

        public static void blockedRegistrationUser() {
            given()
                    .spec(requestSpec)
                    .body(new Gson().toJson(new TestUser(username, password, "blocked")))
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
    @Data
    @RequiredArgsConstructor
    public static class TestUser {
        String login;
        String password;
        String status;
    }

}