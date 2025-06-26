package stellarburgers.user;

import stellarburgers.base.BaseTest;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import stellarburgers.model.Credentials;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@DisplayName("Логин пользователя")
public class LoginUserTest extends BaseTest {

    @Test
    @DisplayName("Логин с валидными данными")
    @Description("Проверяем, что пользователь может войти с правильными email и паролем")
    public void loginWithValidCredentialsTest() {
        Credentials creds = new Credentials(user.getEmail(), user.getPassword());
        Response response = userClient.login(creds);
        assertEquals(SC_OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Логин с неверным email")
    @Description("Проверяем, что при неправильном email возвращается ошибка 401")
    public void loginWithWrongEmailTest() {
        Credentials creds = new Credentials("wrong@mail.com", user.getPassword());
        Response response = userClient.login(creds);
        assertEquals(SC_UNAUTHORIZED, response.getStatusCode());
        String message = response.jsonPath().getString("message");
        assertEquals("email or password are incorrect", message);
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Проверяем, что при неправильном пароле возвращается ошибка 401")
    public void loginWithWrongPasswordTest() {
        Credentials creds = new Credentials(user.getEmail(), "wrongpassword");
        Response response = userClient.login(creds);
        assertEquals(SC_UNAUTHORIZED, response.getStatusCode());
        String message = response.jsonPath().getString("message");
        assertEquals("email or password are incorrect", message);
    }
}