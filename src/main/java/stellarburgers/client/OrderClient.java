package stellarburgers.client;

import io.qameta.allure.Step;
import stellarburgers.model.Order;
import stellarburgers.constants.Endpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String APPLICATION_JSON = "application/json";

    @Step("Создать заказ")
    public Response create(Order order, String accessToken) {
        return given()
                .header(AUTHORIZATION_HEADER, accessToken)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .body(order)
                .when()
                .post(Endpoints.ORDERS);
    }

    @Step("Получить заказы пользователя")
    public Response getAll(String accessToken) {
        return given()
                .header(AUTHORIZATION_HEADER, accessToken)
                .when()
                .get(Endpoints.ORDERS);
    }
}