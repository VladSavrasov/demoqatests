package tests;

import api.BookApi;
import extentions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;


public class DemoQATest extends TestBase {
    BookApi bookApi = new BookApi();
    ProfilePage profilePage = new ProfilePage();

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

