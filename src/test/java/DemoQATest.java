import com.codeborne.selenide.Configuration;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static com.codeborne.selenide.Condition.text;
import static java.lang.String.format;

public class DemoQATest {
    @Test
void successfulLoginWithUiTest() {
        Configuration.pageLoadStrategy = "eager";
        open("https://demoqa.com/login");
        $("#userName").setValue("tester");
        $("#password").setValue("Tester12!").pressEnter();
        $("#userName-value").shouldHave(text("tester"));
    }

    @Test
    void successfulLoginWithApiTest() {
        Configuration.pageLoadStrategy = "eager";
        String authData = "{\"userName\":\"tester\",\"password\":\"Tester12!\"}";

        Response authResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://demoqa.com/Account/v1/Login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();

        String addBookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"9781449325862\"}]}",authResponse.path("userId").toString());


        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(addBookData)
//                .cookie("userID", authResponse.path("userId"))
//                .cookie("expires", authResponse.path("expires"))
                .header("Authorization", "Bearer "+ authResponse.path("token"))
                .when()
                .post("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);

        String deleteBookData = format("{\"isbn\": \"9781449325862\", \"userId\":\"%s\"}",authResponse.path("userId").toString());
//            given()
//                    .header("Authorization", "Bearer "+ authResponse.path("token"))
//                    .queryParam("UserId", authResponse.path("userId").toString())
//                    .when()
//                    .delete("https://demoqa.com/BookStore/v1/Books")
//                    .then()
//                    .statusCode(204);

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer "+ authResponse.path("token"))
                .body(deleteBookData)
                .when()
                .delete("https://demoqa.com/BookStore/v1/Book")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

        open("https://demoqa.com/BookStore/v1/Books");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

        open("https://demoqa.com/profile");
        $("#userName-value").shouldHave(text("tester"));
        $(".rt-tbody").shouldNotHave(text("Git Pocket Guide"));
        $("[href='/profile?book=9781449325862']").shouldBe(hidden);
    }
}

