package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;


public class ProfilePage {
    SelenideElement userName = $("#userName-value"),
            booksList = $(".rt-tbody");

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
}
