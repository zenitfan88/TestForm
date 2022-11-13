package ru.netology.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.User.*;


public class TestMode {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void loginСompletedTest() {
        activeRegistrationUser();
        $("[data-test-id='login'] input").val(getUserName());
        $("[data-test-id='password'] input").val(getPassword());
        $("[data-test-id='action-login']").click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    void loginBlockedTest() {
        blockedRegistrationUser();
        $("[data-test-id='login'] input").val(getUserName());
        $("[data-test-id='password'] input").val(getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void notTrueLoginTest() {
        activeRegistrationUser();
        $("[data-test-id='login'] input").val(generateUserName());
        $("[data-test-id='password'] input").val(getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void notTruePasswordTest() {
        activeRegistrationUser();
        $("[data-test-id='login'] input").val(getUserName());
        $("[data-test-id='password'] input").val(generatePassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }
}
