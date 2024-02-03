package api;

import api.models.LoginRequestModel;
import api.models.LoginResponseModel;

import static api.specs.userSpec.requestSpec;
import static api.specs.userSpec.responseSpec;
import static io.restassured.RestAssured.given;

public class AutorizationApi {
    public LoginResponseModel login(LoginRequestModel loginRequestModel) {
        return given(requestSpec)
                .body(loginRequestModel)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(LoginResponseModel.class);
    }
}
