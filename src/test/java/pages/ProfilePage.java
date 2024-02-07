package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ProfilePage {
    SelenideElement userName = $("#userName-value"),
            booksList = $(".rt-tbody");

    @Step("Проверить что у пользователя нет книг")
    public ProfilePage checkBookListHasNoBook(String value) {
        booksList.shouldBe(visible).shouldNotHave(text(value));
        return this;
    }

    public void linkForBookMissing(String value) {
        $("[href='/profile?book=" + value + "']").shouldHave(hidden);
    }

    public ProfilePage checkUserName(String value) {
        userName.shouldHave(text(value));
        return this;
    }

    @Step("Открыть UI страницу")
    public ProfilePage openSite() {
        open("/profile");
        return this;
    }
}
