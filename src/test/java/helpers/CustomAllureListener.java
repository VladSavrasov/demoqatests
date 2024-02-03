package helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomAllureListener {
    private final static AllureRestAssured FILTER = new AllureRestAssured();
    public static AllureRestAssured withCustomTemplates(){
        FILTER.setRequestTemplate("request.ftl");
        FILTER.setResponseTemplate("response.ftl");
        return FILTER;
    }
}
