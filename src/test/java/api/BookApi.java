package api;

import api.models.*;
import io.qameta.allure.Step;


import static api.specs.userSpec.requestSpec;
import static api.specs.userSpec.responseSpec;
import static io.restassured.RestAssured.given;
import static tests.TestBase.*;

public class BookApi {
    @Step("Запрос на добавление книги в корзину")
    public void addBookRequest(
                               AddBookRequestModel addBookRequestModel){
        given(requestSpec)
                .body(addBookRequestModel)
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(201);

    }
    @Step("Дбавление книги в корзину")
    public void addBook(
                        AddBookRequestModel addBookRequestModel){
        isbnList.add(isbn);
        addBookRequestModel.setUserId(userId);
        addBookRequestModel.setCollectionOfIsbns(isbnList);
        addBookRequest(addBookRequestModel);
    }
    @Step("Удаление книги из корзины")
    public void deleteBook(
                           DeleteBookModel deleteBookModel
                                  ) {
        deleteBookModel.setIsbn(isbn.getIsbn());
        deleteBookModel.setUserId(userId);
        deleteBookRequest(deleteBookModel);
    }
    @Step("Запрос на удаление книги из корзины")
    public void deleteBookRequest(
                                  DeleteBookModel deleteBookModel){
        given(requestSpec)
                .header("Authorization", "Bearer "+ token)
                .body(deleteBookModel)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(responseSpec)
                .statusCode(204);
    }
    @Step("Удаление всех книг из корзины")
    public void deleteAllBooks (){
        given(requestSpec)
                .header("Authorization", "Bearer "+ token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(204);
    }
    @Step("Запрос на получение списка доспуных книг")
    public GetAllBooksResponseModel getAllBooks (){
        return given(requestSpec)
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract().as(GetAllBooksResponseModel.class);
    }
    @Step("Список доспуных книг")
    public void findAvailibleIsbn (){
        GetAllBooksResponseModel books = getAllBooks();
        isbn = books.getBooks()[0];
    }
}
