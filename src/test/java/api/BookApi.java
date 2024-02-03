package api;

import api.models.AddBookRequestModel;
import api.models.DeleteBookModel;
import api.models.LoginResponseModel;

import static api.specs.userSpec.requestSpec;
import static api.specs.userSpec.responseSpec;
import static io.restassured.RestAssured.given;

public class BookApi {
    public void addBook(LoginResponseModel response,
                        AddBookRequestModel addBookRequestModel){
        given(requestSpec)
                .body(addBookRequestModel)
//                .cookie("userID", authResponse.path("userId"))
//                .cookie("expires", authResponse.path("expires"))
                .header("Authorization", "Bearer "+ response.getToken())
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(201);

    }

    public void deleteBook (LoginResponseModel response,
                            DeleteBookModel deleteBookModel){
        given(requestSpec)
                .header("Authorization", "Bearer "+ response.getToken())
                .body(deleteBookModel)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(responseSpec)
                .statusCode(204);
    }



}
