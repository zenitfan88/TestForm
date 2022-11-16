package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.data.DataGenerator.User.*;
import static ru.netology.data.DataGenerator.User.generatePassword;

public class TestForm {
    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void loginСompletedTest() {
        registrationUser("active");
        $("[data-test-id='login'] input").val(getUserName());
        $("[data-test-id='password'] input").val(getPassword());
        $("[data-test-id='action-login']").click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(5));
    }

    @Test
    void loginBlockedTest() {
        registrationUser("blocked");
        $("[data-test-id='login'] input").val(getUserName());
        $("[data-test-id='password'] input").val(getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Пользователь заблокирован"))
                .isDisplayed();
    }

    @Test
    void notTrueLoginTest() {
        registrationUser("active");
        $("[data-test-id='login'] input").val(generateUserName());
        $("[data-test-id='password'] input").val(getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"))
                .isDisplayed();
    }

    @Test
    void notTruePasswordTest() {
        registrationUser("active");
        $("[data-test-id='login'] input").val(getUserName());
        $("[data-test-id='password'] input").val(generatePassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"))
                .isDisplayed();
    }
}