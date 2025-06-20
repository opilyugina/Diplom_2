package stellarburgers.client;

import stellarburgers.constants.Endpoints;
import stellarburgers.model.User;
import stellarburgers.model.Credentials;
import io.restassured.response.Response;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class UserClient {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String APPLICATION_JSON = "application/json";

    @Step("Регистрация пользователя")
    public Response register(User user) {
        return given()
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .body(user)
                .when()
                .post(Endpoints.REGISTER);
    }

    @Step("Авторизация пользователя")
    public Response login(Credentials credentials) {
        return given()
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .body(credentials)
                .when()
                .post(Endpoints.LOGIN);
    }

    @Step("Обновление данных пользователя")
    public Response updateUser(String accessToken, User user) {
        return given()
                .header(AUTHORIZATION_HEADER, accessToken)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .body(user)
                .when()
                .patch(Endpoints.USER);
    }

    @Step("Удаление пользователя")
    public void delete(String accessToken) {
        given()
                .header(AUTHORIZATION_HEADER, accessToken)
                .when()
                .delete(Endpoints.USER)
                .then()
                .statusCode(SC_ACCEPTED)
                .extract().response();
    }
}