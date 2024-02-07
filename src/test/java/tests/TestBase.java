package tests;

import api.models.AddBookRequestModel;
import api.models.DeleteBookModel;
import api.models.IsbnModel;
import api.models.LoginRequestModel;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;


import config.Config;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    public static IsbnModel isbn;
    public static List<IsbnModel> isbnList = new ArrayList<>();
    public static String userId;
    public static String token;

//    LoginRequestModel loginRequestModel = new LoginRequestModel(TestData.LOGIN,
//            TestData.PASSWORD);
    AddBookRequestModel addBookRequestModel = new AddBookRequestModel();
    DeleteBookModel deleteBookModel = new DeleteBookModel();

    @BeforeAll
    static void beforeAll() {
        Config config = ConfigFactory.create(Config.class, System.getProperties());
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browser = config.browser();
        Configuration.browserVersion = config.browserVersion();
        Configuration.browserSize = config.browserSize();
        Configuration.remote = config.remoteUrl();
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterEach
    void afterAll() {
        Attach.screenShotAs("Last screenshot");
        if (!Configuration.browser.equalsIgnoreCase("firefox")){
            Attach.browserConsoleLogs();
        }
        Attach.pageSource();
        Attach.addVideo();

        closeWebDriver();
    }
//    Configuration.browser = System.getProperty("browser", "chrome");
//    Configuration.browserVersion = System.getProperty("browserVersion", "99.0");
//    Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
//    Configuration.pageLoadStrategy = "eager";
//    Configuration.baseUrl = "https://vog.vkusvill.ru/";
//    Configuration.remote = "https://user1:1234@" + remoteUrl + "/wd/hub";
//
//    DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("browserName", System.getProperty("browser"));
//        capabilities.setCapability("browserVersion",System.getProperty("browserVersion"));
//        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
//                "enableVNC", true,
//                        "enableVideo", true
//    ));
//    Configuration.browserCapabilities = capabilities;
//        SelenideLogger.addListener("allure", new AllureSelenide());
//    open(baseUrl);
//}
//
//
//    @AfterEach
//    void addAttachments() {
//        Attach.screenshotAs("Last screenshot");
//        Attach.pageSource();
//        if (!Configuration.browser.equalsIgnoreCase("firefox")) {
//            Attach.browserConsoleLogs();
//        }
//        Attach.addVideo();
//    }
}
