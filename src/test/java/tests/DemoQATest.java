package tests;

import api.BookApi;
import com.codeborne.selenide.Configuration;
import extentions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.text;


public class DemoQATest extends TestBase {
    BookApi bookApi = new BookApi();
    ProfilePage profilePage = new ProfilePage();

//    @Test
//    void successfulLoginWithUiTest() {
//        Configuration.pageLoadStrategy = "eager";
//        open("/login");
//        $("#userName").setValue("tester");
//        $("#password").setValue("Tester12!").pressEnter();
//        $("#userName-value").shouldHave(text("tester"));
//    }

    @WithLogin
    @DisplayName("Удаление книги из профиля зарегистрированного пользователя")
    @Test
    void successfulLoginTest() {

        bookApi.deleteAllBooks();
        bookApi.findAvailibleIsbn();
        bookApi.addBook(addBookRequestModel);
        bookApi.deleteBook(deleteBookModel);


        profilePage.openSite().
                checkUserName(TestData.LOGIN).
                checkBookListHasNoBook(isbn.getTitle()).
                linkForBookMissing(isbn.getIsbn());
    }
}

