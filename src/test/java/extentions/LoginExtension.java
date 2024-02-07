package extentions;

import api.AutorizationApi;
import api.models.LoginRequestModel;
import api.models.LoginResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import tests.TestBase;
import tests.TestData;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        LoginRequestModel loginRequestModel = new LoginRequestModel(TestData.LOGIN, TestData.PASSWORD);
        LoginResponseModel response = new AutorizationApi().login(loginRequestModel);
        TestBase.userId = response.getUserId();
        TestBase.token = response.getToken();
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", response.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", response.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", response.getExpires()));
    }

}
