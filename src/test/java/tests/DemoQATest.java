package tests;

import api.AutorizationApi;
import api.BookApi;
import api.models.*;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

import static api.specs.userSpec.requestSpec;
import static api.specs.userSpec.responseSpec;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static com.codeborne.selenide.Condition.text;
import static java.lang.String.format;
import static tests.TestData.LOGIN;
import static tests.TestData.PASSWORD;

public class DemoQATest extends TestBase{
    @Test
void successfulLoginWithUiTest() {
        Configuration.pageLoadStrategy = "eager";
        open("/login");
        $("#userName").setValue("tester");
        $("#password").setValue("Tester12!").pressEnter();
        $("#userName-value").shouldHave(text("tester"));
    }

    @Test
    void successfulLoginTest() {
//        LoginRequestModel loginRequestModel =new LoginRequestModel(LOGIN,PASSWORD);

//        AutorizationApi autorizationApi = new AutorizationApi();
        LoginResponseModel response =new AutorizationApi().login(loginRequestModel);
//        LoginResponseModel response = given(requestSpec)
//                .body(loginRequestModel)
//                .when()
//                .post("/Account/v1/Login")
//                .then()
//                .spec(responseSpec)
//                .statusCode(200)
//                .extract().as(LoginResponseModel.class);

        AddBookRequestModel addBookRequestModel = new AddBookRequestModel();
        IsbnModel isbnModel = new IsbnModel();
        isbnModel.setIsbn("9781449325862");
        List<IsbnModel> isbnList = new ArrayList<>();
        isbnList.add(isbnModel);
        addBookRequestModel.setUserId(response.getUserId());
        addBookRequestModel.setCollectionOfIsbns(isbnList);

        BookApi bookApi =new BookApi();
        bookApi.addBook(response,addBookRequestModel);
//        given(requestSpec)
//                .body(addBookRequestModel)
////                .cookie("userID", authResponse.path("userId"))
////                .cookie("expires", authResponse.path("expires"))
//                .header("Authorization", "Bearer "+ response.getToken())
//                .when()
//                .post("/BookStore/v1/Books")
//                .then()
//                .spec(responseSpec)
//                .statusCode(201);

//            given()
//                    .header("Authorization", "Bearer "+ authResponse.path("token"))
//                    .queryParam("UserId", authResponse.path("userId").toString())
//                    .when()
//                    .delete("https://demoqa.com/BookStore/v1/Books")
//                    .then()
//                    .statusCode(204);
        DeleteBookModel deleteBookModel = new DeleteBookModel();
        deleteBookModel.setIsbn("9781449325862");
        deleteBookModel.setUserId(response.getUserId());
//        given(requestSpec)
//                .header("Authorization", "Bearer "+ response.getToken())
//                .body(deleteBookModel)
//                .when()
//                .delete("/BookStore/v1/Book")
//                .then()
//                .spec(responseSpec)
//                .statusCode(204);
        bookApi.deleteBook(response,deleteBookModel);

        open("/BookStore/v1/Books");
        getWebDriver().manage().addCookie(new Cookie("userID", response.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", response.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", response.getToken()));

        open("https://demoqa.com/profile");
        $("#userName-value").shouldHave(text("tester"));
        $(".rt-tbody").shouldNotHave(text("Git Pocket Guide"));
        $("[href='/profile?book=9781449325862']").shouldBe(hidden);
    }
}

